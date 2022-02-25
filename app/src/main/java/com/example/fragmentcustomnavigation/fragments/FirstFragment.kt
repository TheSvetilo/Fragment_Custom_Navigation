package com.example.fragmentcustomnavigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentcustomnavigation.MainActivity
import com.example.fragmentcustomnavigation.databinding.FragmentFirstBinding
import com.example.fragmentcustomnavigation.navigation.navigate
import com.example.fragmentcustomnavigation.observer.Observer

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        arguments?.getString(KEY).let {
            binding.valueTextView.text = it
        }

        binding.nextButton.setOnClickListener { navigate().showSecondFragment() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).observable.addObserver(
            object : Observer {
                override fun update(value: String) {
                    binding.valueTextView.text = value
                    Log.d("TAG", "First Fragment: $value")
                }
            }
        )
    }

    companion object {

        private const val KEY = "key"

        fun newInstance(value: String): FirstFragment {
            val args = Bundle().apply {
                putString(KEY, value)
            }
            val fragment = FirstFragment()
            fragment.arguments = args
            return fragment
        }
    }
}