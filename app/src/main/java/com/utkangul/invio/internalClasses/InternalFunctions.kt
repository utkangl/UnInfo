package com.utkangul.invio.internalClasses

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import com.utkangul.invio.R

class InternalFunctions {
    object Internalfuncs{
        fun <T : View>setViewVisibleWithFade(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.fade_in)
                fadeOut.setTarget(component)
                fadeOut.start()
                component!!.visibility = View.VISIBLE
            }
        }

        fun <T : View> setViewGoneWithLeftSlide(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.slide_left)
                fadeOut.setTarget(component)
                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        component!!.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                fadeOut.start()
            }
        }

        fun <T : View> setViewVisibleWithLeftSlide(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.slide_left_2)
                fadeOut.setTarget(component)
                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        component!!.visibility = View.VISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                fadeOut.start()
            }
        }
        fun <T : View> setViewGoneWithRightSlide(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.slide_right)
                fadeOut.setTarget(component)
                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        component!!.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                fadeOut.start()
            }
        }

        fun <T : View> setViewVisibleWithRightSlide(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.slide_right_2)
                fadeOut.setTarget(component)
                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        component!!.visibility = View.VISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                fadeOut.start()
            }
        }

        fun <T : View> setViewGoneWithFade(context: Context, vararg components: T?) {
            for (component in components) {
                val fadeOut = AnimatorInflater.loadAnimator(context, R.animator.fade_out)
                fadeOut.setTarget(component)
                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        component!!.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                fadeOut.start()
            }
        }


    }

}