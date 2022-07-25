package com.example.tetris.storage
import android.content.Context
import android.content.SharedPreferences

class AppPreferences (ctx: Context){
    var data: SharedPreferences = ctx.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveHighScore(highScore:Int){
        data.edit().putInt("HIGH_SCORE", highScore).apply() //high_score - ключ
    }

    fun getHighScore(): Int{
        return data.getInt("HIGH_SCORE", 0) //если ключ отсутствует, возвращает ноль
    }

    fun clearHighScore(){
        data.edit().putInt("HIGH_SCORE", 0).apply()
    }
}