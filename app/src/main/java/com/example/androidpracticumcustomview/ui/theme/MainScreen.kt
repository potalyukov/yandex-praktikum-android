package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты.
*/

@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            CustomContainerCompose(
                firstChild = {
                    Text(stringResource(R.string.simple_text))
                },
                secondChild = {
                    Button({}) {
                        Text(stringResource(R.string.button_text))
                    }
                }
            )
        }
    }
}