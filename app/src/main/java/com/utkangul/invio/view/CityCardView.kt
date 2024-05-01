package com.utkangul.invio.view

import android.content.Context
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.utkangul.invio.R
import com.utkangul.invio.internalClasses.InternalFunctions
import com.utkangul.invio.model.City

class CityCardView(
    context: Context,
    city: City

) : RelativeLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.city_card_view, this, true)

        val cityName = findViewById<TextView>(R.id.cityNameTV)
        val expandCityCardIB = findViewById<ImageButton>(R.id.expandCityCardIB)
        val cityCardUniversitiesContainer = findViewById<LinearLayout>(R.id.universitiesContainerLL)
        val cityNameContainerRL = findViewById<RelativeLayout>(R.id.cityNameContainerRL)

        var isExpandClicked = false

        cityName.text = city.province

        for (university in city.universities){
            val universityCardView = UniversityCardView(context, university,null)
            cityCardUniversitiesContainer.addView(universityCardView)
        }

        cityNameContainerRL.setOnClickListener{
            expandCityCardIB.performClick()
        }
        expandCityCardIB.setOnClickListener{
            if (!isExpandClicked){
                val animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
                cityCardUniversitiesContainer.startAnimation(animation)
                InternalFunctions.Internalfuncs.setViewVisibleWithFade(context,cityCardUniversitiesContainer)
                isExpandClicked = true
                expandCityCardIB.rotation = 90.0F


            } else {
                val animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                cityCardUniversitiesContainer.startAnimation(animation)
                InternalFunctions.Internalfuncs.setViewGoneWithFade(context,cityCardUniversitiesContainer)
                expandCityCardIB.rotation = 0.0F
                //universityInformationsContainer.visibility = GONE
                isExpandClicked = false
            }
        }
    }



}

