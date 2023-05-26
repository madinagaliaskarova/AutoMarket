package com.example.technicaltaskforitsurf.ui.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageWithPlaceholder(url: String, placeholderResId: Int) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(placeholderResId))
        .into(this)
}