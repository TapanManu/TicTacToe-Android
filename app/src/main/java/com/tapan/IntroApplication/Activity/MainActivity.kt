package com.tapan.IntroApplication.Activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.tapan.introapplication.R
import com.tapan.IntroApplication.models.GameStatus
import com.tapan.IntroApplication.services.AutoPlayService
import com.tapan.IntroApplication.services.GameService

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var service = GameService()
    var autoPlayService = AutoPlayService()
    var gameStatus = GameStatus.INIT
    lateinit var result : TextView
    lateinit var uiButtonList : ArrayList<Button>
    lateinit var resetButton : Button
    var lastPlayedByUser = true
    var isAutoPlayEnabled : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result =  findViewById<TextView>(R.id.resultText)
        result.setText("Start Game")
        result.setTextColor(Color.WHITE)
        uiButtonList = arrayListOf<Button>(findViewById<Button>(R.id.btn1), findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3), findViewById<Button>(R.id.btn4), findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6), findViewById<Button>(R.id.btn7), findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9))

        resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener(this)

        uiButtonList.stream().forEach {
            it.setOnClickListener(this)
            it.isEnabled = true
        }
        service.initGame()
        Log.i("Starter","Starting Application")

    }

    override fun onResume() {
        super.onResume()
        if(gameStatus != GameStatus.PLAYING){
            lastPlayedByUser = true
            isAutoPlayEnabled = false
            service.resetGame(uiButtonList, result)
        }
    }

    fun btnClick(view: View?) {
        var btnSelected = view as Button
        var btnId = -1

        if(btnSelected.isEnabled == true) {

            when (btnSelected.id) {
                R.id.btn1 -> btnId = 0
                R.id.btn2 -> btnId = 1
                R.id.btn3 -> btnId = 2
                R.id.btn4 -> btnId = 3
                R.id.btn5 -> btnId = 4
                R.id.btn6 -> btnId = 5
                R.id.btn7 -> btnId = 6
                R.id.btn8 -> btnId = 7
                R.id.btn9 -> btnId = 8
                R.id.resetButton -> {
                    lastPlayedByUser = true
                    isAutoPlayEnabled = false
                    service.resetGame(uiButtonList, result)
                    return;
                }
            }
            Log.i("BtnActivity",btnId.toString());
            updateStatus(btnId, btnSelected)
            btnSelected.isEnabled = false

            lastPlayedByUser = !lastPlayedByUser

        }
        else{
            Toast.makeText(this, "Button already selected", Toast.LENGTH_SHORT)
        }

    }

    private fun updateStatus(btnId: Int, btnSelected: Button) {
        gameStatus = service.playGame(btnId, btnSelected)
        Log.i("Status", gameStatus.toString())

        when(gameStatus){
            GameStatus.WIN -> {
                result.setText("You have won")
                result.setTextColor(Color.GREEN)
                service.disableAllGameButtons(uiButtonList)
            }
            GameStatus.LOSE -> {
                result.setText("You have lost, Try again next time")
                result.setTextColor(Color.RED)
                service.disableAllGameButtons(uiButtonList)
            }
            GameStatus.DRAW -> {
                result.setText("Match drawn")
                result.setTextColor(Color.WHITE)
                service.disableAllGameButtons(uiButtonList)
            }
            else -> {
                result.setText("")
                isAutoPlayEnabled = true
                // game resumes
            }
        }

    }

    override fun onClick(view: View?) {
        btnClick(view)
        if(lastPlayedByUser == false){
            Handler().postDelayed({
                autoPlayService.autoPlay(isAutoPlayEnabled,uiButtonList)
            }, 1000)

        }
    }


}