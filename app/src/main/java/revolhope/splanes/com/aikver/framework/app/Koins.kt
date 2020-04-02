package revolhope.splanes.com.aikver.framework.app

import revolhope.splanes.com.aikver.data.serie.SerieDataSource
import revolhope.splanes.com.aikver.data.serie.SerieRepository
import revolhope.splanes.com.aikver.data.user.UserDataSource
import revolhope.splanes.com.aikver.data.user.UserRepository
import revolhope.splanes.com.aikver.framework.helper.FirebaseHelper
import revolhope.splanes.com.aikver.framework.helper.ImageLoaderHelper
import revolhope.splanes.com.aikver.framework.helper.SharedPreferencesHelper
import revolhope.splanes.com.aikver.framework.service.SerieService
import revolhope.splanes.com.aikver.framework.service.UserService
import revolhope.splanes.com.aikver.interactor.*
import revolhope.splanes.com.aikver.presentation.detailsserie.DetailsSerieViewModel
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