package com.dicoding.githubconnectv20

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class UserProfile(
        var name: String? = null,
        var username: String? = null,
        var avatar: String? = null,
        var company: String?= null,
)