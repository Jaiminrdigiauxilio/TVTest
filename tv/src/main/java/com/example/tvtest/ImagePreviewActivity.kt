package com.example.tvtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagePreviewActivity : FragmentActivity() {

    private  lateinit var viewPager: ViewPager2
    private  lateinit var imgAdapter: ImageAdapter
    private  val imgList = listOf(
        ImageModel("https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8fA%3D%3D"),
        ImageModel("https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg"),
        ImageModel("https://images.hdqwalls.com/wallpapers/yellowstone-national-park-hd-qs.jpg"),
        ImageModel("https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D"),
        ImageModel("https://images.unsplash.com/photo-1674407729043-c21b71fded37?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MTh8MTMxOTA0MHx8ZW58MHx8fHx8"),
    )
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        viewPager = findViewById(R.id.viewPager)
        imgAdapter = ImageAdapter(imgList)
        viewPager.adapter = imgAdapter

        coroutineScope.launch {
            while (isActive) {
                delay(2500)
                withContext(Dispatchers.Main) {
                    val currentItem = viewPager.currentItem
                    viewPager.setCurrentItem((currentItem + 1) % imgList.size, true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}