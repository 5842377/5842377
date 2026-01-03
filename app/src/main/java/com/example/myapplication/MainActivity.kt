package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.navigation.NavGraph
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.data.UserPreferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.work.NotificationWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = UserPreferencesDataStore(applicationContext)

        setContent {
            val viewModel: AppViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        return AppViewModel(dataStore) as T
                    }
                }
            )
            MyApplicationTheme {
                NavGraph(viewModel = viewModel)
            }

            val channel = NotificationChannel(
                "grad_channel",
                "Graduation Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)

            val request = PeriodicWorkRequestBuilder<NotificationWorker>(
                24, TimeUnit.HOURS
            ).build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_motivation",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}