package com.close.svea.refactoringsample.base

import android.app.Application

import com.close.svea.refactoringsample.di.AppComponent
import com.close.svea.refactoringsample.di.DaggerAppComponent

class BaseApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
