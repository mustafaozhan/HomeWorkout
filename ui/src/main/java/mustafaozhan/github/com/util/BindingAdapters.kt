package mustafaozhan.github.com.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.SearchViewBindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import mustafaozhan.github.com.di.view.GlideApp
import mustafaozhan.github.com.ui.R

@BindingAdapter("visibility")
fun View.visibility(visible: Boolean) {
    visibility = if (visible) {
        bringToFront()
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("imageUrl", "placeholderImage", "imageRadius", requireAll = false)
fun ImageView.loadImageUrl(
    imageUrl: String?,
    placeholderImage: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.ic_glide_error_placeholder
    ),
    imageCornerRadius: Float?
) {

    GlideApp
        .with(context)
        .load(imageUrl)
        .fallback(placeholderImage)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (imageCornerRadius != null) {
                transform(CenterCrop(), RoundedCorners(imageCornerRadius.toInt()))
            } else {
                centerCrop()
            }
        }
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .into(this)
}

@BindingAdapter("onQueryTextChange")
fun SearchView.setOnQueryTextListener(change: SearchViewBindingAdapter.OnQueryTextChange) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false
        override fun onQueryTextChange(newText: String?) = change.onQueryTextChange(newText)
    })
}
