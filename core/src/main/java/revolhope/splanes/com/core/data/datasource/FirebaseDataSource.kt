package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.data.entity.user.UserEntity
import revolhope.splanes.com.core.data.entity.user.UserGroupEntity
import revolhope.splanes.com.core.domain.model.content.serie.Serie

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

    suspend fun insertSerie(userGroupEntity: UserGroupEntity, serie: Serie): Boolean

    suspend fun logout()
}