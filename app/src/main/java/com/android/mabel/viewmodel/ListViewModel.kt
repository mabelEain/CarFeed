package com.android.mabel.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mabel.di.DaggerApiComponent
import com.android.mabel.model.FeedModel
import com.android.mabel.model.JsonResponse
import com.android.mabel.restservice.FeedService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class ListViewModel : ViewModel() {
    @Inject
    lateinit var feedService: FeedService

    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()
    val articles = MutableLiveData<List<FeedModel>>()
    val feedLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    fun refresh() {
        fetchArticles()
    }


    private fun fetchArticles() {
        loading.value = true
        disposable.add(
            feedService.getArticles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonResponse>() {
                    override fun onSuccess(data: JsonResponse) {
                        Log.d("data ", "" + data)
                        articles.value = data.content
                        feedLoadError.value = false
                        loading.value = false
                    }
                    override fun onError(e: Throwable) {
                        feedLoadError.value = true
                        loading.value = false
                        Log.d("error ", "" + e.printStackTrace())
                    }
                })
        )
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}