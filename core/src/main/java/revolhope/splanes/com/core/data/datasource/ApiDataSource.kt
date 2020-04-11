package revolhope.splanes.com.core.data.datasource

import retrofit2.http.GET
import retrofit2.http.Url
import revolhope.splanes.com.core.data.entity.UserAvatarTypesEntity

interface ApiDataSource {

    // Avatar api
    @GET
    suspend fun fetchAvatarTypes(@Url url: String) : UserAvatarTypesEntity?

    // Serie & movie api

}