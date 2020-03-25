package bemobile.splanes.com.aikver.presentation.common.widget.imagepager

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import bemobile.splanes.com.aikver.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition


class ImagePagerAdapter(
    private val context: Context,
    private val links: MutableList<String>,
    private val itemRemovedCallback: (position: Int) -> Unit
) : RecyclerView.Adapter<ImagePagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.component_image_pager_holder, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = links.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(links[position])
            .into(object : CustomViewTarget<ImageView, Drawable>(holder.imageView) {

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    links.removeAt(position)
                    notifyItemRemoved(position)
                    itemRemovedCallback(position)
                }

                override fun onResourceCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Drawable,
                                             transition: Transition<in Drawable>?) {
                    holder.imageView.setImageDrawable(resource)
                }
            })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

}