package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.arch.lifecycle.MutableLiveData
import hiroshi_homma.homma_create.hiroshi_create_android.core.interactor.UseCase.None
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseViewModel
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

    fun loadMovies() = getMovies(None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies.map { MovieView(it.id, it.poster) }
    }
}