package com.jjhadr.youtubeex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjhadr.youtubeex.adapter.VideoAdapter
import com.jjhadr.youtubeex.dto.VideoDto
import com.jjhadr.youtubeex.service.VideoService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //프래그먼트 연결
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,PlayerFragment())
            .commit()

        videoAdapter = VideoAdapter()

        findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        getVideoList()
    }

    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also { 
            it.listVideos()
                .enqueue(object : Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (!response.isSuccessful) {
                            Log.e("MainActivity","responseFail")
                            return
                        }
                        response.body()?.let { videoDto ->
                            videoDto.videos.forEach {
                                videoAdapter.submitList(videoDto.videos)
                            }
                        }

                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {

                    }
                })
        }
    }
}