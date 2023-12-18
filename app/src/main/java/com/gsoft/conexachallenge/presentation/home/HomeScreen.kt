package com.gsoft.conexachallenge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.gsoft.conexachallenge.R
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.model.Rating
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.presentation.error.ErrorScreen
import com.gsoft.conexachallenge.presentation.home.composables.CartButton
import com.gsoft.conexachallenge.presentation.home.composables.ProductCard
import com.gsoft.conexachallenge.ui.shared.CategoryModalContent
import com.gsoft.conexachallenge.ui.shared.CustomField
import com.gsoft.conexachallenge.ui.shared.FullScreenBackground
import com.gsoft.conexachallenge.ui.shared.FullScreenModal

@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    addProductToCart : (ProductDB) -> Unit,
    getItemCount : () -> Unit,
    showCategoryModal : () -> Unit,
    hideCategoryModal : () -> Unit,
    onCategorySelect : (String) -> Unit
) {

    val search = remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.data ){
        getItemCount()
    }

    FullScreenBackground(
        backgroundImage = painterResource(id = R.drawable.overlay)
    ) {

        FullScreenModal(
            onCloseModal = { hideCategoryModal() },
            modalVisible = state.isCategoryModalVisible,
            color = Color.DarkGray
        ) {
            CategoryModalContent(
                closeModal = { hideCategoryModal() },
                categories = state.categories ,
                onCategorySelected = { onCategorySelect(it) }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(state.noInternet){
                navController.navigate("noConnection"){
                    popUpTo("home"){
                        inclusive = true
                    }
                }
            }

            if(state.isError){
                ErrorScreen(
                    navController = navController
                )
            }


            if (state.isLoading){
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }else{
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (topBar, feed, cart) = createRefs()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(horizontal = 12.dp, vertical = 20.dp)
                            .background(Color.Transparent)
                            .constrainAs(topBar) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        CustomField(textState = search)
                        IconButton(onClick = { showCategoryModal() }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "category",
                                tint = Color.White
                            )
                        }
                    }

                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        content = {
                            state.data
                                .filter { product ->
                                    product.title.contains(search.value, ignoreCase = true) &&
                                            (state.selectedCategory.isEmpty() || product.category.contains(state.selectedCategory, ignoreCase = true))
                                }
                                .sortedBy { it.title }
                                .let { product ->
                                    items(product.size) {
                                        ProductCard(
                                            product = product[it],
                                            addToCart = addProductToCart
                                        )
                                        Spacer(modifier = Modifier.height(25.dp))
                                    }
                                }
                        },
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .heightIn(max = 700.dp)
                            .constrainAs(feed) {
                                top.linkTo(topBar.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )


                    Box(
                        modifier = Modifier.constrainAs(cart){
                            bottom.linkTo(parent.bottom, margin = 20.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                    ) {
                        CartButton(state = state, onClick = {navController.navigate("cart")})
                    }

                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val product = listOf(
        Product(
            id = 1,
            title = "Product 1",
            description = "Description",
            category = "Category",
            image = "",
            price = 0.0,
            rating = Rating(0.0, 0)
        ),
                Product(
                id = 2,
        title = "Product 2",
        description = "Description",
        category = "Category",
        image = "",
        price = 0.0,
        rating = Rating(0.0, 0)
    )
    )
    HomeScreen(
        navController = NavController(LocalContext.current),
        state = HomeState(
            data = product,
        ),
        addProductToCart = {},
        getItemCount = {},
        showCategoryModal = {},
        hideCategoryModal = {},
        onCategorySelect = {}
    )
}