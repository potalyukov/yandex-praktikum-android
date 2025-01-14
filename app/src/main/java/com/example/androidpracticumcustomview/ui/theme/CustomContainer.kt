package com.example.androidpracticumcustomview.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

private const val maxElements = 2

class CustomContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val alphaDurationMillis: Long = 2000,
    private val yPosDurationMillis: Long = 5000
) : ViewGroup(context, attrs) {

    init {
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val childLeft = width / maxElements - childWidth / maxElements
            val childTop = if (i == 0) 0 else height - childHeight
            val childRight = childLeft + childWidth
            val childBottom = childTop + childHeight

            child.layout(childLeft, childTop, childRight, childBottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var desiredWidth = 0
        var desiredHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            desiredWidth = max(desiredWidth, child.measuredWidth)
            desiredHeight += child.measuredHeight
        }

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun addView(child: View) {
        if (childCount == maxElements) {
            throw IllegalStateException("CustomContainer can have two elements max")
        }

        super.addView(child)

        startAnimation(child)
    }

    private fun startAnimation(child: View) {
        child.alpha = 0f

        child.post {
            // alpha animation
            child
                .animate()
                .alpha(1f)
                .setDuration(alphaDurationMillis)
                .start()

            //position Y animation
            val originalY = child.y
            child.y = height / 2f - child.height / 2f
            child
                .animate()
                .y(originalY)
                .setDuration(yPosDurationMillis)
                .start()
        }
    }
}