package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.core.interactor.UseCase
import hiroshi_homma.homma_create.hiroshi_create_android.core.interactor.UseCase.None
import javax.inject.Inject

class GetMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, None>() {
    override suspend fun run(params: None) = moviesRepository.movies()
}
