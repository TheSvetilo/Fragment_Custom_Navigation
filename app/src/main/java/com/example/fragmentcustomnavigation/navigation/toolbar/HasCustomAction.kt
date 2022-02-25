package com.example.fragmentcustomnavigation.navigation.toolbar

interface HasCustomAction {

    fun getCustomAction(): CustomAction
}

data class CustomAction(
    val iconRes: Int,
    val descriptionRes: Int,
    val action: Runnable
)