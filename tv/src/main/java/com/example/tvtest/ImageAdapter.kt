package com.example.tvtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class ImageAdapter(private  val images: List<ImageModel>):
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        private var imgData:List<ImageModel> = images

        inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.displayImg)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imgUrl = imgData[position].imgUrl
        Picasso
            .get()
            .load(imgUrl)
            .into(holder.imageView)
    }

    fun updateData(newData: List<ImageModel>) {
        imgData = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return imgData.size
    }

}

data class ImageModel(val imgUrl: String)