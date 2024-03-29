package com.example.tvtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoTools

class MainActivity : FragmentActivity() {

    private lateinit var title: TextView
    private lateinit var welcomeImg: ImageView
    private lateinit var getStartedBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PicassoTools.clearCache(Picasso.get())
        welcomeImg = findViewById(R.id.welcomeImg)
        title = findViewById(R.id.title)
        getStartedBtn = findViewById(R.id.getStartedBtn)

        loadImg()

        getStartedBtn.setOnClickListener {
            val nextScreen = Intent(this@MainActivity, ImagePreviewActivity::class.java)
            startActivity(nextScreen)
        }
    }

    fun loadImg() {
//        val urlStr = "https://images.pexels.com/photos/346529/pexels-photo-346529.jpeg"
        val urlStr = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        Picasso.get().load(urlStr).networkPolicy(NetworkPolicy.NO_CACHE).into(welcomeImg)
    }


}