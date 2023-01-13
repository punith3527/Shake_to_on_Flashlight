package com.example.shaketoonflashlight

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.math.roundToInt

@Composable
fun MainView() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
    ) {
        val (button, toggle, element) = createRefs()
        Button(modifier = Modifier.constrainAs(button) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 100.dp)
            end.linkTo(parent.end, margin = 100.dp)
            //width = Dimension.fillToConstraints
        })

        ToShakeOn(modifier = Modifier.constrainAs(toggle) {
            top.linkTo(button.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        //MovingButton(modifier = Modifier.constrainAs(element) {
          //  top.linkTo(parent.top, margin = 10.dp)
           // end.linkTo(parent.end, margin = 10.dp)
        //})
    }
}

@Composable
fun Button(modifier: Modifier) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.flashlight_icon),
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

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Shake to ON",
            modifier = Modifier,
            fontSize = 26.sp,
            color = Color.White
        )

        Switch(
            checked = switchState.value,
            onCheckedChange = {
                switchState.value = it
                prefs.edit().putBoolean("switch_state", it).apply()
            },
            modifier = Modifier
        )
    }
}
/**
@Composable
fun MovingButton(modifier: Modifier){
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        text = "Drag me!"
    )
}

@Composable
fun Toolbar() {
    val openDrawer = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My App Title") },
                navigationIcon = {
                    IconButton(onClick = { openDrawer.value = true },)
                }
            )
        },
        drawerContent = {
            Drawer(
                modifier = Modifier.drawerState(openDrawer),
                drawerState = openDrawer.value,
                onCloseRequest = { openDrawer.value = false },
                children = {
                    // Drawer content here
                }
            )
        },
        bodyContent = {
            // Body content here
        }
    )
}
**/

@Preview(showBackground = true)
@Composable
fun Preview1() {
    MaterialTheme {
        MainView()
    }
}


