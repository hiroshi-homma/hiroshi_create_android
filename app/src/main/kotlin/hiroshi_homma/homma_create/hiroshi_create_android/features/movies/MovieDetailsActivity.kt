package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.content.Context
import android.content.Intent
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.BaseActivity

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.fernandocejas.INTENT_PARAM_MOVIE"

        fun callingIntent(context: Context, movie: MovieView): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_MOVIE, movie)
            return intent
        }
    }

    override fun fragment() = MovieDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))
}
