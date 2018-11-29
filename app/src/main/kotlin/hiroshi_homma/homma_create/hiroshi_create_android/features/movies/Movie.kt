package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.core.extension.empty

data class Movie(val id: Int, val poster: String) {
    companion object {
        fun empty() = Movie(0, String.empty())
    }
}
