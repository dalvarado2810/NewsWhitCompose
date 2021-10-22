package com.example.newswhitcompose.provider

import com.example.newswhitcompose.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "fc8d43dc50e1472fa95052d8d89ae3cc"

interface NewsProvider {

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun topHeadLines(@Query("country") country: String) : Response<NewsApiResponse>

}