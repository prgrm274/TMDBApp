package com.programmer2704.movapp.forfragment

import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentFactoryModule {
    @Binds
    abstract fun bindFragmentFactory(factory: DefaultFragmentFactory): FragmentFactory
}