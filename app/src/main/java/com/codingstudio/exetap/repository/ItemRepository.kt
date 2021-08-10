package com.codingstudio.exetap.repository

import com.codingstudio.exetap.api.RetrofitInstance


class ItemRepository {

    suspend fun getUiData() = RetrofitInstance.api.getUiData()


}