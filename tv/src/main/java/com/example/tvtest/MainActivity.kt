package com.example.tvtest

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.media3.exoplayer.ExoPlayer
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoTools
import java.lang.Exception
import kotlin.reflect.typeOf

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

        val p = Picasso.get()
        PicassoTools.clearCache(p)
        clearCacheDir(this)

        if (isDeviceConnectedToNetwork()) {
            loadImg()
            getStartedBtn.setOnClickListener {
                val nextScreen = Intent(this@MainActivity, ImagePreviewActivity::class.java)
                startActivity(nextScreen)
            }

        } else {
            val errSrc = Intent(this@MainActivity, ErrorScreen::class.java)
            startActivity(errSrc)

        }



    }

//    clears all the cache of in the android memory allocation for this app
    private fun clearCacheDir(context: Context) {
        try {
            val cacheDir = context.cacheDir
            cacheDir.deleteRecursively()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    loads image on main home screen
    private fun loadImg() {
//        val urlStr = "https://images.pexels.com/photos/346529/pexels-photo-346529.jpeg"
        val urlStr = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        Picasso.get().load(urlStr).into(welcomeImg)
        //Debugging code
    }


//    checks internet connectivity is there or not
    private fun isDeviceConnectedToNetwork(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Log.d("Internet","-/-/ connectivity estd")
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }

    }
}


//"https://images.unsplash.com/photo-1610552050890-fe99536c2615?q=80&w=2707&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
//https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D
//https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg
//https://images.unsplash.com/photo-1610552050890-fe99536c2615?q=80&w=2707&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D
//    https://images.hdqwalls.com/wallpapers/yellowstone-national-park-hd-qs.jpg
//    private val imgList = listOf(
//        ImageModel("https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8fA%3D%3D"),
//        ImageModel("https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg"),
//        ImageModel("https://images.hdqwalls.com/wallpapers/yellowstone-national-park-hd-qs.jpg"),
//        ImageModel("https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D"),
//        ImageModel("https://images.unsplash.com/photo-1674407729043-c21b71fded37?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MTh8MTMxOTA0MHx8ZW58MHx8fHx8"),
//    )