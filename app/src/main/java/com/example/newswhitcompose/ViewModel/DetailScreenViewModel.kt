package com.example.newswhitcompose.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswhitcompose.model.News
import com.example.newswhitcompose.repository.NewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository : NewRepository
): ViewModel() {

    private val _news = MutableLiveData<News>()

    fun getNewsByTitle(title : String): LiveData<News> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNew(title)
            _news.postValue(news)
        }
        return _news
    }

}