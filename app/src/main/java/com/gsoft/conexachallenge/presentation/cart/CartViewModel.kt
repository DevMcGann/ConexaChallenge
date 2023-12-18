package com.gsoft.conexachallenge.presentation.cart


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.usecase.AddProduct
import com.gsoft.conexachallenge.domain.usecase.CleanCart
import com.gsoft.conexachallenge.domain.usecase.GetCart
import com.gsoft.conexachallenge.domain.usecase.GetTotalPrice
import com.gsoft.conexachallenge.domain.usecase.RestProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addProduct: AddProduct,
    private val restProduct: RestProduct,
    private val cleanCart: CleanCart,
    private val getCart: GetCart,
    private val getTotalPrice: GetTotalPrice
) : ViewModel() {


    private val _cartState = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _cartState


    fun getCart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCart.invoke().collect { data ->
                    _cartState.value = CartState(data = data, isLoading = false)
                }
            } catch (e: Exception) {
                _cartState.value = CartState(isLoading = false, isError = true)
            }
        }
    }

    fun getTotalPrice() {
            viewModelScope.launch(Dispatchers.IO) {
                getTotalPrice.invoke().collect {
                    if (it == null) {
                        _cartState.value = _cartState.value.copy(total = 0.0)
                    }else{
                        _cartState.value = _cartState.value.copy(total = it)
                    }

                }
            }
    }


    fun addProductToCart(productDB: ProductDB) {
        viewModelScope.launch(Dispatchers.IO) {
            addProduct(productDB)
        }
    }

    fun removeProductFromCart(productDB: ProductDB) {
        viewModelScope.launch(Dispatchers.IO) {
            restProduct(productDB)
        }
    }


    fun cleanCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cleanCart.invoke()
        }
    }



}