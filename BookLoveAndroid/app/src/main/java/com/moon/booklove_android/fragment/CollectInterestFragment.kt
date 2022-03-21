package com.moon.booklove_android.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.config.ApplicationClass.Companion.checkedInterest
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.databinding.FragmentCollectInterestBinding


private const val TAG = "CollectInterestFragment"

class CollectInterestFragment  : Fragment(){
    private lateinit var binding: FragmentCollectInterestBinding
    private lateinit var nextButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton = (activity as CollectActivity).findViewById(R.id.nextStepButton)
        nextButton.setText("완료")

        init()

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            //여기 들어가지 못 하는 이유는???


            Log.d(TAG, "onViewCreated1: ")
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {chipView ->
                checkedInterest.add(chip.text.toString())
                Log.d(TAG, "onViewCreated: ")
            } ?: kotlin.run {
            }
        }

        nextButton.setOnClickListener {
            Log.d(TAG, "onViewCreated: ${checkedInterest}")
        }

    }

    private fun init(){
        for(chip in interest){
            binding.chipGroup.addView(
                Chip(context).apply{
                    text = chip
                    isCheckable = true
                    setChipBackgroundColorResource(R.color.light_purple)
                }
            )
        }

    }


}