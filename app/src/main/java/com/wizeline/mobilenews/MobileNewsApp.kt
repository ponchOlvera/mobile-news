package com.wizeline.mobilenews

import android.app.Application
import com.wizeline.mobilenews.utils.ResourceProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobileNewsApp: Application(){
    override fun onCreate() {
        ResourceProvider.initialize(applicationContext)
        super.onCreate()
    }
}
