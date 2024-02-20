package com.example.tvtest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class ImageAdapter(private val images: List<MediaItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var imgData: List<MediaItem> = images

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MediaType.IMAGE.ordinal -> ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.display_image, parent, false))
            MediaType.VIDEO.ordinal -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.player, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imgUrl = imgData[position].mediaUrl
        when (holder) {
            is ImageViewHolder -> {
                Picasso
                    .get()
                    .load(imgUrl)
                    .into(holder.imageView)
            }
            is VideoViewHolder -> {
                val context = holder.itemView.context
                val playerView: PlayerView = holder.itemView.findViewById(R.id.playerView)
                val player = ExoPlayer.Builder(context).build()

                val mediaItem = androidx.media3.common.MediaItem.fromUri(imgUrl)
                player.setMediaItem(mediaItem)
                player.prepare()
                playerView.player = player
                player.play()
//                player.playWhenReady = true

            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<MediaItem>) {
        imgData = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return imgData.size
    }

    override fun getItemViewType(position: Int): Int {
        return imgData[position].mediaType.ordinal
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if(holder is VideoViewHolder) {
            holder.playerView.player?.release()
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.displayImg)
    }

    inner class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.playerView)
    }

}
