package com.utkangul.invio.view

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.utkangul.invio.R
import com.utkangul.invio.viewModel.FavouritesViewModel
import com.utkangul.invio.viewModel.favList

class FavouritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val favouritesViewModel = FavouritesViewModel()
        val linear = findViewById<LinearLayout>(R.id.universitiesContainer)
        var universityCard: UniversityCardView

        val favouriteUniversities = favouritesViewModel.stringsToUniversityInfoList(favList)

        for (uni in favouriteUniversities) {
            universityCard = UniversityCardView(this, uni, linear)
            linear.addView(universityCard)
        }
    }
}