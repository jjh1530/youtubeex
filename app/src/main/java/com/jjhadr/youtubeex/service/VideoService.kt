package com.jjhadr.youtubeex.service

import com.jjhadr.youtubeex.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET


interface VideoService {

    @GET("0c05ab56-f027-4f39-bde2-b40fb4c4df7c")
    fun listVideos(): retrofit2.Call<VideoDto>
}