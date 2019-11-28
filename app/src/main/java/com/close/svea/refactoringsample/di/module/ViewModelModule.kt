package com.close.svea.refactoringsample.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.close.svea.refactoringsample.di.util.ViewModelKey
import com.close.svea.refactoringsample.ui.MainViewModel
import com.close.svea.refactoringsample.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

}