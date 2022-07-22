package com.nyller.android.mach3.models

import java.io.Serializable

data class User(
    var nome : String?= null,
    var email : String?= null,
    var senha : String?= null,
    var id : String?= null
) : Serializable
