package com.android.mabel.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.android.mabel.R


fun ImageView.loadImage(uri: String?) {

    val options = RequestOptions()
        .placeholder(R.drawable.ic_reload)
        .error(R.drawable.ic_error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .centerCrop()
        .into(this)
}
