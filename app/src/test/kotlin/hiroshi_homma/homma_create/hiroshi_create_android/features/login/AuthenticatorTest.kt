package hiroshi_homma.homma_create.hiroshi_create_android.features.login

import hiroshi_homma.homma_create.hiroshi_create_android.UnitTest
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }
}
