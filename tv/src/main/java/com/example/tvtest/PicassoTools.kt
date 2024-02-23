package com.squareup.picasso
class PicassoTools {
    companion object{
        fun clearCache (p:Picasso) {
            p.cache.clear()
        }
    }
}