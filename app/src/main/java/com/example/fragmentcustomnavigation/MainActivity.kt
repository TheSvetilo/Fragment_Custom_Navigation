package com.example.fragmentcustomnavigation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fragmentcustomnavigation.databinding.ActivityMainBinding
import com.example.fragmentcustomnavigation.fragments.FirstFragment
import com.example.fragmentcustomnavigation.fragments.SecondFragment
import com.example.fragmentcustomnavigation.fragments.ThirdFragment
import com.example.fragmentcustomnavigation.navigation.Navigator
import com.example.fragmentcustomnavigation.navigation.toolbar.CustomAction
import com.example.fragmentcustomnavigation.navigation.toolbar.HasCustomAction
import com.example.fragmentcustomnavigation.navigation.toolbar.HasCustomTitle
import com.example.fragmentcustomnavigation.observer.Observable
import java.lang.ref.SoftReference

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object: FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateToolbar()
        }
    }

    val observable = Observable.Base()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            val fragment = FirstFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun updateToolbar() {

        val fragment = currentFragment

        if (fragment is HasCustomTitle) {
            supportActionBar?.title = getString(fragment.getTitleRes())
        } else {
            supportActionBar?.title = getString(R.string.app_name)
        }

        if (fragment is HasCustomAction) {
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            binding.toolbar.menu.clear()
        }
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        val iconDrawable = DrawableCompat.wrap((ContextCompat.getDrawable(this, action.iconRes))!!)
        iconDrawable.setTint(Color.WHITE)

        binding.toolbar.menu.add(action.descriptionRes).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            icon = iconDrawable
            setOnMenuItemClickListener {
                action.action.run()
                return@setOnMenuItemClickListener true
            }
        }
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun showSecondFragment() {
        launchFragment(SecondFragment())
    }

    override fun showFirstFragment(value: String) {
        launchFragment(FirstFragment.newInstance(value))
    }

    override fun showThirdFragment() {
        launchFragment(ThirdFragment())
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}