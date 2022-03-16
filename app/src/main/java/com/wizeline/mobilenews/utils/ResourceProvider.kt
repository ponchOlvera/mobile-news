package com.wizeline.mobilenews.utils

import android.content.Context

object ResourceProvider {

    private lateinit var applicationContext: Context
    fun initialize(context: Context) {
        applicationContext = context
    }

    fun getString(id: Int) =
        applicationContext.getString(id)
}