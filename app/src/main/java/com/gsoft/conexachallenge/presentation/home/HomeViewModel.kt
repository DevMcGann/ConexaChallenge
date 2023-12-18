package com.gsoft.conexachallenge.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.domain.usecase.AddProduct
import com.gsoft.conexachallenge.domain.usecase.GetCart
import com.gsoft.conexachallenge.domain.usecase.GetProducts
import com.gsoft.conexachallenge.util.NetworkUtils
import com.gsoft.conexachallenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProducts,
    private val getCartProductsUseCase: GetCart,
    private val addProduct: AddProduct,
    private val networkUtils: NetworkUtils
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            setLoadingState()
            if (! networkUtils.isNetworkConnected()){
                setNoInternetState()
            }else{
                try{
                    when (val response = getProductsUseCase.invoke()){
                        Resource.success(response.data) -> {
                            setSuccessState(response.data.orEmpty())
                        }
                        Resource.error(message = response.message.orEmpty(), data = null) -> {
                            setErrorState(response.message.orEmpty())

                        }
                    }
                }catch (e: Exception){
                    setErrorState(e.message.toString())
                }
            }
        }
    }

    private fun setLoadingState(){
        _state.value = _state.value.copy(isLoading = true)
        _state.value = _state.value.copy(isError = false)
        _state.value = _state.value.copy( data = emptyList())
        _state.value = _state.value.copy(error = "")
    }

    private fun setSuccessState(data :  List<Product>){
        _state.value = _state.value.copy(data = data)
        _state.value = _state.value.copy(isLoading = false)
        getCategories(data)
    }

    private fun setErrorState(message: String){
        _state.value = _state.value.copy(isLoading = false)
        _state.value = _state.value.copy(isError = true)
        _state.value = _state.value.copy(error = message)
        _state.value = _state.value.copy(data = emptyList())
    }

    private fun setNoInternetState(){
        _state.value = _state.value.copy(isLoading = false)
        _state.value = _state.value.copy(isError = false)
        _state.value = _state.value.copy(noInternet = true)
        _state.value = _state.value.copy(error = "No internet connection")
        _state.value = _state.value.copy(data = emptyList())
    }


     fun getCartCount() {
        viewModelScope.launch {
            getCartProductsUseCase.invoke().collect{
                _state.value = _state.value.copy(cartItemCount = it.size)
            }
        }
    }

    fun addProductToCart(productDB: ProductDB) {
        viewModelScope.launch(Dispatchers.IO) {
            addProduct.invoke(productDB)
        }
    }

    fun hideCategoryModal() {
        _state.value = _state.value.copy(isCategoryModalVisible = false)
    }

    fun showCategoryModal() {
        _state.value = _state.value.copy(isCategoryModalVisible = true)
    }

    fun setSelectedCategory(category: String) {
        _state.value = _state.value.copy(selectedCategory = category)
        hideCategoryModal()
    }

    private fun getCategories (list : List<Product>) {
        val categories = mutableListOf<String>()
        for (product in list ){
            categories.add(product.category)
        }
        _state.value = _state.value.copy(categories = categories.distinct().toList())
    }

}