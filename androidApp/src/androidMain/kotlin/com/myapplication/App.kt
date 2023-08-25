package com.myapplication

import android.app.Application
import core.appContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
