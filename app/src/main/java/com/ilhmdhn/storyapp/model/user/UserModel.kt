package com.ilhmdhn.storyapp.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String? = null,
    var userId: String? = null,
    var token: String? = null,
    var isLogin: Boolean = false
): Parcelable
