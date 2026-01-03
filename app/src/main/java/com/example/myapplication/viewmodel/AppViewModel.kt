package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.RetrofitInstance
import com.example.myapplication.data.UserPreferencesDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    init {
        viewModelScope.launch {
            dataStore.graduationMessage.collect { saved ->
                _message.value = saved
            }
        }
    }

    fun updateMessage(newMessage: String) {
        viewModelScope.launch {
            dataStore.saveMessage(newMessage)
        }
    }

    private val _quote = MutableStateFlow("Loading...")
    val quote: StateFlow<String> = _quote

    init {
        loadQuote()
    }

    fun loadQuote() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomQuote()
                val quote = response[0]

                Log.d("QuoteAPI", "Quote: ${quote.q} — ${quote.a}")

                _quote.value = "\"${quote.q}\" — ${quote.a}"
            } catch (e: Exception) {
                Log.e("QuoteAPI", "Error loading quote", e)
                _quote.value = "Failed to load quote"
            }
        }
    }


    private val _nasaImageUrl = MutableStateFlow("")
    val nasaImageUrl: StateFlow<String> = _nasaImageUrl

    private val _nasaTitle = MutableStateFlow("")
    val nasaTitle: StateFlow<String> = _nasaTitle

    private val _nasaExplanation = MutableStateFlow("")
    val nasaExplanation: StateFlow<String> = _nasaExplanation

    fun loadNasaImage(apiKey: String) {
        viewModelScope.launch {
            try {
                val responseList = RetrofitInstance.nasaApi.getApodRange(
                    apiKey = apiKey,
                    startDate = "2024-12-01",
                    endDate = "2025-02-01"
                )

                val nasaImage = responseList
                    .filter { it.media_type == "image" }
                    .randomOrNull()

                _nasaImageUrl.value = nasaImage?.hdurl ?: nasaImage?.url ?: ""
                _nasaTitle.value = nasaImage?.title ?: ""
                _nasaExplanation.value = nasaImage?.explanation ?: ""

            } catch (e: Exception) {
                _nasaImageUrl.value = ""
                _nasaTitle.value = ""
                _nasaExplanation.value = ""
            }
        }
    }
}