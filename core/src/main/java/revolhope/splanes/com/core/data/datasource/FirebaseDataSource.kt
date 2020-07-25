package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.data.entity.content.CustomContentEntity
import revolhope.splanes.com.core.data.entity.content.CustomMovieEntity
import revolhope.splanes.com.core.data.entity.content.CustomSerieEntity
import revolhope.splanes.com.core.data.entity.user.UserEntity
import revolhope.splanes.com.core.data.entity.user.UserGroupEntity

interface FirebaseDataSource {
    suspend fun login(email: String, pwd: String) : Boolean

    suspend fun register(email: String, pwd: String) : Boolean

    suspend fun insertUser(userEntity: UserEntity) : Boolean

    suspend fun fetchUser(id: String) : UserEntity?

    suspend fun fetchUserByName(username: String) : UserEntity?

    suspend fun insertUserGroup(userGroupEntity: UserGroupEntity) : Boolean

    suspend fun fetchUserGroup(userGroupId: String) : UserGroupEntity?

    suspend fun fetchUserGroups(userEntity: UserEntity, limitTo: Int = -1) : List<UserGroupEntity>?

    suspend fun deleteUserGroup(userGroupEntity: UserGroupEntity) : Boolean

    suspend fun insertSerie(userGroupEntity: UserGroupEntity, serie: CustomSerieEntity): Boolean

    suspend fun insertMovie(userGroupEntity: UserGroupEntity, movie: CustomMovieEntity): Boolean

    suspend fun fetchGroupContent(groupId: String): List<CustomContentEntity>?

    suspend fun fetchGroupSeries(groupId: String): List<CustomSerieEntity>?

    suspend fun fetchGroupMovies(groupId: String): List<CustomMovieEntity>?

    suspend fun logout()
}