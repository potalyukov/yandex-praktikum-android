package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    alphaDurationMillis: Int = 2000,
    offsetDurationMillis: Int = 5000
) {
    val density = LocalDensity.current.density

    val firstAlpha = remember { Animatable(0f) }
    val firstOffsetY = remember { Animatable(0f) }

    val secondAlpha = remember { Animatable(0f) }
    val secondOffsetY = remember { Animatable(0f) }

    var parentHeight by remember { mutableFloatStateOf(0f) }
    var firstHeight by remember { mutableFloatStateOf(0f) }
    var secondHeight by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        launch {
            firstAlpha.animateTo(targetValue = 1f, tween(alphaDurationMillis))
            secondAlpha.animateTo(targetValue = 1f, tween(alphaDurationMillis))
        }
        launch {
            firstOffsetY.animateTo(
                targetValue = -parentHeight / 2 + firstHeight / 2,
                tween(offsetDurationMillis)
            )
        }
        launch {
            delay(alphaDurationMillis.toLong())
            secondOffsetY.animateTo(
                targetValue = parentHeight / 2f - secondHeight / 2,
                tween(offsetDurationMillis),
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onPlaced { layoutCoordinates ->
                parentHeight = layoutCoordinates.size.height.toFloat() / density
            }
    ) {
        firstChild?.let {
            Box(
                modifier = Modifier
                    .offset(y = firstOffsetY.value.dp)
                    .align(Alignment.Center)
                    .onPlaced { layoutCoordinates ->
                        firstHeight = layoutCoordinates.size.height / density
                    }
                    .alpha(firstAlpha.value)
            ) {
                it()
            }
        }

        secondChild?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = secondOffsetY.value.dp)
                    .onPlaced { layoutCoordinates ->
                        secondHeight = layoutCoordinates.size.height / density
                    }
                    .alpha(secondAlpha.value)
            ) {
                it()
            }
        }
    }
}