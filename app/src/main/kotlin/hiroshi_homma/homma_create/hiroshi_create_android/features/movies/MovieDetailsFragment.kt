package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.os.Bundle
import android.view.View
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseFragment
import hiroshi_homma.homma_create.hiroshi_create_android.R
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.MovieFailure.NonExistentMovie
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.NetworkConnection
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.ServerError
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.close
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.failure
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.isVisible
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.loadFromUrl
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.loadUrlAndPostponeEnterTransition
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.observe
import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.viewModel
import kotlinx.android.synthetic.main.fragment_movie_details.movieCast
import kotlinx.android.synthetic.main.fragment_movie_details.movieDetails
import kotlinx.android.synthetic.main.fragment_movie_details.movieDirector
import kotlinx.android.synthetic.main.fragment_movie_details.moviePlay
import kotlinx.android.synthetic.main.fragment_movie_details.moviePoster
import kotlinx.android.synthetic.main.fragment_movie_details.movieSummary
import kotlinx.android.synthetic.main.fragment_movie_details.movieYear
import kotlinx.android.synthetic.main.fragment_movie_details.scrollView
import kotlinx.android.synthetic.main.toolbar.toolbar
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_MOVIE = "param_movie"

        fun forMovie(movie: MovieView): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_MOVIE, movie)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }

    @Inject lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let { movieDetailsAnimator.postponeEnterTransition(it) }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            movieDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_MOVIE) as MovieView).id)
        } else {
            movieDetailsAnimator.scaleUpView(moviePlay)
            movieDetailsAnimator.cancelTransition(moviePoster)
            moviePoster.loadFromUrl((arguments!![PARAM_MOVIE] as MovieView).poster)
        }
    }

    override fun onBackPressed() {
        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        if (moviePlay.isVisible())
            movieDetailsAnimator.scaleDownView(moviePlay)
        else
            movieDetailsAnimator.cancelTransition(moviePoster)
    }

    private fun renderMovieDetails(movie: MovieDetailsView?) {
        movie?.let {
            with(movie) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
                    it.toolbar.title = title
                }
                movieSummary.text = summary
                movieCast.text = cast
                movieDirector.text = director
                movieYear.text = year.toString()
                moviePlay.setOnClickListener { movieDetailsViewModel.playMovie(trailer) }
            }
        }
        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
        movieDetailsAnimator.scaleUpView(moviePlay)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> { notify(R.string.failure_network_connection); close() }
            is ServerError -> { notify(R.string.failure_server_error); close() }
            is NonExistentMovie -> { notify(R.string.failure_movie_non_existent); close() }
        }
    }
}
