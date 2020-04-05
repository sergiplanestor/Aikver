package revolhope.splanes.com.aikver.presentation.addserie
/*

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bemobile.splanes.com.core.domain.Platform
import bemobile.splanes.com.core.domain.Serie
import revolhope.splanes.com.aikver.framework.helper.ImageLoaderHelper
import revolhope.splanes.com.aikver.framework.helper.SharedPreferencesHelper
import bemobile.splanes.com.core.interactor.AddSerieUseCase
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class AddSerieViewModel : BaseViewModel(), KoinComponent {

    private val sharedPreferencesHelper: SharedPreferencesHelper by inject()
    private val imageLoaderHelper: ImageLoaderHelper by inject()
    private val addSerieUseCase: AddSerieUseCase by inject()

    fun searchImages(query: String) : LiveData<List<String>> {

        val liveData = MutableLiveData<List<String>>()

        imageLoaderHelper.search(query, object : ImageLoaderHelper.OnQueryDone {
            override fun onResponse() {
                liveData.postValue(imageLoaderHelper.links)
            }
        })

        return liveData
    }

    fun addSerie(
        title: String?,
        imageUrl: String?,
        platform: bemobile.splanes.com.core.domain.Platform?,
        category: String?,
        score: Int
    ) : LiveData<Boolean> {

        val liveData = MutableLiveData<Boolean>()

        runBlocking(Dispatchers.IO) {
            val user = sharedPreferencesHelper.getString(SharedPreferencesHelper.PREF_USR)
            addSerieUseCase.invoke(
                serie = bemobile.splanes.com.core.domain.Serie(
                    title = title!!,
                    imageUrl = imageUrl,
                    platform = platform,
                    category = category,
                    score = score,
                    userCreator = user,
                    userScorers = mutableListOf(user)
                ),
                onSuccess = {
                    liveData.postValue(it)
                },
                onFailure = errorConsumer()
            )
        }

        return liveData
    }
}*/
