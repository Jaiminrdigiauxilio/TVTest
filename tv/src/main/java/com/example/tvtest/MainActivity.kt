package com.example.tvtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.media3.exoplayer.ExoPlayer
import com.squareup.picasso.Picasso

class MainActivity : FragmentActivity() {

    private lateinit var title: TextView
    private lateinit var welcomeImg: ImageView
    private lateinit var getStartedBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeImg = findViewById(R.id.welcomeImg)
        title = findViewById(R.id.title)
        getStartedBtn = findViewById(R.id.getStartedBtn)

        loadImg()
        getStartedBtn.setOnClickListener {
            val nextScreen = Intent(this@MainActivity, ImagePreviewActivity::class.java)
            startActivity(nextScreen)
        }

    }

    private fun loadImg() {
//        val urlStr = "https://images.pexels.com/photos/346529/pexels-photo-346529.jpeg"
        val urlStr = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        Picasso.get().load(urlStr).into(welcomeImg)
        //Debugging code
    }

}


//"https://images.unsplash.com/photo-1610552050890-fe99536c2615?q=80&w=2707&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
//https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D
//https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg
//https://images.unsplash.com/photo-1610552050890-fe99536c2615?q=80&w=2707&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D

