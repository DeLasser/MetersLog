package ru.mininn.meterslog.data.api

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CntDevApi {

    @Headers("Content-Type: application/json")
    @POST("n")
    fun postMeterData(@Body meters: JsonObject): Single<Void>
}