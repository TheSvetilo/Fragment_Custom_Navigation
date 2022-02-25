package com.example.fragmentcustomnavigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fragmentcustomnavigation.MainActivity
import com.example.fragmentcustomnavigation.R
import com.example.fragmentcustomnavigation.databinding.FragmentThirdBinding
import com.example.fragmentcustomnavigation.navigation.navigate
import com.example.fragmentcustomnavigation.navigation.toolbar.HasCustomTitle
import com.example.fragmentcustomnavigation.observer.Observer

class ThirdFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentThirdBinding.inflate(inflater, container, false).apply {

        goBackButton.setOnClickListener { navigate().goBack() }
        menuButton.setOnClickListener { navigate().goToMenu() }

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).observable.addObserver(
            object : Observer {
                override fun update(value: String) {
                    view.findViewById<TextView>(R.id.thirdTextView).text = value
                    Log.d("TAG", "Third Fragment: $value")
                }
            }
        )
    }

    override fun getTitleRes() = R.string.third_fragment_title
}