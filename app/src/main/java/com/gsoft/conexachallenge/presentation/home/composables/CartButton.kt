package com.gsoft.conexachallenge.presentation.home.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gsoft.conexachallenge.presentation.home.HomeState

@Composable
fun CartButton(
    state : HomeState,
    onClick : () -> Unit
) {
    Box(
        modifier = Modifier.clip(CircleShape).background(Color.Black)
            .padding(8.dp).size(60.dp)
    ){
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){

            IconButton(
                onClick = {
                    onClick()
                          },
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "go to cart",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            Text(text = state.cartItemCount.toString(),
                color = Color.Red,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CartButtonPreview() {
    CartButton(
        state = HomeState(),
        onClick = {}
    )
}