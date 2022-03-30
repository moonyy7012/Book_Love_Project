package com.moon.booklove_android.view.main.myPage.editCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentEditInterestBinding
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.view.main.myPage.editCategory.presenter.EditCategoryPresenterImpl

class EditCategoryFragment  : Fragment(), EditCategoryView{

    private lateinit var presenter: EditCategoryPresenterImpl
    private lateinit var binding: FragmentEditInterestBinding

    var checkedInterestList = ArrayList<String>()
    var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = EditCategoryPresenterImpl(this)

        init()

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                presenter.updateUserCategory(UserInfoUpdateReqDTO(currentuser.age,checkedInterestList, currentuser.gender), requireContext())
            }else{
                toast("관심사를 선택해주세요", requireContext())
            }
        }
    }

    override fun init(){
        checkedInterestList = currentuser.userCategoryList
        for(chip in interest){
            binding.chipGroup.addView(
                Chip(context).apply{

                    text = chip
                    tag = chip
                    isCheckable = true
                    setChipBackgroundColorResource(R.color.light_purple)
                    if(currentuser.userCategoryList.contains(chip)){
                        isChecked = true
                        isCheckedIconVisible = true
                    }
                    setOnClickListener {
                        if(!isClicked){
                            isClicked = true
                            binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
                        }
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

    override fun selectComplete() {
        isClicked = false
        checkedInterestList = currentuser.userCategoryList
        binding.nextStepButton.setBackgroundResource(R.drawable.dark_box_rectangle)
    }
}