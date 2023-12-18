package com.gsoft.conexachallenge.presentation.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(
    category : String,
    isSelected : Boolean,
    onClick : (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = category,
            fontSize = 24.sp,
            color = Color.White

        )
        RadioButton(
            selected = isSelected,
            onClick = { onClick(category) },
            modifier = Modifier.scale(1.3f),
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White,
                unselectedColor = Color.White,
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    CategoryItem(category = "Category", isSelected = true, onClick = {})
}