package com.gsoft.conexachallenge.presentation.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.gsoft.conexachallenge.R
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.usecase.GetCart
import com.gsoft.conexachallenge.presentation.cart.composables.CartItem
import com.gsoft.conexachallenge.presentation.home.composables.ProductCard
import com.gsoft.conexachallenge.ui.shared.FullScreenBackground

@Composable
fun CartScreen(
    navController: NavController,
    state : CartState,
    addProductToCart : (ProductDB) -> Unit,
    removeProductFromCart : (ProductDB) -> Unit,
    clearCart : () -> Unit,
    getCart : () -> Unit,
    getTotalPrice : () -> Unit
) {


    //yes i could not use this and do the init block in the viewModel too.
    LaunchedEffect(key1 = state.data ){
        getCart()
        getTotalPrice()

    }

    FullScreenBackground (
        backgroundImage = painterResource(id = R.drawable.overlay)
    ){

       ConstraintLayout(
           modifier = Modifier.fillMaxSize()
       ) {
            val (title, cart, back ,clear, total) = createRefs()

           Text("Cart", modifier = Modifier.constrainAs(title){
               top.linkTo(parent.top, margin = 20.dp)
               start.linkTo(parent.start)
               end.linkTo(parent.end) },
               textAlign = TextAlign.Center,
               fontWeight = FontWeight.Bold,
               fontSize = 40.sp,
               color = Color.White
           )

           IconButton(
               onClick = { navController.popBackStack() },
               modifier = Modifier.constrainAs(back){
               top.linkTo(parent.top, margin = 20.dp)
               start.linkTo(parent.start, margin = 20.dp)
               },
           ){
               Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" , tint = Color.White)
           }


           LazyColumn(
               horizontalAlignment = Alignment.Start,
               content = {
                   state.data.let { product ->
                       items(product.size) { it ->
                           CartItem(
                               productDB = product[it],
                               addProduct = { addProductToCart(it)  },
                               removeProduct = { removeProductFromCart(it) }
                           )
                           Spacer(modifier = Modifier.height(25.dp))
                       }
                   }
               },
               modifier = Modifier
                   .padding(horizontal = 15.dp, vertical = 25.dp)
                   .heightIn(max = 600.dp)
                   .constrainAs(cart) {
                       top.linkTo(title.bottom, margin = 20.dp)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                   }
           )

           Text("Total: $${String.format("%.2f", state.total)}", modifier = Modifier.constrainAs(total){
               bottom.linkTo(parent.bottom, margin = 30.dp)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
           },
               textAlign = TextAlign.Center,
               fontWeight = FontWeight.Bold,
               fontSize = 20.sp,
               color = Color.White
           )


           IconButton(
               onClick = { clearCart() },
               modifier = Modifier.constrainAs(clear){
                   bottom.linkTo(parent.bottom, margin = 20.dp)
                   end.linkTo(parent.end, margin = 20.dp)
               }
           ) {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "delete",
                   tint = Color.White
               )
               
           }

       }

    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(
        navController = NavController(LocalContext.current),
        state = CartState(),
        addProductToCart = {},
        removeProductFromCart = {},
        clearCart = {},
        getCart = {},
        getTotalPrice = {}
    )
}