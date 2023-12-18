package com.gsoft.conexachallenge.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsoft.conexachallenge.presentation.home.composables.CategoryItem


@Composable
fun CategoryModalContent(
    closeModal : () -> Unit,
    categories : List<String>,
    onCategorySelected : (String) -> Unit
){
    val categorySelected = remember { mutableStateOf("") }

    fun handleCategory(category : String){
        categorySelected.value = category
        onCategorySelected(category)
        closeModal()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (category in categories){
            CategoryItem(
                category = category,
                isSelected = categorySelected.value == category,
                onClick = { handleCategory(category) }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Clear Filter", color = Color.White, fontSize = 24.sp, modifier = Modifier.clickable { handleCategory("") })
    }


}

@Preview(showBackground = true)
@Composable
fun CategoryModalContentPreview() {
    CategoryModalContent(
        closeModal = {},
        categories = listOf("Category 1", "Category 2", "Category 3"),
        onCategorySelected = {}
    )
}