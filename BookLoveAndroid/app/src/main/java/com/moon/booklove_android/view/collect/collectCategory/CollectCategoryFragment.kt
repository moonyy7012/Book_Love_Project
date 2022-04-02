package com.moon.booklove_android.view.collect.collectCategory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.view.main.MainActivity
import com.moon.booklove_android.view.collect.collectCategory.presenter.CollectCategoryPresenterImpl
import com.moon.booklove_android.view.collect.CollectActivity
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.databinding.FragmentCollectInterestBinding
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO

class CollectCategoryFragment  : Fragment(), CollectCategoryView {

    private lateinit var presenter: CollectCategoryPresenterImpl
    private lateinit var binding: FragmentCollectInterestBinding

    private lateinit var nextButton: AppCompatButton
    lateinit var selectedGender: String
    var checkedInterestList = ArrayList<String>()
    var selectedAgeRange: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedGender = requireArguments().getString("gender").toString()
            selectedAgeRange = requireArguments().getInt("ageRange")
        }
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

        presenter = CollectCategoryPresenterImpl(this)


        nextButton = (activity as CollectActivity).findViewById(R.id.nextStepButton)
        init()

        nextButton.setOnClickListener {
            presenter.updateUserInfo(UserInputInfoReqDTO(selectedAgeRange,checkedInterestList, selectedGender),requireContext())
        }
    }

    override fun init(){
        nextButton.text = "완료"
        for(chip in ApplicationClass.interest){
            binding.chipGroup.addView(
                Chip(context).apply{
                    text = chip
                    tag = chip
                    isCheckable = true
                    setChipBackgroundColorResource(R.color.light_purple)
                    setOnClickListener {
                        if(!isChecked){
                            checkedInterestList.remove(chip)
                        }else{
                            checkedInterestList.add(chip)
                        }
                    }
                }
            )
        }
    }

    override fun goMain() {
        val mainIntent = Intent(requireContext(), MainActivity::class.java)
        startActivity(mainIntent)
    }

    companion object {

        private const val gender = "gender"
        private const val ageRange = "ageRange"

        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            CollectCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(gender, param1)
                    putInt(ageRange, param2)
                }
            }
    }
}