package com.example.fragmentcustomnavigation.navigation

import androidx.fragment.app.Fragment

fun Fragment.navigate(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    // Common
    fun goBack()
    fun goToMenu()

    // First Fragment|Screen
    fun showSecondFragment()

    //Second Fragment|Screen
    fun showFirstFragment(value: String)
    fun showThirdFragment()

}