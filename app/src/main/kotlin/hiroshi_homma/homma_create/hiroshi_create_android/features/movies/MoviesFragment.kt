package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseFragment
import hiroshi_homma.homma_create.hiroshi_create_android.R
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.MovieFailure.ListNotAvailable
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.NetworkConnection
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.ServerError
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.failure
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.invisible
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.observe
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.viewModel
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.visible
import hiroshi_homma.homma_create.hiroshi_create_android.core.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_movies.emptyView
import kotlinx.android.synthetic.main.fragment_movies.movieList
import javax.inject.Inject

class MoviesFragment : BaseFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var moviesAdapter: MoviesAdapter

    private lateinit var moviesViewModel: MoviesViewModel

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        moviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        moviesAdapter.clickListener = { movie, navigationExtras ->
                    navigator.showMovieDetails(activity!!, movie, navigationExtras) }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        moviesViewModel.loadMovies()
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.collection = movies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
