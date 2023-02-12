package com.tapan.IntroApplication.services

import android.widget.Button
import com.tapan.IntroApplication.models.XOButtonModel
import java.util.stream.Collector
import java.util.stream.Collectors

class AutoPlayService {
    // autoplay logic

    fun autoPlay(isAutoPlayEnabled : Boolean, uiButtonList: ArrayList<Button>){
        if(isAutoPlayEnabled) {
            var enabledButtons = uiButtonList.stream().filter {
                it.isEnabled == true
            }.collect(Collectors.toList())

            if (!enabledButtons.isEmpty()) {
                // instead of random function algorithm, make more better algorithm
                var selectedButton = enabledButtons.random()

                selectedButton.performClick()
            }
        }
    }
}