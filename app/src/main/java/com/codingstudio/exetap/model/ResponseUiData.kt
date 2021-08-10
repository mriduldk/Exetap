package com.codingstudio.exetap.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUiData(
    @SerializedName("heading-text")
    val heading_text: String?,
    @SerializedName("logo-url")
    val logo_url: String?,
    val uidata: List<Uidata>
): Parcelable