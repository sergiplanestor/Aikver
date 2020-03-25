package bemobile.splanes.com.aikver.framework.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AikverApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AikverApp)
            modules(listOf (
                repositoryModule,
                dataSourceModule,
                useCaseModule,
                helperModule,
                viewModelModule)
            )
        }
    }
}