package com.example.shaketoonflashlight

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        val (button, toggle) = createRefs()
        Button(modifier = Modifier.constrainAs(button) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 100.dp)
            end.linkTo(parent.end, margin = 100.dp)
            width = Dimension.fillToConstraints
        })

        ToShakeOn(modifier = Modifier.constrainAs(toggle) {
            top.linkTo(button.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
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

@Composable
fun ToShakeOn(modifier: Modifier) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
    val switchState = remember { mutableStateOf(prefs.getBoolean("switch_state", false)) }

    Switch(
        checked = switchState.value,
        onCheckedChange = {
            switchState.value = it
            prefs.edit().putBoolean("switch_state", it).apply()
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Preview1() {
    MaterialTheme {
        MainView()
    }
}


