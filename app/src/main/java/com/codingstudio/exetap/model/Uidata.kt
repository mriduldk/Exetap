package com.codingstudio.exetap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Uidata(
    val hint: String?,
    val key: String?,
    val uitype: String?,
    val value: String?
): Parcelable