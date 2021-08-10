package com.codingstudio.exetap.api


import com.codingstudio.exetap.model.ResponseUiData
import retrofit2.Response
import retrofit2.http.*


interface RetrofitAPI{

    @GET("mobileapps/android_assignment.json")
    suspend fun getUiData() : Response<ResponseUiData>


}