package com.utkangul.invio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.utkangul.invio.R
import com.utkangul.invio.viewModel.SplashViewModel

class MainActivity : AppCompatActivity() {

    private val splashViewModel = SplashViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen().apply {
            setKeepOnScreenCondition{
                splashViewModel.isLoading.value
            }
        }

        setContentView(R.layout.activity_main)


    }
}