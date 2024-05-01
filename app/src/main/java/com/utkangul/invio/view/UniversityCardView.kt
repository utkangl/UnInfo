package com.utkangul.invio.view

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import com.utkangul.invio.R
import com.utkangul.invio.internalClasses.InternalFunctions
import com.utkangul.invio.model.UniversityInfo
import com.utkangul.invio.viewModel.FavouritesViewModel
import com.utkangul.invio.viewModel.MainActivityViewModel
import com.utkangul.invio.viewModel.favList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

var currentUrl: String? = null

class UniversityCardView(
    context: Context,
    universityInfo: UniversityInfo,
    linearLayout: LinearLayout?
): RelativeLayout(context) {

    val info = universityInfo
    private val _removeView = MutableStateFlow(false)
    val removeView: StateFlow<Boolean> = _removeView.asStateFlow()

    init {
        LayoutInflater.from(context).inflate(R.layout.university_card_view, this, true)

        val websiteCard = findViewById<CardView>(R.id.website_card)
        val phoneCard = findViewById<CardView>(R.id.phone_card)
        val universityCard = findViewById<RelativeLayout>(R.id.universityHeader)
        val expandUniversityCardIB = universityCard.findViewById<ImageButton>(R.id.expandCardButton)
        val favUniversityBTN = universityCard.findViewById<ImageButton>(R.id.favUniversityButton)
        val universityName = universityCard.findViewById<TextView>(R.id.universityNameTV)
        val universityInformationsContainer = findViewById<LinearLayout>(R.id.universityInformationsContainer)
        val universityPhone = universityInformationsContainer.findViewById<TextView>(R.id.phoneTV)
        val universityFax = universityInformationsContainer.findViewById<TextView>(R.id.faxTV)
        val universityWebsite = universityInformationsContainer.findViewById<TextView>(R.id.websiteTV)
        val universityAddress = universityInformationsContainer.findViewById<TextView>(R.id.addressTV)
        val universityRector = universityInformationsContainer.findViewById<TextView>(R.id.rectorTV)
        var mainActivityViewModel = MainActivityViewModel(context)
        var favouritesViewModel = FavouritesViewModel()

        var isExpandClicked = false
        universityName.text = universityInfo.name
        universityPhone.text   = "Phone: ${universityInfo.phone}"
        universityFax.text =     "Fax: ${universityInfo.fax}"
        universityWebsite.text = "Website: ${universityInfo.website}"
        universityAddress.text = "Address: ${universityInfo.address}"
        universityRector.text =  "Rector: ${universityInfo.rector}"


        universityName.setOnClickListener {
            expandUniversityCardIB.performClick()
        }

        expandUniversityCardIB.setOnClickListener{
            if (!isExpandClicked){
                //expandUniversityCardBTN.setBackgroundResource(R.drawable.remove)
                expandUniversityCardIB.rotation = 90.0F
                val animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
                universityInformationsContainer.startAnimation(animation)
                InternalFunctions.Internalfuncs.setViewVisibleWithFade(context,universityInformationsContainer)
                isExpandClicked = true

            } else {
                //expandUniversityCardBTN.setBackgroundResource(R.drawable.forward_arrow)
                expandUniversityCardIB.rotation = 0.0F
                val animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                universityInformationsContainer.startAnimation(animation)
                InternalFunctions.Internalfuncs.setViewGoneWithFade(context,universityInformationsContainer)
                isExpandClicked = false
            }
        }

        websiteCard.setOnClickListener{
            val options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_down, 0)
            val intent = Intent(context, WebsiteActivity::class.java)
            startActivity(context,intent,options.toBundle())
            currentUrl = universityInfo.website
        }

        phoneCard.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${universityPhone.text}")
            startActivity(context,intent,null)
        }

        if (mainActivityViewModel.isFav(universityInfo, favList)){
            favUniversityBTN.setBackgroundResource(R.drawable.filled_heart)
        }



        favUniversityBTN.setOnClickListener{
            val isFav = mainActivityViewModel.isFav(universityInfo, favList)
            println(isFav)

            if (isFav) {
                mainActivityViewModel.removeFavoriteUniversity(this,context)
                favUniversityBTN.setBackgroundResource(R.drawable.heart)
               removeUniversityView(linearLayout,this)
            }
            else{
                mainActivityViewModel.addFavoriteUniversity(this,context)
                favUniversityBTN.setBackgroundResource(R.drawable.filled_heart)
            }
        }
    }

    private fun removeUniversityView(linearLayout: LinearLayout?, universityCardView: RelativeLayout) {
        linearLayout?.let { parentLayout ->
            val slideUpAnimation = AnimationUtils.loadAnimation(parentLayout.context, R.anim.slide_left)
            slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    // Post a Runnable to remove the view after a delay
                    parentLayout.postDelayed({
                        parentLayout.removeView(universityCardView)
                    }, 10) // 100 milliseconds delay
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            universityCardView.startAnimation(slideUpAnimation)
        }
    }


}


