package com.gsoft.conexachallenge.presentation.cart.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gsoft.conexachallenge.R
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.usecase.AddProduct


@Composable
fun CartItem(
    productDB: ProductDB,
    addProduct : (ProductDB) -> Unit,
    removeProduct : (ProductDB) -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.DarkGray)
    ){
        Row(
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(
                model = productDB.image,
                contentDescription = "cover image",
                placeholder = painterResource(id = R.drawable.bgimage),
                error = painterResource(id = R.drawable.bgimage),
                fallback = painterResource(id = R.drawable.bgimage),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.4f)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = productDB.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )

                Text(
                    text = productDB.description,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )

                Text(
                    text = "$${productDB.price}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )


                Row(
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    IconButton(onClick = { addProduct(productDB) }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add",
                            tint = Color.Green
                        )
                    }

                    Box(
                        modifier = Modifier.clip(CircleShape)
                            .fillMaxHeight()
                            .width(50.dp)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "${productDB.quantity}",
                            color = Color.White,
                            fontSize = 20.sp,
                        )
                    }

                    IconButton(onClick = { removeProduct(productDB) }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "delete",
                            tint = Color.Red
                        )
                    }


                }


            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    val image = "https://imgs.search.brave.com/10oBnd8yappkiSrnW63PdplTHUoI59XFk0rCYlbo9yM/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5nZXR0eWltYWdl/cy5jb20vaWQvNjYw/MzM0ODcwL3Bob3Rv/L3Rlc2xhLW1vZGVs/LXgtcDkwZC1hbGwt/ZWxlY3RyaWMtY3Jv/c3NvdmVyLXN1di5q/cGc_cz02MTJ4NjEy/Jnc9MCZrPTIwJmM9/UktRLTlUWXRBUk1z/MmUxVk13VWtXeWN3/ZkZEaEw3RWdpR0xa/V3g4dkZTQT0"
    CartItem(
        productDB = ProductDB(1, "Tesla 2023", 550000.0,  "This is a Mocked Description for this product", "Vehicles", image, ),
        addProduct = {},
        removeProduct = {}
    )

}