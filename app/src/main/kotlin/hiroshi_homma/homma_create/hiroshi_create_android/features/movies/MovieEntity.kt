package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

data class MovieEntity(private val id: Int, private val poster: String) {
    fun toMovie() = Movie(id, poster)
}
