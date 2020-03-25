package bemobile.splanes.com.aikver.framework.helper

import bemobile.splanes.com.aikver.domain.Serie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson

class FirebaseHelper {

    companion object {
        const val REF_SERIE = "db/serie"
        const val REF_USER = "db/user"
    }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun createUser(username: String,
                   password: String,
                   onSuccess: (Boolean) -> Unit,
                   onError: (Throwable) -> Unit) {

        val email = "$username@xyz.com"
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onSuccess(task.isSuccessful)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun signIn(username: String,
               password: String,
               onSuccess: (Boolean) -> Unit,
               onError: (Throwable) -> Unit) {

        val email = "$username@xyz.com"
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onSuccess(task.isSuccessful)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun signOut() = auth.signOut()

    fun insertSerie(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        database.getReference(REF_SERIE).child(serie.id).setValue(
            Gson().toJson(serie)
        ) { error, _ ->
            if (error != null) onFailure(error.toException()) else onSuccess(true)
        }
    }

    fun fetchAll(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        database.getReference(REF_SERIE).addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) = onFailure(error.toException())

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Serie>()
                snapshot.children.forEach { serieSnapshot ->
                    val value = serieSnapshot.value as String?
                    if (value != null) list.add(Gson().fromJson(value, Serie::class.java))
                }
                onSuccess(list)
            }
        })
    }

    fun updateSerie(
        serie: Serie,
        onSuccess: (Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        database.getReference(REF_SERIE).child(serie.id).setValue(
            Gson().toJson(serie)
        ) { error, _ ->
            if (error != null) onFailure(error.toException())
            else onSuccess(true)
        }
    }

    fun deleteSerie(
        serie: Serie,
        onSuccess: (Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        database.getReference(REF_SERIE).child(serie.id).removeValue()
        { error, _ ->
            if (error != null) onFailure(error.toException())
            else onSuccess(true)
        }
    }
}