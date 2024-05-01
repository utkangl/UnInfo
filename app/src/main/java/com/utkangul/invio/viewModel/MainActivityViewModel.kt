package com.utkangul.invio.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.utkangul.invio.model.UniversityInfo
import com.utkangul.invio.view.UniversityCardView


var favList = mutableListOf<String>()

class MainActivityViewModel(context: Context) : ViewModel(){

    val favouriteUnisSharedPref: SharedPreferences = context.getSharedPreferences("fav_universityInfo", Context.MODE_PRIVATE)

    init {
        setFavList()
    }

    fun isFav(universityInfo: UniversityInfo, list: MutableList<String>): Boolean{
        return universityInfo.toString() in list
    }

    fun setSharedPref(){
        val editor = favouriteUnisSharedPref.edit()
        editor.putStringSet("fav", favList.toSet())
        editor.apply()
    }

    fun setFavList(){
        val stringSet = favouriteUnisSharedPref.getStringSet("fav", emptySet()) ?: emptySet()
        // add element if it is not empty
        favList = stringSet.filter { it.isNotBlank() }.toMutableList()
    }

    fun addFavoriteUniversity(universityCardView: UniversityCardView, context: Context) {
        val universityInfo = universityCardView.info
        favList.add(universityInfo.toString())
        setSharedPref()
    }

    fun removeFavoriteUniversity(universityCardView: UniversityCardView, context: Context) {
        val universityInfo = universityCardView.info
        favList.remove(universityInfo.toString())
        setSharedPref()
    }
}