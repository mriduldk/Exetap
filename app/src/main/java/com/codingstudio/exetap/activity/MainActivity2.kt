package com.codingstudio.exetap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.codingstudio.exetap.R
import com.codingstudio.exetap.model.ResponseUiData
import com.codingstudio.exetap.util.Constants.INTENT_DATA
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val responseData = intent.getParcelableExtra<ResponseUiData>(INTENT_DATA)

        responseData?.let { it ->
            renderUiComponent(it)
        }
    }


    private fun renderUiComponent(itemResponse: ResponseUiData) {

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.TOP

        val linearLayoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        /**
         * Image View for Logo
         */
        val imageView = ImageView(this)
        imageView.adjustViewBounds = true

        Glide.with(this).load(itemResponse.logo_url).into(imageView)

        val param2 = LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT)
        imageView.layoutParams = param2
        linearLayout.addView(imageView)


        /**
         * Text View for Heading
         */
        val textView = TextView(this)
        textView.textSize = 20F

        textView.text = itemResponse.heading_text
        textView.setPadding(16)
        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = param
        linearLayout.addView(textView)


        for (item in itemResponse.uidata){


            when(item.uitype){

                "label" -> {
                    val textView = TextView(this)
                    textView.text = item.value
                    textView.setPadding(16)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    textView.layoutParams = params
                    linearLayout.addView(textView)
                }

                "edittext" -> {
                    val editText = EditText(this)
                    editText.hint = item.hint
                    editText.setPadding(16)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    editText.layoutParams = params
                    linearLayout.addView(editText)
                }

                "button" -> {
                    val button = Button(this)
                    button.text = item.value
                    button.setPadding(16)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)


                    button.layoutParams = params
                    linearLayout.addView(button)
                }
            }

            setContentView(linearLayout, linearLayoutParam)

        }



    }



}