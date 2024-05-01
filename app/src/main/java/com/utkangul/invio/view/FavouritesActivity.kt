package com.utkangul.invio.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.utkangul.invio.R
import com.utkangul.invio.model.UniversityInfo
import com.utkangul.invio.viewModel.FavouritesViewModel
import com.utkangul.invio.viewModel.favList
import kotlinx.coroutines.launch

class FavouritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val favouritesViewModel = FavouritesViewModel()
        val linear = findViewById<LinearLayout>(R.id.universitiesContainer)
        var universityCard: UniversityCardView

        val favouriteUniversities = favouritesViewModel.stringsToUniversityInfoList(favList)

        for (uni in favouriteUniversities){
            universityCard = UniversityCardView(this,uni,linear)
            linear.addView(universityCard)
        }
    }
}