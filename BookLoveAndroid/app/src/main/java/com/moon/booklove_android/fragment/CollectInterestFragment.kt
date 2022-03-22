package com.moon.booklove_android.fragment

import android.content.Intent
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
import com.moon.booklove_android.activity.MainActivity
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

        nextButton.setOnClickListener {
            checkedInterest = binding.chipGroup.getCheckedChipIds()
            Log.d(TAG, "onViewCreated: ${checkedInterest}")

            //companion object에 저장한 세 항목(성별, 연령대, 관심사)을 Rest Api로 여기서 post

            val mainIntent = Intent(context, MainActivity::class.java)
            startActivity(mainIntent)
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