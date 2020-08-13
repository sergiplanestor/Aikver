package revolhope.splanes.com.aikver.framework.datasourceimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.entity.content.CustomContentEntity
import revolhope.splanes.com.core.data.entity.content.CustomMovieEntity
import revolhope.splanes.com.core.data.entity.content.CustomSerieEntity
import revolhope.splanes.com.core.data.entity.user.UserEntity
import revolhope.splanes.com.core.data.entity.user.UserGroupEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseDataSourceImpl : FirebaseDataSource {

    companion object {
        const val REF_SERIE = "db/content/serie"
        const val REF_MOVIE = "db/content/movie"
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
                    cont.resume(error == null)
                }
        }

    override suspend fun insertSerie(
        userGroupEntity: UserGroupEntity,
        serie: CustomSerieEntity
    ): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_SERIE)
                .child(userGroupEntity.id ?: "")
                .push()
                .setValue(Gson().toJson(serie)) { error, _ ->
                    cont.resume(error == null)
                }
        }

    override suspend fun insertMovie(
        userGroupEntity: UserGroupEntity,
        movie: CustomMovieEntity
    ): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_MOVIE)
                .child(userGroupEntity.id ?: "")
                .push()
                .setValue(Gson().toJson(movie)) { error, _ ->
                    cont.resume(error == null)
                }
        }

    override suspend fun deleteSerie(
        userGroupEntity: UserGroupEntity,
        serie: CustomSerieEntity
    ): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_SERIE)
                .child(userGroupEntity.id ?: "")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(false)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.find {
                            val entity =
                                Gson().fromJson(it.value as String, CustomSerieEntity::class.java)
                            entity.contentId == serie.contentId
                        }?.key?.let {
                            database.getReference(REF_SERIE)
                                .child(userGroupEntity.id ?: "")
                                .child(it)
                                .removeValue { error, _ ->
                                    cont.resume(error == null)
                                }
                        } ?: cont.resume(false)
                    }
                })
        }

    override suspend fun deleteMovie(
        userGroupEntity: UserGroupEntity,
        movie: CustomMovieEntity
    ): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_MOVIE)
                .child(userGroupEntity.id ?: "")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(false)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.find {
                            val entity =
                                Gson().fromJson(it.value as String, CustomMovieEntity::class.java)
                            entity.contentId == movie.contentId
                        }?.key?.let {
                            database.getReference(REF_MOVIE)
                                .child(userGroupEntity.id ?: "")
                                .child(it)
                                .removeValue { error, _ ->
                                    cont.resume(error == null)
                                }
                        } ?: cont.resume(false)
                    }
                })
        }

    override suspend fun fetchGroupContent(
        groupId: String
    ): List<CustomContentEntity>? {
        val series = fetchGroupSeries(groupId)
        val movies = fetchGroupMovies(groupId)
        return mutableListOf<CustomContentEntity>().apply {
            series?.let { addAll(it) }
            movies?.let { addAll(it) }
        }
    }

    override suspend fun fetchGroupSeries(
        groupId: String
    ): List<CustomSerieEntity>? =
        suspendCoroutine { cont ->
            database.getReference(REF_SERIE)
                .child(groupId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) =
                        cont.resume(
                            dataSnapshot.children.map {
                                Gson().fromJson(it.value as String, CustomSerieEntity::class.java)
                            }
                        )
                })
        }

    override suspend fun fetchGroupMovies(
        groupId: String
    ): List<CustomMovieEntity>? =
        suspendCoroutine { cont ->
            database.getReference(REF_MOVIE)
                .child(groupId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) =
                        cont.resume(
                            dataSnapshot.children.map {
                                Gson().fromJson(it.value as String, CustomMovieEntity::class.java)
                            }
                        )
                })
        }

    override suspend fun logout() = auth.signOut()
}