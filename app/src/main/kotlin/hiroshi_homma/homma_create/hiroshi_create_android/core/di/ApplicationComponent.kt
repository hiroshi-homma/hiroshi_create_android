package hiroshi_homma.homma_create.hiroshi_create_android.core.di

import hiroshi_homma.homma_create.hiroshi_create_android.AndroidApplication
import hiroshi_homma.homma_create.hiroshi_create_android.core.di.viewmodel.ViewModelModule
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.MovieDetailsFragment
import hiroshi_homma.homma_create.hiroshi_create_android.features.movies.MoviesFragment
import hiroshi_homma.homma_create.hiroshi_create_android.core.navigation.RouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
