package com.example.tvtest

data class MediaItem(val mediaUrl: String, val duration: Long, val mediaType: MediaType)

enum class MediaType {
    IMAGE,
    VIDEO,
}

//data class ImageModel(val imgUrl: String, val duration: Long)