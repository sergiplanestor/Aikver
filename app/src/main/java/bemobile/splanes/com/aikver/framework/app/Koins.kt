package bemobile.splanes.com.aikver.framework.app

import bemobile.splanes.com.aikver.data.serie.SerieDataSource
import bemobile.splanes.com.aikver.data.serie.SerieRepository
import bemobile.splanes.com.aikver.data.user.UserDataSource
import bemobile.splanes.com.aikver.data.user.UserRepository
import bemobile.splanes.com.aikver.framework.helper.FirebaseHelper
import bemobile.splanes.com.aikver.framework.helper.ImageLoaderHelper
import bemobile.splanes.com.aikver.framework.helper.SharedPreferencesHelper
import bemobile.splanes.com.aikver.framework.service.SerieService
import bemobile.splanes.com.aikver.framework.service.UserService
import bemobile.splanes.com.aikver.interactor.*
import bemobile.splanes.com.aikver.presentation.detailsserie.DetailsSerieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/* Repositories */
val repositoryModule = module(override = true) {
    factory { UserRepository(get()) }
    factory { SerieRepository(get()) }
}

/* DataSources */
val dataSourceModule = module(override = true) {
    factory<UserDataSource> { UserService(get(), get()) }
    factory<SerieDataSource> { SerieService(get()) }
}

/* UseCases */
val useCaseModule = module(override = true) {
    factory { GetUserUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
    factory { DoLoginUseCase(get()) }
    factory { AddSerieUseCase(get()) }
    factory { GetSeriesUseCase(get()) }
    factory { GetRecentSeriesUseCase(get()) }
    factory { UpdateSerieUseCase(get()) }
    factory { DeleteSerieUseCase(get()) }
}

/* Helpers */
val helperModule = module(override = true) {
    factory { SharedPreferencesHelper(get()) }
    single { FirebaseHelper() }
    factory { ImageLoaderHelper(get()) }
}

val viewModelModule = module(override = true) {
    viewModel { DetailsSerieViewModel(get(), get()) }
}