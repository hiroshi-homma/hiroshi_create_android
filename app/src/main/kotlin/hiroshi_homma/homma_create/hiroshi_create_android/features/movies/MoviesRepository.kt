package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.NetworkConnection
import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.ServerError
import hiroshi_homma.homma_create.hiroshi_create_android.core.functional.Either
import hiroshi_homma.homma_create.hiroshi_create_android.core.functional.Either.Left
import hiroshi_homma.homma_create.hiroshi_create_android.core.functional.Either.Right
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    fun movies(): Either<Failure, List<Movie>>
    fun movieDetails(movieId: Int): Either<Failure, MovieDetails>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: MoviesService) : MoviesRepository {

        override fun movies(): Either<Failure, List<Movie>> {
            return when (networkHandler.isConnected) {
                true -> request(service.movies(), { it.map { it.toMovie() } }, emptyList())
                false, null -> Left(NetworkConnection())
            }
        }

        override fun movieDetails(movieId: Int): Either<Failure, MovieDetails> {
            return when (networkHandler.isConnected) {
                true -> request(service.movieDetails(movieId), { it.toMovieDetails() }, MovieDetailsEntity.empty())
                false, null -> Left(NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError())
                }
            } catch (exception: Throwable) {
                Left(ServerError())
            }
        }
    }
}
