package com.android.mabel.restservice

import com.android.mabel.model.JsonResponse
import io.reactivex.Single
import retrofit2.http.GET

interface FeedApi {

    @GET("article/get_articles_list")
    fun getArticle(): Single<JsonResponse>
}
