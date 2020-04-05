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
import revolhope.splanes.com.aikver.interactor.user.DoLoginUseCase
import revolhope.splanes.com.aikver.interactor.user.GetUserUseCase
import revolhope.splanes.com.aikver.interactor.user.RegisterUserUseCase
import revolhope.splanes.com.aikver.interactor.user.SaveUserLocalUseCase
import revolhope.splanes.com.aikver.presentation.init.login.LoginViewModel
import revolhope.splanes.com.aikver.presentation.init.register.RegisterViewModel
import revolhope.splanes.com.aikver.presentation.init.splash.SplashViewModel

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

    /* User use cases */
    factory { GetUserUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
    factory { DoLoginUseCase(get()) }
    factory { SaveUserLocalUseCase(get()) }

    /* Series use cases */
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
    viewModel { SplashViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { DetailsSerieViewModel(get(), get()) }
}