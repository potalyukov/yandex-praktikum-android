package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?,
    animationDurationMillis: Int = 2000
) {
    val firstAlpha = remember { Animatable(0f) }
    val secondAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        firstChild?.let { firstAlpha.animateTo(targetValue = 1f, tween(animationDurationMillis)) }
        secondChild?.let { secondAlpha.animateTo(targetValue = 1f, tween(animationDurationMillis)) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        firstChild?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .alpha(firstAlpha.value)
            ) {
                it()
            }
        }

        secondChild?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .alpha(secondAlpha.value)
            ) {
                it()
            }
        }
    }
}
