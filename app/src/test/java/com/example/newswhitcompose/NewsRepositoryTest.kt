package com.example.newswhitcompose

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import com.example.newswhitcompose.provider.NewsProvider
import com.example.newswhitcompose.repository.ApiKeyInvalidException
import com.example.newswhitcompose.repository.MissingApiKeyException
import com.example.newswhitcompose.repository.NewRepositoryImp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets


class NewsRepositoryTest {
   private val mockWebServer = MockWebServer()

    private val newsProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsProvider::class.java)

    private val newsRepository = NewRepositoryImp(newsProvider)

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun topHeadlinesResponseCorrect () {
        mockWebServer.enqueueResponse("top_healines.json")

        runBlocking {
            val articles = newsRepository.getNews("US")
            assertEquals(2,articles.size)
            assertEquals(null,articles[0].author)
            assertEquals("Daniel Dale",articles[1].author)
        }

    }

    @Test
    fun apiKeyMissingException (){
        mockWebServer.enqueueResponse("api_key_missing.json")
        assertThrows(MissingApiKeyException::class.java){
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

    @Test
    fun apiKeyInvalidException (){
        mockWebServer.enqueueResponse("api_key_invalid.json")
        assertThrows(ApiKeyInvalidException::class.java){
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

}

fun MockWebServer.enqueueResponse(filePath: String){
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}