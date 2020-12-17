package hiroshi_homma.homma_create.hiroshi_create_android

import android.app.Application
import hiroshi_homma.homma_create.hiroshi_create_android.core.di.ApplicationComponent
import hiroshi_homma.homma_create.hiroshi_create_android.core.di.ApplicationModule
import hiroshi_homma.homma_create.hiroshi_create_android.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class AndroidApplication : Application() {

    // test comment
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
