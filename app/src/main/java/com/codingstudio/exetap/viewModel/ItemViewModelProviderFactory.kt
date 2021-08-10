package com.codingstudio.exetap.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingstudio.exetap.repository.ItemRepository

class ItemViewModelProviderFactory(
    private val application: Application,
    private val itemRepository: ItemRepository,
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass){

            ItemViewModel::class.java -> {
                ItemViewModel(
                    application = application,
                    itemRepository = itemRepository
                ) as T
            }


            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }

}