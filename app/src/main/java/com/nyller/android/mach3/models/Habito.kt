package com.nyller.android.mach3.models

import java.io.Serializable

data class Habito(
    var nome : String ?= null,
    var turno : String ?= null,
    var categoria : String ?= null
): Serializable

