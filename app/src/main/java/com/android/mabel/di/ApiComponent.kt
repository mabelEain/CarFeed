package com.android.mabel.di

import com.android.mabel.restservice.FeedService
import com.android.mabel.viewmodel.ListViewModel


import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: FeedService)
    fun inject(viewModel: ListViewModel)
}