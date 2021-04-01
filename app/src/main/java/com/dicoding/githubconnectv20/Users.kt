package com.dicoding.githubconnectv20

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var name: String? = null,
    var avatar: String? = null,

):Parcelable
