package com.codingstudio.exetap.viewModel


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.*
import com.codingstudio.exetap.activity.AppApplication
import com.codingstudio.exetap.model.Resource
import com.codingstudio.exetap.model.ResponseUiData
import com.codingstudio.exetap.repository.ItemRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ItemViewModel(
    application: Application,
    private val itemRepository: ItemRepository
): AndroidViewModel(application) {

    private val TAG = "ItemViewModel"

    val uiData : MutableLiveData<Resource<ResponseUiData>> = MutableLiveData()

    init {
        getUiData()
    }


    /**
     * Call API and get Ui Data
     */
    private fun getUiData() = viewModelScope.launch {
        uiData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = itemRepository.getUiData()
                uiData.postValue(handleENewsPaperResponse(response))
            } else {
                uiData.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> uiData.postValue(Resource.Error("Network Failure"))
                else -> uiData.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleENewsPaperResponse(responseLive: Response<ResponseUiData>) : Resource<ResponseUiData> {

        if (responseLive.isSuccessful){
            responseLive.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(responseLive.message())
    }





    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<AppApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}