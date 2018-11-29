package hiroshi_homma.homma_create.hiroshi_create_android.core.navigation

import hiroshi_homma.homma_create.hiroshi_create_android.AndroidTest
import hiroshi_homma.homma_create.hiroshi_create_android.features.login.Authenticator
import hiroshi_homma.homma_create.hiroshi_create_android.features.login.LoginActivity
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.MoviesActivity
import hiroshi_homma.homma_create.hiroshi_create_android.shouldNavigateTo
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify


class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @Mock private lateinit var authenticator: Authenticator

    @Before fun setup() {
        navigator = Navigator(authenticator)
    }

    @Test fun `should forward user to login screen`() {
        whenever(authenticator.userLoggedIn()).thenReturn(false)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo LoginActivity::class
    }

    @Test fun `should forward user to movies screen`() {
        whenever(authenticator.userLoggedIn()).thenReturn(true)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo MoviesActivity::class
    }
}
