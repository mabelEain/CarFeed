package com.android.mabel.restservice


import com.android.mabel.model.JsonResponse
import com.android.mabel.di.DaggerApiComponent

import io.reactivex.Single
import javax.inject.Inject

class FeedService {
    @Inject
    lateinit var api: FeedApi
    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getArticles(): Single<JsonResponse>{
        return api.getArticle()
    }

}