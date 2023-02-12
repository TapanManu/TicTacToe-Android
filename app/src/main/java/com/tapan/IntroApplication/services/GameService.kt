package com.tapan.IntroApplication.services

import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import com.tapan.IntroApplication.models.GameStatus
import com.tapan.IntroApplication.models.XOButtonModel
import java.util.stream.Collector
import java.util.stream.Collectors

class GameService{

    lateinit var btnList : ArrayList<XOButtonModel> ;

    companion object {
        var activePlayer = 'X'
        var btnCount = 0
    }

    public fun initGame() {
            btnList = arrayListOf<XOButtonModel>()

            for(i in 0..8) {
                btnList.add(XOButtonModel(i, 'D'));
            }
    }

    public fun playGame(btnId : Int, button: Button) : GameStatus{

            btnList.get(btnId).playerName = activePlayer

            if(activePlayer == 'X'){
                button.setText("X")
                button.setTextColor(Color.parseColor("#6f9fed"))
                activePlayer = 'O'
            }
            else{
                button.setText("O")
                button.setTextColor(Color.parseColor("#edbb50"))
                activePlayer = 'X'
            }


            btnCount = btnCount + 1
            return checkGameStatus(btnList, btnCount)
    }

    public fun resetGame(uiButtonList : ArrayList<Button>, result : TextView){
        btnCount = 0
        uiButtonList.stream().forEach{
            it.setText("")
            it.isEnabled = true
        }
        btnList.stream().forEach {
            it.playerName = 'D'
        }
        activePlayer = 'X'
        result.setText("Start the game")
    }

    public fun disableAllGameButtons(uiButtonList : ArrayList<Button>){
        uiButtonList.stream().forEach {
            it.isEnabled = false
        }
    }

    // logic of game winner determines in this strategy
    private fun checkGameStatus(buttonList : ArrayList<XOButtonModel>, btnCount : Int ) : GameStatus {

           val hasXWon = checkWinner(buttonList, 'X')
           val hasOWon = checkWinner(buttonList, 'O')

            if(hasXWon){
                return GameStatus.WIN;
            }
            if(hasOWon){
                return GameStatus.LOSE;
            }
            if(btnCount == 9){
                return GameStatus.DRAW;
            }

            return GameStatus.PLAYING;
    }

    private fun checkWinner(buttonList: ArrayList<XOButtonModel>, winner : Char) : Boolean {
        return (buttonList[0].playerName == winner && buttonList[3].playerName == winner && buttonList[6].playerName == winner) ||
        (buttonList[1].playerName == winner && buttonList[4].playerName == winner && buttonList[7].playerName == winner) ||
        (buttonList[2].playerName == winner && buttonList[5].playerName == winner && buttonList[8].playerName == winner) ||
        (buttonList[0].playerName == winner && buttonList[1].playerName == winner && buttonList[2].playerName == winner) ||
        (buttonList[3].playerName == winner && buttonList[4].playerName == winner && buttonList[5].playerName == winner) ||
        (buttonList[6].playerName == winner && buttonList[7].playerName == winner && buttonList[8].playerName == winner) ||
        (buttonList[0].playerName == winner && buttonList[4].playerName == winner && buttonList[8].playerName == winner) ||
        (buttonList[2].playerName == winner && buttonList[4].playerName == winner && buttonList[6].playerName == winner) ;
    }
}