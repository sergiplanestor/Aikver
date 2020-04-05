package revolhope.splanes.com.aikver.framework.datasourceimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import java.lang.Exception
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

    override suspend fun insertUser(user: User): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_USER).child(user.id).setValue(Gson().toJson(user))
            { error, _ ->
                cont.resume(error == null)
            }
        }

    override suspend fun fetchUser(id: String): User? =
        suspendCoroutine { cont ->
            database.getReference(REF_USER).child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            cont.resume(
                                Gson().fromJson(
                                    dataSnapshot.value as String,
                                    User::class.java
                                )
                            )
                        } catch (e: Exception) {
                            cont.resume(null)
                        }
                    }
                })
        }

    override suspend fun fetchUserByName(username: String): User? =
        suspendCoroutine { cont ->
            database.getReference(REF_USER)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) = cont.resume(null)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            var user: User?
                            for (snap in dataSnapshot.children) {
                                user = Gson().fromJson(snap.value as String, User::class.java)
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

    override suspend fun insertUserGroup(userGroup: UserGroup): Boolean =
        suspendCoroutine { cont ->
            database.getReference(REF_GROUP)
                .child(userGroup.id)
                .setValue(Gson().toJson(userGroup)) { error, _ ->
                    cont.resume(error == null)
                }
        }

    override suspend fun logout() = auth.signOut()
}