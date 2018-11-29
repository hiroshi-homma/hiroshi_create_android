package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.GetMovieDetails.Params
import hiroshi_homma.homma_create.hiroshi_create_android.core.interactor.UseCase
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<MovieDetails, Params>() {
    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)
    data class Params(val id: Int)
}
