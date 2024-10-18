// MainActivity.kt
package com.example.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.timer.service.StopwatchService
import com.example.timer.core.theme.StopWatchTheme
import com.example.timer.pages.home_page.view_models.HomePageViewModel
import com.example.timer.ui.timer_page.TimerPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * The MainActivity class is the entry point of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * The isBound variable is used to store the current state of the StopwatchService.
     */
    private var isBound by mutableStateOf(false)

    /**
     * The stopwatchService variable is used to store the StopwatchService instance.
     */
    private lateinit var stopwatchService: StopwatchService

    /**
     * The connection variable is used to create a ServiceConnection object to bind the MainActivity to the StopwatchService.
     */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    var viewModel: HomePageViewModel? = null

    /**
     * The onStart function is called when the MainActivity is started.
     * The function binds the MainActivity to the StopwatchService.
     */
    override fun onStart() {
        super.onStart()
        Intent(this, StopwatchService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    /**
     * The onCreate function is called when the MainActivity is created.
     * The function sets the content of the MainActivity to the TimerPage.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StopWatchTheme {
                if (isBound) {
                    if (viewModel == null) {
                        viewModel = HomePageViewModel(stopwatchService = stopwatchService)
                    }
                    TimerPage(viewModel = viewModel!!)
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    /**
     * The requestPermissions function is used to request permissions from the user.
     * @param permissions: The permissions to be requested from the user.
     */
    private fun requestPermissions(vararg permissions: String) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            result.entries.forEach {
                Log.d("MainActivity", "${it.key} = ${it.value}")
            }
        }
        requestPermissionLauncher.launch(permissions.asList().toTypedArray())
    }

    /**
     * The onStop function is called when the MainActivity is stopped.
     * The function unbinds the MainActivity from the StopwatchService.
     */
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}