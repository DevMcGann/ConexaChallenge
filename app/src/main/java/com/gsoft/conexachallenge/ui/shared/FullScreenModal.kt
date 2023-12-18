package com.gsoft.conexachallenge.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.gsoft.conexachallenge.R


@Composable
fun FullScreenModal(
    color: Color = Color.White,
    bgImage: Int = R.drawable.overlay,
    modalHeightFloat: Float = .95f,
    onCloseModal: ()-> Unit,
    modalVisible:  Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = modalVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 650, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 650, easing = FastOutLinearInEasing),
        ),
        modifier = Modifier.zIndex(100f)
        ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = bgImage),
                contentDescription = "Modal Background Image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(fraction = modalHeightFloat)
                        .fillMaxWidth()
                        .background(color),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { onCloseModal() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                    content()
                }
            }
        }
    }
}


@Preview
@Composable
fun CustomPrev(){
    FullScreenModal(color = Color.White, bgImage = R.drawable.overlay, onCloseModal = {}, modalVisible = true) {
       Column(modifier = Modifier.fillMaxSize()){
           Text("Hola")
       }
    }
}