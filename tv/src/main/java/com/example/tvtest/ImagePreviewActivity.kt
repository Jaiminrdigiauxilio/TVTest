package com.example.tvtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer


class ImagePreviewActivity : FragmentActivity() {

    private  lateinit var viewPager: ViewPager2
    private  lateinit var imgAdapter: ImageAdapter
    private val fetchedImgList = mutableListOf<Map<String, Any>>()
    private val imgList = mutableListOf<MediaItem>()
    private val durationList = mutableListOf<Long>(
        2500,
    )
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val TAG = "ImageFetch"
    private val TAGI = "VideoFetch"
    private var type: MediaType = MediaType.IMAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        viewPager = findViewById(R.id.viewPager)

        val dataReady = CompletableDeferred<Unit>()
        listenFirebaseDb(dataReady)

        GlobalScope.launch {

            imgAdapter = ImageAdapter(imgList)
            viewPager.adapter = imgAdapter

            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            var currentItem = 0

            while (isActive) {
//                var currentDelay = 0
                delay(durationList[currentItem])
                withContext(Dispatchers.Main) {
//                    currentDelay++
//                    val currentItem = viewPager.currentItem
//                    Log.d(TAG, "/-/-/-/-/ Printing currentItem duration ${imgList[currentItem].duration}")
                    viewPager.setCurrentItem((currentItem + 1) % imgList.size, true)
                    if (imgList.size - 1 > currentItem) {
                        currentItem++
                    } else {
                        currentItem = 0
                    }

                }
            }
        }
    }


    //      listening code for firebase
    private fun listenFirebaseDb(dataReady: CompletableDeferred<Unit>) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("templates")


        val registration = docRef.addSnapshotListener { snapshot, err ->
            if (err != null) {
                Log.w("fire", "Listen Failed",err)
                println("-/-/-/-/-/Listen Failed $err")
                return@addSnapshotListener
            }

            if(snapshot != null) {
                // emptying list(s) in order to have no duplicates
                fetchedImgList.clear()
                durationList.clear()

                for(docs in snapshot) {
                    // Adding data to local List
                    fetchedImgList.add(docs.data)
                    Log.d(TAG, "-/-/-/-/-/-/-/-/-/- document Data: ${docs.data}")
                    val duration = docs.data["duration"].toString()
                    durationList.add((duration.toLong()))
                }
            } else {
                Log.d(TAG,"Snapshot data: null")
            }
            Log.d(TAG, "List data: ${fetchedImgList}")
            Log.d(TAG, "List Duration: ${durationList}")
            setAllImgs()
            dataReady.complete(Unit)
        }
    }


    //      setting all img URLs from db to local list
    private fun setAllImgs() {

        imgList.clear()
        for (docs in fetchedImgList) {

            if (docs.keys.contains("url")) {
//                Log.d(TAG,"-/-/ LOCAL URLS ${docs}")
                val urlStr = docs["url"].toString()
                val urlDuration = docs["duration"].toString()
                val urlType = docs["type"].toString()

                if (urlType.equals("image", true)) {
                    type = MediaType.IMAGE
                    Log.d(TAGI, "-/-/ LOCAL in photo ${type.name}")
                } else if(urlType.equals("video", true)) {
                    type = MediaType.VIDEO
                    Log.d(TAGI, "-/-/ LOCAL in vid ${type.name}")
                }

                Log.d(TAG, "-/-/ LOCAL URLS ${urlStr}")
                Log.d(TAG, "-/-/ LOCAL durations ${urlDuration.toLong()}")
                Log.d(TAGI, "-/-/ LOCAL types of media $type")
                Log.d(TAGI, "-/-/ LOCAL types of media $urlType")
//                Log.d(TAGI, "-/-/ LOCAL types of media $urlType")
                imgList.add(MediaItem(urlStr, urlDuration.toLong(), type))

            }
        }
        imgAdapter.updateData(imgList)
        Log.d(TAG,"-/-/ LOCAL URLS LIST ${imgList}")
    }


    //    private val imgList = listOf(
//        ImageModel("https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8fA%3D%3D"),
//        ImageModel("https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg"),
//        ImageModel("https://images.hdqwalls.com/wallpapers/yellowstone-national-park-hd-qs.jpg"),
//        ImageModel("https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D"),
//        ImageModel("https://images.unsplash.com/photo-1674407729043-c21b71fded37?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MTh8MTMxOTA0MHx8ZW58MHx8fHx8"),
//    )


//      Fetching data from firebase
//    private fun firebaseReadData() {
//
//        val db = Firebase.firestore
//        db.collection("templates")
//            .get()
//            .addOnSuccessListener { result ->
//                for(doc in result) {
//                    Log.d(TAG, "${doc.id} => ${doc.data}")
////                    println("-/-/-/-/-/-/-/-/-/-/-/-/-/Data Fetched")
//                }
//            }
//            .addOnFailureListener() {exception ->
//                Log.w(TAG, "/-/-/-/-/-/ Error in fetching images", exception)
//
//            }
//    }


//    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
