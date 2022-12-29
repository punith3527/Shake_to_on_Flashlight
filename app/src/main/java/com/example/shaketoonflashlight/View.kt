package com.example.shaketoonflashlight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MainView() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
    ) {
        val (button) = createRefs()
        Button(modifier = Modifier.constrainAs(button) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 100.dp)
            end.linkTo(parent.end, margin = 100.dp)
            width = Dimension.fillToConstraints
        })
    }
}

@Composable
fun Button(modifier: Modifier) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.on),
        contentDescription = stringResource(id = R.string.on_switch),
        modifier = modifier.clickable {
            OnButtonPressed().onButtonClick(context)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview1() {
    MaterialTheme {
        MainView()
    }
}


