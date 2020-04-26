package revolhope.splanes.com.aikver.framework.datasourceimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.entity.UserEntity
import revolhope.splanes.com.core.data.entity.UserGroupEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseDataSourceImpl : FirebaseDataSource {

    companion object {
        const val REF_SERIE = "db/serie"
        const val REF_USER = "db/user"
        const val REF_GROUP = "db/group"
    }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override suspend fun login(email: String, pwd: String): Boolean =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { cont.resume(it.isSuccessful) }
        }

    override suspend fun register(email: String, pwd: String): Boolean =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { cont.resume(it.isSuccessful) }
        }

    override suspend fun insertUser(userEntity: UserEntity): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_USER)
                .child(userEntity.id ?: "")
                .setValue(Gson().toJson(userEntity)) { error, _ ->
                    cont.resume(error == null)
                }
        }

    override suspend fun fetchUser(id: String): UserEntity? =
        suspendCoroutine { cont ->
            database.getReference(REF_USER).child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            cont.resume(
                                Gson().fromJson(
                                    dataSnapshot.value as String,
                                    UserEntity::class.java
                                )
                            )
                        } catch (e: Exception) {
                            cont.resume(null)
                        }
                    }
                })
        }

    override suspend fun fetchUserByName(username: String): UserEntity? =
        suspendCoroutine { cont ->
            database.getReference(REF_USER)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            var user: UserEntity?
                            for (snap in dataSnapshot.children) {
                                user = Gson().fromJson(snap.value as String, UserEntity::class.java)
                                if (user?.username == username) {
                                    cont.resume(user)
                                    return
                                }
                            }
                            cont.resume(null)
                        } catch (e: Exception) {
                            cont.resume(null)
                        }
                    }
                })
        }

    override suspend fun insertUserGroup(userGroupEntity: UserGroupEntity): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_GROUP)
                .child(userGroupEntity.id ?: "")
                .setValue(Gson().toJson(userGroupEntity)) { error, _ ->
                    cont.resume(error == null)
                }
        }

    override suspend fun fetchUserGroup(
        userGroupId: String
    ): UserGroupEntity? =
        suspendCoroutine { cont ->
            database.getReference(REF_GROUP)
                .child(userGroupId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) =
                        cont.resume(
                            (dataSnapshot.value as String?)?.let {
                                Gson().fromJson(it, UserGroupEntity::class.java)
                            }
                        )
                })
        }

    override suspend fun fetchUserGroups(
        userEntity: UserEntity,
        limitTo: Int
    ): List<UserGroupEntity>? =
        suspendCoroutine { cont ->
            database.getReference(REF_GROUP)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) =
                        cont.resume(
                            dataSnapshot.children.filter { data ->
                                userEntity.userGroups.any { it == data.key }
                            }.map {
                                Gson().fromJson(it.value as String, UserGroupEntity::class.java)
                            }
                        )
                })
        }

    override suspend fun deleteUserGroup(userGroupEntity: UserGroupEntity): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_GROUP)
                .child(userGroupEntity.id ?: "")
                .removeValue { error, _ ->
                    cont.resume(error != null)
                }
        }


    override suspend fun logout() = auth.signOut()
}