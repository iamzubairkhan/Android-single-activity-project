package com.close.svea.refactoringsample.base

import android.app.Application

import com.close.svea.refactoringsample.di.component.AppComponent
import com.close.svea.refactoringsample.di.component.DaggerAppComponent

class BaseApplication : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppInjector(): AppComponent {
            return appComponent
        }

    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
