package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.arch.lifecycle.MutableLiveData
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.GetMovieDetails.Params
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getMovieDetails: GetMovieDetails,
                    private val playMovie: PlayMovie) : BaseViewModel() {

    var movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()

    fun loadMovieDetails(movieId: Int) =
            getMovieDetails(Params(movieId)) { it.either(::handleFailure, ::handleMovieDetails) }

    fun playMovie(url: String) = playMovie(PlayMovie.Params(url))

    private fun handleMovieDetails(movie: MovieDetails) {
        this.movieDetails.value = MovieDetailsView(movie.id, movie.title, movie.poster,
                movie.summary, movie.cast, movie.director, movie.year, movie.trailer)
    }
}