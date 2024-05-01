package com.utkangul.invio.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.utkangul.invio.R
import com.utkangul.invio.internalClasses.InternalFunctions
import com.utkangul.invio.viewModel.FavouritesViewModel
import com.utkangul.invio.viewModel.MainActivityViewModel
import com.utkangul.invio.viewModel.SplashViewModel
import com.utkangul.invio.viewModel.favList
import com.utkangul.invio.viewModel.universityData
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var citiesScroll: ScrollView
    private lateinit var favouritesScroll: ScrollView
    private lateinit var favouriteUniversitiesButtonRL: RelativeLayout
    private lateinit var universitiesButtonRL: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        mainViewModel = MainActivityViewModel(this)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                splashViewModel.isLoading.value
            }
        }

        setContentView(R.layout.activity_main)


        val internalFunctions = InternalFunctions.Internalfuncs
        citiesScroll = findViewById(R.id.citiesScrollView)
        favouritesScroll = findViewById(R.id.favouritesScrollView)
        favouriteUniversitiesButtonRL = findViewById(R.id.favouritesButtonRL)
        universitiesButtonRL = findViewById(R.id.universitiesButtonRL)
        val universitiesBottomLine = universitiesButtonRL.findViewById<ImageView>(R.id.universitiesBottomLine)
        val favouritesUniversitiesBottomLine = favouriteUniversitiesButtonRL.findViewById<ImageView>(R.id.favouritesUniversitiesBottomLine)
        var universitiesDisplayed = true
        var favouritesDisplayed = false

        lifecycleScope.launch{
            observeUniversityData()
        }

        citiesScroll.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = citiesScroll.scrollY
            val height = citiesScroll.height

            val percentage = scrollY.toFloat() / (citiesScroll.getChildAt(0).height - height) * 100
            if(universityData.value?.currentPage!! < universityData.value?.totalPage!!){
              if (percentage >= 90){
                  splashViewModel.fetchUniversitiesData(universityData.value?.currentPage!!+1)
              }
            }
        }

        favouriteUniversitiesButtonRL.setOnClickListener {
            if (!favouritesDisplayed){
                favouritesDisplayed = true
                universitiesDisplayed = false
                internalFunctions.setViewVisibleWithLeftSlide(this,favouritesScroll,favouritesUniversitiesBottomLine)
                internalFunctions.setViewGoneWithLeftSlide(this,citiesScroll,universitiesBottomLine)
                val linear = findViewById<LinearLayout>(R.id.mainActivityfavouritesLinearLayout)
                linear.removeAllViews()

                val favouritesViewModel = FavouritesViewModel()
                var universityCard: UniversityCardView

                val favouriteUniversities = favouritesViewModel.stringsToUniversityInfoList(favList)

                println(favouriteUniversities.size)

                for (uni in favouriteUniversities){
                    universityCard = UniversityCardView(this,uni,linear)
                    linear.addView(universityCard)

                }
            }
        }

        universitiesButtonRL.setOnClickListener {
            if (!universitiesDisplayed){
                universitiesDisplayed = true
                favouritesDisplayed = false
                internalFunctions.setViewVisibleWithRightSlide(this,citiesScroll,universitiesBottomLine)
                internalFunctions.setViewGoneWithRightSlide(this,favouritesScroll,favouritesUniversitiesBottomLine)
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Do You Want To Exit?")
                builder.setPositiveButton("Yes") { _, _ -> finish() }
                builder.setNegativeButton("No", null)
                builder.create().show()
            }
        }
        this.onBackPressedDispatcher.addCallback(callback)
    }


    // Observers viewModel's com.utkangul.invio.viewModel.getUniversityData variable the trigger when the value is changed
    // Cause of use: To use the variable when its value is changed to api response's university data
    private suspend fun observeUniversityData() {
        universityData.collect { universityData ->
            universityData?.let {
                val citiesContainer = findViewById<LinearLayout>(R.id.mainActivityCitiesContainer)
                for (city in universityData.data){
                    val cityCardView = CityCardView(this@MainActivity, city)
                    citiesContainer.addView(cityCardView)
                }
            }
        }
    }







}