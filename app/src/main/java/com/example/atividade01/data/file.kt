package com.example.atividade01.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class File(
        val name: String,
        val isInternal: Boolean
): Parcelable