package revolhope.splanes.com.aikver.framework.app

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import revolhope.splanes.com.aikver.framework.net.ApiClientFactory
import revolhope.splanes.com.aikver.framework.datasourceimpl.FirebaseDataSourceImpl
import revolhope.splanes.com.aikver.framework.datasourceimpl.SharedPreferencesDataSourceImpl
import revolhope.splanes.com.aikver.presentation.feature.menu.addcontent.AddContentViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.SerieDetailsSlaveViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.SerieDetailsMasterViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.ProfileViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.UserAvatarViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.ManageGroupsViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details.GroupDetailsViewModel
import revolhope.splanes.com.aikver.presentation.feature.onboarding.login.LoginViewModel
import revolhope.splanes.com.aikver.presentation.feature.onboarding.register.RegisterViewModel
import revolhope.splanes.com.aikver.presentation.feature.onboarding.splash.SplashViewModel
import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.datasource.SharedPreferencesDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.repositoryimpl.ContentRepositoryImpl
import revolhope.splanes.com.core.domain.repositoryimpl.GroupRepositoryImpl
import revolhope.splanes.com.core.domain.repositoryimpl.UserRepositoryImpl
import revolhope.splanes.com.core.interactor.content.SearchMovieUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchSerieDetailsUseCase
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase
import revolhope.splanes.com.core.interactor.content.serie.SearchSerieUseCase
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase
import revolhope.splanes.com.core.interactor.user.DoLoginUseCase
import revolhope.splanes.com.core.interactor.user.avatar.FetchUserAvatarTypesUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserByNameUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserLoginUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.InsertUserLoginUseCase
import revolhope.splanes.com.core.interactor.user.RegisterUserUseCase
import revolhope.splanes.com.core.interactor.user.avatar.InsertUserAvatarUseCase

/* Repositories */
val repositoryModule = module(override = true) {
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory<GroupRepository> { GroupRepositoryImpl(get(), get()) }
    factory<ContentRepository> { ContentRepositoryImpl(get(), get(), get()) }
}

/* DataSources */
val dataSourceModule = module(override = true) {
    factory<FirebaseDataSource> { FirebaseDataSourceImpl() }
    factory<SharedPreferencesDataSource> { SharedPreferencesDataSourceImpl(get()) }
    factory { ApiClientFactory.create(ApiDataSource::class.java) }
}

/* UseCases */
val useCaseModule = module(override = true) {

    /* User use cases */
    factory { InsertUserLoginUseCase(get()) }
    factory { FetchUserLoginUseCase(get()) }
    factory { FetchUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
    factory { FetchUserByNameUseCase(get()) }
    factory { FetchUserAvatarTypesUseCase(get()) }
    factory { InsertUserAvatarUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
    factory { DoLoginUseCase(get()) }

    /* Group use cases */
    factory { InsertUserGroupMemberUseCase(get()) }
    factory { InsertUserGroupUseCase(get()) }
    factory { DeleteUserGroupMemberUseCase(get()) }
    factory { DeleteUserGroupUseCase(get()) }

    /* Content use cases */
    factory { SearchSerieUseCase(get()) }
    factory { SearchMovieUseCase(get()) }
    factory { FetchSerieDetailsUseCase(get()) }
    factory { InsertSerieUseCase(get()) }
}

val viewModelModule = module(override = true) {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { UserAvatarViewModel(get(), get(), get()) }
    viewModel { ManageGroupsViewModel(get(), get(), get()) }
    viewModel { GroupDetailsViewModel(get(), get(), get(), get()) }
    viewModel { AddContentViewModel(get(), get()) }
    viewModel { SerieDetailsMasterViewModel(get()) }
    viewModel { SerieDetailsSlaveViewModel(get(), get()) }
}