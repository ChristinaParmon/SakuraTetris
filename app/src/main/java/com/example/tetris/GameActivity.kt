package com.example.tetris
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.models.Appmodel
import com.example.tetris.storage.AppPreferences
import com.example.tetris.view.TetrisView

class GameActivity: AppCompatActivity()  {
    var tvHighScore: TextView?= null
    var tvCurrentScore: TextView?=null

    private lateinit var tetrisView: TetrisView
    var appPreferences: AppPreferences?=null
    private val appModel: Appmodel = Appmodel()

    public override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        val btnRestart = findViewById<Button>(R.id.btn_restart)
        tvHighScore = findViewById(R.id.tv_high_score)
        tvCurrentScore = findViewById(R.id.tv_currentscore)
        tetrisView = findViewById<TetrisView>(R.id.view_tetris)
        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)

        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        btnRestart.setOnClickListener(this::btnRestartClick)

        updateHighScore()
        updateCurrentScore()

    }

    private fun btnRestartClick(view: View){
        appModel.restartGame()
    }

    private fun onTetrisViewTouch (view: View, event:MotionEvent):
        Boolean{
            if (appModel.isGameOver() || appModel.isGameAwaitingStart()){
                appModel.startGame()
                tetrisView.setGameCommandWithDelay(Appmodel.Motions.DOWN)
            }else if (appModel.isGameActive()){
                when (resolveTouchDirection(view, event)){
                    0 -> moveTetromino (Appmodel.Motions.LEFT)
                    1 -> moveTetromino (Appmodel.Motions.ROTATE)
                    2 -> moveTetromino (Appmodel.Motions.DOWN)
                    3 -> moveTetromino (Appmodel.Motions.RIGHT)
                }
            }
            return true
        }

    private fun resolveTouchDirection(view: View, event: MotionEvent):
            Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction: Int
        direction = if (y>x) {
            if (x> 1 - y) 2 else 0
        } else {
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetromino (motion: Appmodel.Motions){
        if (appModel.isGameActive()){
            tetrisView.setGameCommand(motion)
        }
    }



    private fun updateCurrentScore() {
        tvHighScore?.text =  "${appPreferences?.getHighScore()}"
    }

    private fun updateHighScore() {
        tvCurrentScore?.text ="0"
    }
}