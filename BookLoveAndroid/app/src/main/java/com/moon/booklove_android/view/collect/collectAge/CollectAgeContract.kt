package com.moon.booklove_android.view.collect.collectAge

import androidx.appcompat.widget.AppCompatButton
import com.moon.booklove_android.config.enum.enumAgeRange

interface CollectAgeContract {
    interface View {
        fun clickButton(clickedButton: AppCompatButton, enumAgeRange: enumAgeRange)
        fun goCollectCategory()
    }
}