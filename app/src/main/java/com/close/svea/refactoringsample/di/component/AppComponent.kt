package com.close.svea.refactoringsample.di.component

import android.app.Application
import com.close.svea.refactoringsample.di.module.AppModule
import com.close.svea.refactoringsample.di.module.ViewModelModule
import com.close.svea.refactoringsample.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
