package revolhope.splanes.com.aikver.framework.app

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import revolhope.splanes.com.aikver.framework.net.ApiClientFactory
import revolhope.splanes.com.aikver.framework.datasourceimpl.FirebaseDataSourceImpl
import revolhope.splanes.com.aikver.framework.datasourceimpl.SharedPreferencesDataSourceImpl
import revolhope.splanes.com.aikver.presentation.feature.menu.searchcontent.SearchContentViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.master.ContentDetailsMasterViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.slave.ContentDetailsSlaveViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.CustomContentDetailsViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments.CommentsBottomSheetViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.punctuation.PunctuationBottomSheetViewModel
import revolhope.splanes.com.aikver.presentation.feature.menu.dashboard.DashboardViewModel
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
import revolhope.splanes.com.core.interactor.content.AddCommentUseCase
import revolhope.splanes.com.core.interactor.content.AddPunctuationUseCase
import revolhope.splanes.com.core.interactor.content.AddSeenByUseCase
import revolhope.splanes.com.core.interactor.content.FetchGroupContentUseCase
import revolhope.splanes.com.core.interactor.content.movie.SearchMovieUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchMovieDetailsUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchPopularMoviesUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchRelatedMoviesUseCase
import revolhope.splanes.com.core.interactor.content.movie.InsertMovieUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchPopularSeriesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchRelatedSeriesUseCase
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
    factory { FetchMovieDetailsUseCase(get()) }
    factory { InsertMovieUseCase(get()) }
    factory { FetchRelatedSeriesUseCase(get()) }
    factory { FetchRelatedMoviesUseCase(get()) }
    factory { FetchPopularSeriesUseCase(get()) }
    factory { FetchPopularMoviesUseCase(get()) }
    factory { FetchGroupContentUseCase(get()) }
    factory { AddCommentUseCase(get()) }
    factory { AddPunctuationUseCase(get()) }
    factory { AddSeenByUseCase(get()) }
}

val viewModelModule = module(override = true) {
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
    viewModel { UserAvatarViewModel(get(), get(), get(), get()) }
    viewModel { ManageGroupsViewModel(get(), get(), get(), get()) }
    viewModel { GroupDetailsViewModel(get(), get(), get(), get(), get()) }
    viewModel { SearchContentViewModel(get(), get(), get()) }
    viewModel { ContentDetailsMasterViewModel(get(), get(), get(), get(), get()) }
    viewModel { ContentDetailsSlaveViewModel(get(), get(), get(), get()) }
    viewModel { DashboardViewModel(get(), get(), get(), get(), get()) }
    viewModel { CustomContentDetailsViewModel(get(), get(), get(), get(), get()) }
    viewModel { CommentsBottomSheetViewModel(get(), get()) }
    viewModel { PunctuationBottomSheetViewModel(get(), get()) }
}