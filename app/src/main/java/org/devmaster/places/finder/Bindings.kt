package org.devmaster.places.finder

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import java.io.InputStream

object Bindings {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(view: ImageView, url: String) {
        val okHttpClient = OkHttpClientFactory.getOkHttpClient()
        Glide.get(view.context).registry
                .replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))

        Glide.with(view.context).load(url).into(view)
    }

}


