package com.moon.booklove_android.view.main.myPage.editGender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentEditGenderBinding
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.view.main.myPage.editGender.presenter.EditGenderPresenterImpl

class EditGenderFragment  : Fragment(), EditGenderView{

    private lateinit var presenter: EditGenderPresenterImpl
    private lateinit var binding: FragmentEditGenderBinding

    lateinit var selectedGender: String
    private var isClicked = false

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

        presenter = EditGenderPresenterImpl(this)

        init()

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

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                presenter.updateUserGender(UserInfoUpdateReqDTO(currentuser.age,currentuser.userCategoryList, selectedGender), requireContext())
            }else{
                toast("성별을 선택해주세요", requireContext())
            }
        }
    }

    override fun init() {
        if(currentuser.gender=="Man"){
            binding.maleButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }else{
            binding.femaleButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }
    }

    override fun selectMan() {
        isClicked = true
        selectedGender = "Man"
        binding.femaleButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    override fun selectWoman() {
        isClicked = true
        selectedGender = "Woman"
        binding.maleButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    override fun selectComplete() {
        binding.nextStepButton.setBackgroundResource(R.drawable.dark_box_rectangle)
    }
}