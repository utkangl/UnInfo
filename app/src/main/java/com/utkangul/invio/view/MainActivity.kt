package com.utkangul.invio.view

import SplashViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.iterator
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.utkangul.invio.R
import com.utkangul.invio.internalClasses.InternalFunctions
import com.utkangul.invio.viewModel.FavouritesViewModel
import com.utkangul.invio.viewModel.MainActivityViewModel
import com.utkangul.invio.viewModel.favList
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var splashViewModel: SplashViewModel
    lateinit var mainViewModel: MainActivityViewModel
    lateinit var citiesScroll: ScrollView
    lateinit var favouritesScroll: ScrollView
    lateinit var favouriteUniversitiesButtonRL: RelativeLayout
    lateinit var universitiesButtonRL: RelativeLayout
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

        observeUniversityData()


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

                println("size")
                println(favouriteUniversities.size)

                for (uni in favouriteUniversities){
                    universityCard = UniversityCardView(this,uni,linear)
                    linear.addView(universityCard)
                    println("child count")
                    println(linear.childCount)

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


    // Observers viewModel's universityData variable the trigger when the value is changed
    // Cause of use: To use the variable when its value is changed to api response's university data
    private fun observeUniversityData() {
        var page2fetched = false
        var page3fetched = false
        println("cagirildim")
        lifecycleScope.launch {
            splashViewModel.universityData.collect { universityData ->
                universityData?.let {

                    citiesScroll.viewTreeObserver.addOnScrollChangedListener {
                        val scrollY = citiesScroll.scrollY // Şu anki dikey kaydırma pozisyonu
                        val height = citiesScroll.height // ScrollView'in yüksekliği

                        val percentage = scrollY.toFloat() / (citiesScroll.getChildAt(0).height - height) * 100
                        if (!page2fetched or !page3fetched){
                            if (percentage >= 80 && universityData.currentPage ==1) {
                                splashViewModel.fetchUniversitiesData(2)
                                page2fetched = true
                            }
                            if (percentage >= 80 && universityData.currentPage ==2) {
                                splashViewModel.fetchUniversitiesData(3)
                                page3fetched = true
                            }
                        }
                    }
                    val citiesContainer = findViewById<LinearLayout>(R.id.mainActivityCitiesContainer)
                    for (city in universityData.data){
                        val cityCardView = CityCardView(this@MainActivity, city)
                        citiesContainer.addView(cityCardView)
                    }
                }
            }
        }
    }







}