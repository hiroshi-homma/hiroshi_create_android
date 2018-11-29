package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.UnitTest
import hiroshi_homma.homma_create.hiroshi_create_android.core.functional.Either.Right
import hiroshi_homma.homma_create.hiroshi_create_android.core.interactor.UseCase
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

    private lateinit var getMovies: GetMovies

    @Mock private lateinit var moviesRepository: MoviesRepository

    @Before fun setUp() {
        getMovies = GetMovies(moviesRepository)
        given { moviesRepository.movies() }.willReturn(Right(listOf(Movie.empty())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getMovies.run(UseCase.None()) }

        verify(moviesRepository).movies()
        verifyNoMoreInteractions(moviesRepository)
    }
}
