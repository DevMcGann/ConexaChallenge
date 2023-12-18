package com.gsoft.conexachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsoft.conexachallenge.presentation.cart.CartScreen
import com.gsoft.conexachallenge.presentation.cart.CartViewModel
import com.gsoft.conexachallenge.presentation.error.ErrorScreen
import com.gsoft.conexachallenge.presentation.home.HomeScreen
import com.gsoft.conexachallenge.presentation.home.HomeViewModel
import com.gsoft.conexachallenge.presentation.noConnection.NoConnectionScreen
import com.gsoft.conexachallenge.presentation.splash.SplashScreen
import com.gsoft.conexachallenge.presentation.splash.SplashViewModel
import com.gsoft.conexachallenge.ui.theme.ConexaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val splashViewModel = hiltViewModel<SplashViewModel>()
            val splashState = splashViewModel.state

            val homeViewModel = hiltViewModel<HomeViewModel>()
            val homeState = homeViewModel.state

            val cartViewModel = hiltViewModel<CartViewModel>()
            val cartState by cartViewModel.state.collectAsState()

            ConexaChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "splash") {

                        composable("splash") {
                            SplashScreen(
                                state = splashState.value,
                                navController = navController
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                state = homeState.value,
                                addProductToCart = homeViewModel::addProductToCart,
                                getItemCount = homeViewModel::getCartCount,
                                showCategoryModal = homeViewModel::showCategoryModal,
                                hideCategoryModal = homeViewModel::hideCategoryModal,
                                onCategorySelect = homeViewModel::setSelectedCategory
                            )
                        }


                       composable("noConnection") {
                            NoConnectionScreen()
                        }

                        composable("error") {
                            ErrorScreen(
                                navController = navController
                            )
                        }

                        composable("cart") {
                            CartScreen(
                                navController = navController,
                                state = cartState,
                                removeProductFromCart = cartViewModel::removeProductFromCart,
                                addProductToCart = cartViewModel::addProductToCart,
                                clearCart = cartViewModel::cleanCart,
                                getCart = cartViewModel::getCart,
                                getTotalPrice = cartViewModel::getTotalPrice
                            )
                        }

                    }//NavHost


                } //Surface
            } //ConexaChallengeTheme
        } //setContent
    } //onCreate
} //MainActivity

