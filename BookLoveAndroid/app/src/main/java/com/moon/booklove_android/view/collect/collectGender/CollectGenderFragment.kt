package com.moon.booklove_android.view.collect.collectGender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.view.collect.CollectActivity
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.enum.enumGender
import com.moon.booklove_android.databinding.FragmentCollectGenderBinding

class CollectGenderFragment  : Fragment(), CollectGenderView {

    private lateinit var binding: FragmentCollectGenderBinding

    private lateinit var nextButton: AppCompatButton
    lateinit var selectedGender: enumGender
    var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = (activity as CollectActivity).findViewById(R.id.nextStepButton)

        binding.maleButton.apply {
            setOnClickListener {
                selectMan()
                setBackgroundResource(R.drawable.dark_box_rectangle)
            }
        }

        binding.femaleButton.apply {
            setOnClickListener {
                selectWoman()
                setBackgroundResource(R.drawable.dark_box_rectangle)
            }
        }

        nextButton.setOnClickListener {
            goCollectAge()
        }
    }

    override fun selectMan() {
        isClicked = true
        selectedGender = enumGender.MAN
        binding.femaleButton.setBackgroundResource(R.drawable.light_box_rectangle)
        nextButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    override fun selectWoman() {
        isClicked = true
        selectedGender = enumGender.WOMAN
        binding.maleButton.setBackgroundResource(R.drawable.light_box_rectangle)
        nextButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    override fun goCollectAge() {
        if(isClicked){
            when(selectedGender) {
                enumGender.MAN -> (activity as CollectActivity).openFragment(1, "Man", 0)
                enumGender.WOMAN -> (activity as CollectActivity).openFragment(1, "Woman", 0)
            }
        }else toast( "성별을 선택해주세요", requireContext())
    }
}