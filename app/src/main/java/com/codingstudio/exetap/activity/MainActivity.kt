package com.codingstudio.exetap.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codingstudio.exetap.R
import com.codingstudio.exetap.model.Resource
import com.codingstudio.exetap.repository.ItemRepository
import com.codingstudio.exetap.util.Constants.INTENT_DATA
import com.codingstudio.exetap.viewModel.ItemViewModel
import com.codingstudio.exetap.viewModel.ItemViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel : ItemViewModel
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemRepository = ItemRepository()

        val viewModelProviderFactory =
                ItemViewModelProviderFactory(
                        application = application,
                        itemRepository = itemRepository,
                )

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ItemViewModel::class.java)

        observeUiResponse()


    }



    private fun observeUiResponse(){

        viewModel.uiData.observe(this, Observer { response ->

            when (response) {

                is Resource.Success -> {

                    hideProgressbar()
                    response.data?.let { itemResponse ->

                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra(INTENT_DATA, itemResponse)
                        startActivity(intent)
                        finish()
                    }
                }

                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { errorMessage ->
                        Log.e(TAG, "An Error Occurred : $errorMessage")
                    }
                }

                is Resource.Loading -> {
                    showProgressbar()
                }
            }

        })

    }







    private fun hideProgressbar(){
        progress_circular.visibility = View.GONE
    }

    private fun showProgressbar(){
        progress_circular.visibility = View.VISIBLE
    }




}