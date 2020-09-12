package revolhope.splanes.com.core.domain.repositoryimpl


import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.datasource.CacheConfigurationDataSource
import revolhope.splanes.com.core.data.datasource.CacheContentDataSource
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.filterRepeated
import revolhope.splanes.com.core.domain.mapper.ConfigurationMapper
import revolhope.splanes.com.core.domain.mapper.ContentMapper
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.mapper.UserMapper
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.movie.CustomMovie
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.CustomSerie
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class ContentRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val firebaseDataSource: FirebaseDataSource,
    private val userRepository: UserRepository
) : ContentRepository {

    override suspend fun fetchConfiguration(): Configuration? =
        if (CacheConfigurationDataSource.fetchConfig() == null) {
            apiDataSource.fetchConfiguration()?.let(ConfigurationMapper::fromEntityToModel)
                .also(CacheConfigurationDataSource::insertConfig)
        } else {
            CacheConfigurationDataSource.fetchConfig()
        }

    override suspend fun searchMovies(query: String): List<Movie>? =
        apiDataSource.searchMovies(query = query)?.let {
            it.results?.let { movies ->
                ContentMapper.fromMovieEntityListToModel(movies, fetchConfiguration())
            }
        }

    override suspend fun searchSeries(query: String, page: Int): List<Serie>? =
        apiDataSource.searchSeries(query = query, page = page)?.let {
            it.results?.let { series ->
                ContentMapper.fromSerieEntityListToModel(series, fetchConfiguration())
            }
        }

    override suspend fun fetchSerieDetails(serieId: Int): SerieDetails? =
        apiDataSource.fetchSerieDetails(serieId)?.let {
            ContentMapper.fromSerieDetailsEntityToModel(it, fetchConfiguration())
        }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails? =
        apiDataSource.fetchMovieDetails(movieId)?.let {
            ContentMapper.fromMovieDetailsEntityToModel(it, fetchConfiguration())
        }

    override suspend fun fetchRelatedSeries(serieId: Int, page: Int): QueriedSeries? =
        apiDataSource.fetchRelatedSeries(serieId, page)?.let {
            ContentMapper.fromQuerySeriesEntityToModel(it, fetchConfiguration())
        }

    override suspend fun fetchRelatedMovies(movieId: Int, page: Int): QueriedMovies? =
        apiDataSource.fetchRelatedMovies(movieId, page)?.let {
            ContentMapper.fromQueryMoviesEntityToModel(it, fetchConfiguration())
        }

    override suspend fun fetchPopularSeries(page: Int, forceCall: Boolean): QueriedSeries? =
        if (forceCall || CacheContentDataSource.fetchPopularSeries() == null) {
            apiDataSource.fetchPopularSeries(page)?.let {
                ContentMapper.fromQuerySeriesEntityToModel(it, fetchConfiguration())
            }.also(CacheContentDataSource::insertPopularSeries)
        } else {
            CacheContentDataSource.fetchPopularSeries()
        }

    override suspend fun fetchPopularMovies(page: Int, forceCall: Boolean): QueriedMovies? =
        if (forceCall || CacheContentDataSource.fetchPopularMovies() == null) {
            apiDataSource.fetchPopularMovies(page)?.let {
                ContentMapper.fromQueryMoviesEntityToModel(it, fetchConfiguration())
            }.also(CacheContentDataSource::insertPopularMovies)
        } else {
            CacheContentDataSource.fetchPopularMovies()
        }

    override suspend fun insertSerie(
        serie: SerieDetails,
        seenByUser: Boolean,
        network: Network,
        recommendedTo: List<UserGroupMember>,
        userPunctuation: Int,
        userComments: String
    ): Boolean =
        userRepository.fetchUser()?.let { user ->
            user.selectedUserGroup?.let { selectedGroup ->
                val userMember = UserMapper.fromUserModelToUserGroupMemberModel(
                    user,
                    selectedGroup.id,
                    selectedGroup.userGroupAdmin.userId
                )
                firebaseDataSource.insertSerie(
                    UserGroupMapper.fromModelToEntity(selectedGroup),
                    CustomSerie(
                        content = serie,
                        userAdded = userMember,
                        dateAdded = System.currentTimeMillis(),
                        seenBy = if (seenByUser) mutableListOf(userMember) else mutableListOf(),
                        network = network,
                        recommendedTo = recommendedTo,
                        punctuation = if (userPunctuation != -1) {
                            mutableListOf(userMember to userPunctuation.toFloat())
                        } else {
                            mutableListOf()
                        },
                        comments = if (userComments.isNotBlank()) {
                            mutableListOf(userMember to userComments)
                        } else {
                            mutableListOf()
                        }
                    ).let(ContentMapper::fromCustomSerieModelToEntity)
                ).also {
                    if (it) userRepository.updateUser(user.apply { numOfContentAddedByUser++ })
                }
            } ?: false
        } ?: false

    override suspend fun insertMovie(
        movie: MovieDetails,
        seenByUser: Boolean,
        network: Network,
        recommendedTo: List<UserGroupMember>,
        userPunctuation: Int,
        userComments: String
    ): Boolean =
        userRepository.fetchUser()?.let { user ->
            user.selectedUserGroup?.let { selectedGroup ->
                val userMember = UserMapper.fromUserModelToUserGroupMemberModel(
                    user,
                    selectedGroup.id,
                    selectedGroup.userGroupAdmin.userId
                )
                firebaseDataSource.insertMovie(
                    UserGroupMapper.fromModelToEntity(selectedGroup),
                    CustomMovie(
                        content = movie,
                        userAdded = userMember,
                        dateAdded = System.currentTimeMillis(),
                        seenBy = if (seenByUser) mutableListOf(userMember) else mutableListOf(),
                        network = network,
                        recommendedTo = recommendedTo,
                        punctuation = if (userPunctuation != -1) {
                            mutableListOf(userMember to userPunctuation.toFloat())
                        } else {
                            mutableListOf()
                        },
                        comments = if (userComments.isNotBlank()) {
                            mutableListOf(userMember to userComments)
                        } else {
                            mutableListOf()
                        }
                    ).let(ContentMapper::fromCustomMovieModelToEntity)
                ).also {
                    if (it) userRepository.updateUser(user.apply { numOfContentAddedByUser++ })
                }
            } ?: false
        } ?: false

    override suspend fun fetchSelectedGroupContent(forceCall: Boolean): List<CustomContent<ContentDetails>>? =
        if (forceCall || CacheContentDataSource.fetchGroupContent().isNullOrEmpty()) {
            userRepository.fetchUser()?.let { user ->
                user.selectedUserGroup?.let { group ->
                    val series = fetchSelectedGroupSeries(group)
                    val movies = fetchSelectedGroupMovies(group)
                    series.toMutableList().filterRepeated().apply {
                        addAll(movies.toMutableList().filterRepeated())
                        sortWith(compareBy { it.dateAdded })
                    }.also(CacheContentDataSource::insertGroupContent)
                } ?: emptyList()
            }
        } else {
            CacheContentDataSource.fetchGroupContent()
        }

    // FIXME: Try to resolver unchecked cast
    @Suppress("UNCHECKED_CAST")
    private suspend fun fetchSelectedGroupSeries(group: UserGroup): List<CustomContent<ContentDetails>> =
        firebaseDataSource.fetchGroupSeries(groupId = group.id)?.map {
            val userAdded =
                userRepository.fetchUserById(it.userAdded ?: "")?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }

            val seenBy = it.seenBy?.map { id ->
                userRepository.fetchUserById(id)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }
            }.orEmpty().filterNotNull()

            val recommendedTo = it.recommendedTo?.map { id ->
                userRepository.fetchUserById(id)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }
            }.orEmpty().filterNotNull()

            val punctuation = it.punctuation?.map { pair ->
                userRepository.fetchUserById(pair.first)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }?.run { this to pair.second }
            }.orEmpty().filterNotNull()

            val comments = it.comments?.map { pair ->
                userRepository.fetchUserById(pair.first)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }?.run { this to pair.second }
            }.orEmpty().filterNotNull()

            ContentMapper.fromCustomSerieEntityToModel(
                entity = it,
                serieDetails = fetchSerieDetails(it.contentId?.toInt() ?: -1),
                userAdded = userAdded,
                seenBy = seenBy,
                recommendedTo = recommendedTo,
                punctuation = punctuation,
                comments = comments
            )

        } as? List<CustomContent<ContentDetails>> ?: emptyList()

    @Suppress("UNCHECKED_CAST")
    private suspend fun fetchSelectedGroupMovies(group: UserGroup): List<CustomContent<ContentDetails>> =
        firebaseDataSource.fetchGroupMovies(groupId = group.id)?.map {
            val userAdded =
                userRepository.fetchUserById(it.userAdded ?: "")?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }
            val seenBy = it.seenBy?.map { id ->
                userRepository.fetchUserById(id)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }
            }.orEmpty().filterNotNull()
            val recommendedTo = it.recommendedTo?.map { id ->
                userRepository.fetchUserById(id)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }
            }.orEmpty().filterNotNull()
            val punctuation = it.punctuation?.map { pair ->
                userRepository.fetchUserById(pair.first)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }?.run { this to pair.second }
            }.orEmpty().filterNotNull()

            val comments = it.comments?.map { pair ->
                userRepository.fetchUserById(pair.first)?.let { user ->
                    UserMapper.fromUserModelToUserGroupMemberModel(
                        model = user,
                        groupId = group.id,
                        userGroupAdminId = group.userGroupAdmin.userId
                    )
                }?.run { this to pair.second }
            }.orEmpty().filterNotNull()

            ContentMapper.fromCustomMovieEntityToModel(
                entity = it,
                movieDetails = fetchMovieDetails(it.contentId?.toInt() ?: -1),
                userAdded = userAdded,
                seenBy = seenBy,
                recommendedTo = recommendedTo,
                punctuation = punctuation,
                comments = comments
            )

        } as? List<CustomContent<ContentDetails>> ?: emptyList()

    private suspend fun updateContent(
        currentUser: User,
        customContent: CustomContent<ContentDetails>
    ): Boolean =
        currentUser.selectedUserGroup?.let {
            val groupEntity = UserGroupMapper.fromModelToEntity(it)
            when (customContent.content) {
                is SerieDetails -> {
                    val serieEntity =
                        ContentMapper.fromCustomSerieModelToEntity(customContent.toCustomSerie())
                    firebaseDataSource.deleteSerie(groupEntity, serieEntity) &&
                            firebaseDataSource.insertSerie(groupEntity, serieEntity)
                }
                is MovieDetails -> {
                    val movieEntity =
                        ContentMapper.fromCustomMovieModelToEntity(customContent.toCustomMovie())
                    firebaseDataSource.deleteMovie(groupEntity, movieEntity) &&
                            firebaseDataSource.insertMovie(groupEntity, movieEntity)
                }
                else -> false
            }
        } ?: false

    override suspend fun insertComment(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        comment: String
    ): List<Pair<UserGroupMember, String>> =
        currentUser.selectedUserGroup?.let { selectedGroup ->
            val userComment = UserMapper.fromUserModelToUserGroupMemberModel(
                model = currentUser,
                groupId = selectedGroup.id,
                userGroupAdminId = selectedGroup.userGroupAdmin.userId
            ) to comment
            customContent.comments.add(userComment)
            if (updateContent(currentUser, customContent)) {
                CacheContentDataSource.fetchGroupContent()?.find {
                    it.content.id == customContent.content.id
                }?.comments?.apply { add(userComment) }
                    ?: fetchSelectedGroupContent()?.find { it.content.id == customContent.content.id }?.comments
                    ?: emptyList<Pair<UserGroupMember, String>>()
            } else {
                emptyList()
            }
        } ?: emptyList()


    override suspend fun insertPunctuation(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        punctuation: Int
    ): List<Pair<UserGroupMember, Float>> =
        currentUser.selectedUserGroup?.let { selectedGroup ->
            val userPunctuation = UserMapper.fromUserModelToUserGroupMemberModel(
                model = currentUser,
                groupId = selectedGroup.id,
                userGroupAdminId = selectedGroup.userGroupAdmin.userId
            ) to punctuation.toFloat()
            customContent.punctuation.add(userPunctuation)
            if (updateContent(currentUser, customContent)) {
                CacheContentDataSource.fetchGroupContent()?.find {
                    it.content.id == customContent.content.id
                }?.punctuation?.apply { add(userPunctuation) }
                    ?: fetchSelectedGroupContent()?.find { it.content.id == customContent.content.id }?.punctuation
                    ?: emptyList<Pair<UserGroupMember, Float>>()
            } else {
                emptyList()
            }
        } ?: emptyList()

    override suspend fun insertSeenBy(
        currentUser: User,
        customContent: CustomContent<ContentDetails>
    ): List<UserGroupMember> =
        currentUser.selectedUserGroup?.let { selectedGroup ->
            val member = UserMapper.fromUserModelToUserGroupMemberModel(
                model = currentUser,
                groupId = selectedGroup.id,
                userGroupAdminId = selectedGroup.userGroupAdmin.userId
            )
            customContent.seenBy.add(member)
            if (updateContent(currentUser, customContent)) {
                CacheContentDataSource.fetchGroupContent()?.find {
                    it.content.id == customContent.content.id
                }?.seenBy?.apply { add(member) }
                    ?: fetchSelectedGroupContent()?.find { it.content.id == customContent.content.id }?.seenBy
                    ?: emptyList<UserGroupMember>()
            } else {
                emptyList()
            }
        } ?: emptyList()

}