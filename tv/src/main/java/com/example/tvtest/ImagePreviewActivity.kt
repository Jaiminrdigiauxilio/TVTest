package com.example.tvtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class ImagePreviewActivity : FragmentActivity() {

    private  lateinit var viewPager: ViewPager2
    private  lateinit var imgAdapter: ImageAdapter
    private val fetchedImgList = mutableListOf<Map<String, Any>>()

//    private val imgList = mutableListOf<ImageModel>()

    private val imgList = listOf(
        ImageModel("https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8fA%3D%3D"),
        ImageModel("https://1.bp.blogspot.com/-BknVauztAWE/Uv4XuRHItGI/AAAAAAAAChk/0CZgIDXIDzE/s1600/Rocks+Water+wallpaper.jpg"),
        ImageModel("https://images.hdqwalls.com/wallpapers/yellowstone-national-park-hd-qs.jpg"),
        ImageModel("https://images.unsplash.com/photo-1494500764479-0c8f2919a3d8?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8fA%3D%3D"),
        ImageModel("https://images.unsplash.com/photo-1674407729043-c21b71fded37?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MTh8MTMxOTA0MHx8ZW58MHx8fHx8"),
    )
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val TAG = "ImageFetch"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        listenFirebaseDb()

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
//        firebaseReadData()
    }

    //      listening code for firebase
    private fun listenFirebaseDb() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("templates")


        val registration = docRef.addSnapshotListener { snapshot, err ->
            if (err != null) {
                Log.w(TAG, "Listen Failed",err)
                println("-/-/-/-/-/Listen Failed $err")
                return@addSnapshotListener
            }

            if(snapshot != null) {
                // emptying list in order to have no duplicates
                fetchedImgList.clear()

                for(docs in snapshot) {
                    // Adding data to local List
                    fetchedImgList.add(docs.data)
                    Log.d(TAG, "-/-/-/-/-/-/-/-/-/- document Data: ${docs.data}")
                }

            } else {
                Log.d(TAG,"Snapshot data: null")
            }
            Log.d(TAG, "List data: ${fetchedImgList}")
//            setAllImgs()
        }
    }


    //      setting all img URLs from db to local list
//    private fun setAllImgs() {
//        for (docs in fetchedImgList) {
//            if (docs.keys.contains("url")) {
////                Log.d(TAG,"-/-/ LOCAL URLS ${docs}")
//                val urlStr = docs["url"].toString()
//                Log.d(TAG,"-/-/ LOCAL URLS ${urlStr}")
//                imgList.plus(ImageModel(urlStr))
////                imgList.plus(ImageModel((docs["url"].toString())))
//
//            }
//        }
//        Log.d(TAG,"-/-/ LOCAL URLS LIST ${imgList}")
//    }

    //      Fetching data from firebase
    private fun firebaseReadData() {

        val db = Firebase.firestore
        db.collection("templates")
            .get()
            .addOnSuccessListener { result ->
                for(doc in result) {
                    Log.d(TAG, "${doc.id} => ${doc.data}")
//                    println("-/-/-/-/-/-/-/-/-/-/-/-/-/Data Fetched")
                }
            }
            .addOnFailureListener() {exception ->
                Log.w(TAG, "/-/-/-/-/-/ Error in fetching images", exception)

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}