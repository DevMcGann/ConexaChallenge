package com.gsoft.conexachallenge.presentation.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.gsoft.conexachallenge.R
import com.gsoft.conexachallenge.data.DataMapper
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.model.Rating
import com.gsoft.conexachallenge.domain.model.Product

@Composable
fun ProductCard(
    product: Product,
    addToCart : (ProductDB) -> Unit
) {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (image, title, description, priceAndCart) = createRefs()

            AsyncImage(
                model = product.image,
                contentDescription = "cover image",
                placeholder = painterResource(id = R.drawable.bgimage),
                error = painterResource(id = R.drawable.bgimage),
                fallback = painterResource(id = R.drawable.bgimage),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.4f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(product.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 15.dp)
            )

            Text(product.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 3,
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(title.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .background(Color.DarkGray)
                    .padding(horizontal = 10.dp)
                .constrainAs(priceAndCart) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ){
                Text(
                    "$ ${product.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                IconButton(
                    onClick = { addToCart(DataMapper.domainToDB(product = product)) },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "add to cart",
                        tint = Color.White
                    )
                }
            }

        }//constrain layout
    }
}


@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val image = "https://imgs.search.brave.com/10oBnd8yappkiSrnW63PdplTHUoI59XFk0rCYlbo9yM/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5nZXR0eWltYWdl/cy5jb20vaWQvNjYw/MzM0ODcwL3Bob3Rv/L3Rlc2xhLW1vZGVs/LXgtcDkwZC1hbGwt/ZWxlY3RyaWMtY3Jv/c3NvdmVyLXN1di5q/cGc_cz02MTJ4NjEy/Jnc9MCZrPTIwJmM9/UktRLTlUWXRBUk1z/MmUxVk13VWtXeWN3/ZkZEaEw3RWdpR0xa/V3g4dkZTQT0"
    ProductCard(product = Product(1, "Tesla 2023", 550000.0,  "This is a Mocked Description for this product", "Vehicles", image,  Rating(1.0,1)),
        addToCart = {}
    )
}