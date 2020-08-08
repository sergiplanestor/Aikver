package revolhope.splanes.com.core.domain.repositoryimpl

import com.google.gson.Gson
import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.datasource.CacheUserDataSource
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.datasource.SharedPreferencesDataSource
import revolhope.splanes.com.core.data.entity.user.UserEntity
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.mapper.UserMapper
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.domain.sha256
import java.lang.Exception
import java.util.UUID


class UserRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    private val apiDataSource: ApiDataSource
) : UserRepository {

    companion object {
        const val AVATAR_URL = "https://api.adorable.io/avatars/list"
    }

    override suspend fun fetchUserLogin(): UserLogin? {
        val value =
            sharedPreferencesDataSource.getString(SharedPreferencesDataSource.KEY_USR_LOGIN)
        return try {
            Gson().fromJson(value, UserLogin::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun insertUserLogin(userLogin: UserLogin): Boolean =
        sharedPreferencesDataSource.putString(
            SharedPreferencesDataSource.KEY_USR_LOGIN,
            Gson().toJson(userLogin)
        )

    override suspend fun register(username: String, userGroupName: String?): Boolean {
        val userLogin = UserLogin(
            id = UUID.randomUUID().toString().replace("-", ""),
            email = "${username.trim()}@xyz.com",
            pwd = sha256(username)
        )
        val resultRegister = firebaseDataSource.register(userLogin.email, userLogin.pwd)
        return if (resultRegister) {
            val resultInsertUserLogin = insertUserLogin(userLogin)
            val user = User(
                id = userLogin.id,
                avatar = UserAvatar(),
                username = username,
                selectedUserGroup = null,
                userGroups = mutableListOf()
            )
            if (!userGroupName.isNullOrBlank()) {
                val groupId = UUID.randomUUID().toString().replace("-", "")
                val userGroup =
                    UserGroup(
                        id = groupId,
                        name = userGroupName,
                        members = mutableListOf(
                            UserMapper.fromUserModelToUserGroupMemberModel(
                                model = user,
                                groupId = groupId,
                                userGroupAdminId = user.id
                            )
                        ),
                        userGroupAdmin = UserMapper.fromUserModelToUserGroupMemberModel(
                            model = user,
                            groupId = groupId,
                            userGroupAdminId = user.id
                        ),
                        dateCreation = System.currentTimeMillis()
                    )
                val resultStoreUser = insertUser(
                    user.apply {
                        selectedUserGroup = userGroup
                        userGroups.add(userGroup)
                    }
                )
                val resultStoreUserGroup =
                    firebaseDataSource.insertUserGroup(userGroup.let(UserGroupMapper::fromModelToEntity))
                resultInsertUserLogin && resultStoreUser && resultStoreUserGroup
            } else {
                insertUser(user)
            }
        } else {
            false
        }
    }

    override suspend fun doLogin(userLogin: UserLogin) =
        firebaseDataSource.login(userLogin.email, userLogin.pwd)

    override suspend fun fetchUser(forceCall: Boolean): User? =
        if (CacheUserDataSource.fetchUser() == null || forceCall) {
            firebaseDataSource.fetchUser(fetchUserLogin()?.id ?: "")?.let { user ->
                UserMapper.fromUserEntityToUserModel(
                    entity = user,
                    userGroups = fetchUserGroupsFromEntity(user)
                )
            }.also(CacheUserDataSource::insertUser)
        } else {
            CacheUserDataSource.fetchUser()
        }

    override suspend fun fetchUserByName(username: String): User? =
        if (CacheUserDataSource.fetchUser()?.username == username) {
            CacheUserDataSource.fetchUser()
        } else {
            firebaseDataSource.fetchUserByName(username)?.let { user ->
                UserMapper.fromUserEntityToUserModel(
                    entity = user,
                    userGroups = fetchUserGroupsFromEntity(user)
                )
            }.also(CacheUserDataSource::insertUser)
        }

    override suspend fun fetchUserById(userId: String): User? =
        firebaseDataSource.fetchUser(userId)?.let {
            UserMapper.fromUserEntityToUserModel(
                entity = it,
                userGroups = fetchUserGroupsFromEntity(it)
            )
        }

    override suspend fun fetchUserAvatarTypes(): UserAvatarTypes? =
        apiDataSource.fetchAvatarTypes(AVATAR_URL)?.let(UserMapper::fromUserAvatarTypesEntityToModel)

    override suspend fun insertUser(user: User, shouldCache: Boolean): Boolean {
        val result = firebaseDataSource.insertUser(UserMapper.fromUserModelToEntity(user))
        if (result && shouldCache) CacheUserDataSource.insertUser(user)
        return result
    }

    override suspend fun insertUserAvatar(avatar: UserAvatar): Boolean =
        fetchUser()?.let { user ->
            user.avatar = avatar
            updateUser(user).also {
                if (it) {
                    CacheUserDataSource.clear()
                    fetchUser()
                }
            }
        } ?: false

    override suspend fun updateUser(user: User): Boolean =
        firebaseDataSource.insertUser(user.let(UserMapper::fromUserModelToEntity))

    override suspend fun doLogout() = firebaseDataSource.logout()

    // -- Private functions -- //

    private suspend fun fetchUserGroupsFromEntity(entity: UserEntity): List<UserGroup> =
        firebaseDataSource.fetchUserGroups(entity)?.map { groupEntity ->
            UserGroupMapper.fromEntityToModel(
                entity = groupEntity,
                members = groupEntity.members?.mapNotNull {
                    fetchUserGroupMemberById(
                        userId = it,
                        groupId = groupEntity.id ?: "",
                        groupAdminId = groupEntity.userAdmin ?: ""
                    )
                } ?: emptyList())
        } ?: emptyList()

    private suspend fun fetchUserGroupMemberById(
        userId: String,
        groupId: String,
        groupAdminId: String
    ): UserGroupMember? =
        firebaseDataSource.fetchUser(userId)?.let {
            UserMapper.fromUserEntityToUserGroupMemberModel(it, groupId, groupAdminId)
        }
}