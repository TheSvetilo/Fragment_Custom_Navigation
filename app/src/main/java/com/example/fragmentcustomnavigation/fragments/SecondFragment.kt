package com.example.fragmentcustomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentcustomnavigation.MainActivity
import com.example.fragmentcustomnavigation.R
import com.example.fragmentcustomnavigation.databinding.FragmentSecondBinding
import com.example.fragmentcustomnavigation.navigation.navigate
import com.example.fragmentcustomnavigation.navigation.toolbar.CustomAction
import com.example.fragmentcustomnavigation.navigation.toolbar.HasCustomAction
import com.example.fragmentcustomnavigation.navigation.toolbar.HasCustomTitle

class SecondFragment : Fragment(), HasCustomTitle, HasCustomAction {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.apply {

            confirmButton.setOnClickListener { onConfirmPressed() }
            goBackButton.setOnClickListener { navigate().goBack() }
            goNextButton.setOnClickListener { navigate().showThirdFragment() }
        }

        return binding.root
    }

    private fun onConfirmPressed() {
        val text = binding.newValueEditText.text.toString()
        (requireActivity() as MainActivity).observable.notifyObservers(text)
        navigate().showFirstFragment(text)
    }

    // Toolbar stuff
    override fun getTitleRes() = R.string.second_fragment_title

    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_toolbar_done,
        descriptionRes = R.string.toolbar_desc_done,
        action = {
            onConfirmPressed()
        }
    )
}