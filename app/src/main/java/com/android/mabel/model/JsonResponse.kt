package com.android.mabel.model

data class JsonResponse (
    val status: String,
    val content: List<FeedModel>
)