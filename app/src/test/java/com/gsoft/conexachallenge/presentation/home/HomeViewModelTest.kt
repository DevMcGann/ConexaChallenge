package com.gsoft.conexachallenge.presentation.home

import com.gsoft.conexachallenge.data.model.Rating
import com.gsoft.conexachallenge.data.repository.ProductRepository
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.domain.usecase.AddProduct
import com.gsoft.conexachallenge.domain.usecase.GetCart
import com.gsoft.conexachallenge.domain.usecase.GetProducts
import com.gsoft.conexachallenge.util.NetworkUtils
import com.gsoft.conexachallenge.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest{
    val getProductsUseCase: GetProducts by lazy { mockk(relaxed = true) }
    val getCartProductsUseCase: GetCart by lazy { mockk(relaxed = true) }
    val addProduct: AddProduct by lazy { mockk(relaxed = true) }
    val networkUtils: NetworkUtils by lazy { mockk(relaxed = true) }

    val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()


    val homeViewModel : HomeViewModel by lazy {
        HomeViewModel(getProductsUseCase, getCartProductsUseCase, addProduct, networkUtils)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getProducts()  = runTest(this.dispatcher){
        val products = listOf(
            Product(id = 1, title = "Product 1", description = "Description 1", price = 10.0, category = "Category 1", image = "Image 1", rating = Rating(1.0, 1)),
        )

        val response = Resource.success(products)


        coEvery { getProductsUseCase.invoke() } returns response

        homeViewModel.getProducts()

        advanceUntilIdle()

        coVerify {
            homeViewModel.getProducts()
        }
        val state = homeViewModel.state.value
        print("state: ${state.data}")

        assert(state.data == products)
    }



}