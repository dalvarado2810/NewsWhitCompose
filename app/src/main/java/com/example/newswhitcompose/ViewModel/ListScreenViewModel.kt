package com.example.newswhitcompose.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswhitcompose.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.newswhitcompose.repository.NewRepository
import javax.inject.Inject


@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository : NewRepository
): ViewModel() {

    private val _news = MutableLiveData<List<News>>()

    fun getNews(): LiveData<List<News>>{
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews("MX")
            _news.postValue(news)
        }
        return _news
    }

}