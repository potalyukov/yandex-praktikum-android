package com.example.androidpracticumcustomview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.example.androidpracticumcustomview.ui.theme.CustomContainer
import com.example.androidpracticumcustomview.ui.theme.MainScreen

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Раскомментируйте нужный вариант

        startXmlPracticum() // «традиционный» android (XML)

        /*setContent { // Jetpack Compose
           MainScreen()
        }*/
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        customContainer.setBackgroundColor(getColor(R.color.teal_700))

        val layoutParams =
            FrameLayout.LayoutParams(
                500, 500
                //ViewGroup.LayoutParams.MATCH_PARENT,
                //ViewGroup.LayoutParams.MATCH_PARENT
            )
        layoutParams.gravity = Gravity.CENTER
        customContainer.layoutParams = layoutParams

        val rootContainer = FrameLayout(this)
        rootContainer.setBackgroundColor(getColor(R.color.black))
        rootContainer.addView(customContainer)

        setContentView(rootContainer)

        val firstView = TextView(this).apply {
            text = "first text"
            setBackgroundColor(getColor(R.color.purple_200))
        }

        val secondView = TextView(this).apply {
            text = "second text"
            setBackgroundColor(getColor(R.color.teal_200))
        }

        val thirdView = TextView(this).apply {
            text = "third text"
            setBackgroundColor(getColor(R.color.purple_200))
        }

        customContainer.addView(firstView)

        // Добавление второго элемента через некоторое время
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)


        // Добавление третьего элемента через некоторое время. Ловим ожидаемое исключение
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                customContainer.addView(thirdView)
            } catch (e: IllegalStateException) {
                Log.e(null, e.toString())
            }
        }, 4000)
    }
}