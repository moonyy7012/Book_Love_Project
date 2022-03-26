package com.moon.booklove_android.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.databinding.FragmentCollectGenderBinding
import com.moon.booklove_android.databinding.FragmentEditGenderBinding

class EditGenderFragment  : Fragment(){
    private lateinit var binding: FragmentEditGenderBinding
    private lateinit var nextButton: AppCompatButton
    var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton = view.findViewById(R.id.nextStepButton)
        binding.maleButton.apply {
            setOnClickListener {
                isClicked = true
                setBackgroundResource(R.drawable.dark_box_rectangle)
                binding.femaleButton.setBackgroundResource(R.drawable.light_box_rectangle)
                nextButton.setBackgroundResource(R.drawable.complete_rectangle)
            }
        }
        binding.femaleButton.apply {
            setOnClickListener {
                isClicked = true
                setBackgroundResource(R.drawable.dark_box_rectangle)
                binding.maleButton.setBackgroundResource(R.drawable.light_box_rectangle)
                nextButton.setBackgroundResource(R.drawable.complete_rectangle)
            }
        }

        nextButton.setOnClickListener {
            //companion object에 gender 저장

            if(isClicked){
                Toast.makeText(context, "내 정보를 수정했습니다", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "성별을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }
}