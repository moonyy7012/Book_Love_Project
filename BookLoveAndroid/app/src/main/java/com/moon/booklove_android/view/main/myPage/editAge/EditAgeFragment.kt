package com.moon.booklove_android.view.main.myPage.editAge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentEditAgeBinding
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO
import com.moon.booklove_android.view.main.myPage.editAge.presenter.EditAgePresenterImpl

class EditAgeFragment  : Fragment(), EditAgeView{

    private lateinit var presenter: EditAgePresenterImpl
    private lateinit var binding: FragmentEditAgeBinding

    var isClicked = false
    var selectedAge = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = EditAgePresenterImpl(this)

        init()

        binding.teenagerButton.setOnClickListener {
            selectedAge = 10
            clickButton(binding.teenagerButton)
        }
        binding.twentiesButton.setOnClickListener {
            selectedAge = 20
            clickButton(binding.twentiesButton)
        }
        binding.thirtiesButton.setOnClickListener {
            selectedAge = 30
            clickButton(binding.thirtiesButton)
        }
        binding.fourtiesButton.setOnClickListener {
            selectedAge = 40
            clickButton(binding.fourtiesButton)
        }
        binding.fiftiesButton.setOnClickListener {
            selectedAge = 50
            clickButton(binding.fiftiesButton)
        }

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                presenter.updateUserAge(UserInputInfoReqDTO(selectedAge,currentuser.userCategoryList, currentuser.gender), requireContext())
            }else{
                toast("연령대를 선택해주세요", requireContext())
            }
        }

    }

    override fun init() {
        when(currentuser.age){
            10 -> binding.teenagerButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            20 -> binding.twentiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            30 -> binding.thirtiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            40 -> binding.fourtiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            50 -> binding.fiftiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }
    }
    override fun clickButton(clickedButton: AppCompatButton){
        isClicked = true
        binding.teenagerButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.twentiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.thirtiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.fourtiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.fiftiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        clickedButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    override fun selectComplete() {
        binding.nextStepButton.setBackgroundResource(R.drawable.dark_box_rectangle)
    }

}