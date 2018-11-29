package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.content.Context
import android.content.Intent
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
