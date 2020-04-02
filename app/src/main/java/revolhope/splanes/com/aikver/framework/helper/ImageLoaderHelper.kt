package revolhope.splanes.com.aikver.framework.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * @author sergiplanes on 2020-02-17
 */
class ImageLoaderHelper(private val context: Context) {

    companion object {
        const val BASE_URL = "https://www.googleapis.com/customsearch/v1"
        const val API_KEY = "AIzaSyAZpA9LpTDbP0TwVmwjqTnVANzuGoAAsMM"
        const val ENGINE = "005962009366625936766:kycogbhdmhj"
    }

    private var currentIndex: Int = 0
    val links: MutableList<String> = listOf<String>().toMutableList()

    fun search(query: String, onQueryDone: OnQueryDone? = null) {

        val queryFormatted = query.replace(" ", "+") + "+serie+poster"
        val url = URL(
            BASE_URL +
                    "?key=$API_KEY&" +
                    "cx=$ENGINE&" +
                    "q=$queryFormatted&" +
                    "searchType=image"
        )

        GlobalScope.launch(Dispatchers.IO) {

            val connection = url.openConnection()

            var line: String?
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))

            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
            }

            val json = JSONObject(builder.toString())
            val items = json["items"] as JSONArray

            links.clear()

            for (index in 0 until items.length()) {
                links.add(items.getJSONObject(index)["link"] as String)
            }

            onQueryDone?.onResponse()
        }
    }

    fun next(imageView: ImageView) : Boolean = next(imageView, links[currentIndex])

    private fun next(imageView: ImageView, link: String) : Boolean {

        try {
            Glide.with(context).load(link).into(imageView)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

        currentIndex++
        return if (currentIndex >= links.size) {
            currentIndex = 0
            false
        } else true
    }

    interface OnQueryDone {
        fun onResponse()
    }

}