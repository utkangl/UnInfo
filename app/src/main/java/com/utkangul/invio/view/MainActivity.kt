package com.utkangul.invio.view

import SplashViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.utkangul.invio.R
import com.utkangul.invio.model.UniversityData
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var splashViewModel: SplashViewModel
    lateinit var updatedUniversityData: UniversityData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                splashViewModel.isLoading.value
            }
        }

        setContentView(R.layout.activity_main)
        observeUniversityData()

    }


    // Observers viewModel's universityData variable the trigger when the value is changed
    // Cause of use: To use the variable when its value is changed to api response's university data
    private fun observeUniversityData() {
        lifecycleScope.launch {
            splashViewModel.universityData.collect { universityData ->
                universityData?.let {

                }
            }
        }
    }

}