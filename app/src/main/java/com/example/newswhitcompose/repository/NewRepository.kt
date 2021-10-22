package com.example.newswhitcompose.repository

import com.example.newswhitcompose.model.News
import com.example.newswhitcompose.provider.NewsProvider
import java.lang.Exception
import javax.inject.Inject

interface NewRepository {
    suspend fun getNews (country :String) : List<News>
    fun getNew(title : String): News

}

class NewRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider
    ): NewRepository {

    private var news : List<News> = emptyList()

    override suspend fun getNews(country: String): List<News> {
        val apiResponse = newsProvider.topHeadLines(country).body()
        if (apiResponse?.status == "error"){
            when (apiResponse.code){
                "apiKeyMissing" -> throw MissingApiKeyException()
                "apiKeyInvalid" -> throw ApiKeyInvalidException()
                else -> throw Exception()




            }
        }

        news = apiResponse?.articles ?: emptyList()
        return news

    }

    override fun getNew(title: String): News =
        news.first { it.title == title}


}

class MissingApiKeyException : java.lang.Exception()
class ApiKeyInvalidException : java.lang.Exception()


