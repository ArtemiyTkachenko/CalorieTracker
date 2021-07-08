package com.artkachenko.recipe_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.recipe_list.recipe_list.RecipeListFragment
import com.artkachenko.recipe_list.recipe_list.RecipeListViewModel
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RecipeListFragmentTest : TestCase() {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @MockK(relaxed = true)
    lateinit var navController: NavController

    val mockViewModel = mockkClass(RecipeListViewModel::class, relaxed = true)

    val mockMutableReponse = MutableStateFlow<List<RecipeEntity>>(mutableListOf())

    val mockResponse: StateFlow<List<RecipeEntity>>
        get() = mockMutableReponse

    @Before
    fun setUp() {
        clearAllMocks()
        MockKAnnotations.init(this)
        rule.inject()
        every {
            mockViewModel.viewModelScope
        } returns CoroutineScope(Job() + Dispatchers.Main.immediate)
    }

    @BindValue
    @JvmField
    val viewModel: RecipeListViewModel = mockViewModel

    private val list = listOf<RecipeEntity>(
        RecipeEntity(id = 637876, title = "Chicken 65", image = "Chicken-65-(-Chicken-Marinaded-In-Traditional-Indian-Spices-and-Deep-Fried)-637876.jpg"),
        RecipeEntity(id = 629963, title = "chilli chicken", image = "chilli-chicken-629963.jpg"),
        RecipeEntity(id = 632810, title = "Asian Chicken", image = "Asian-Chicken-632810.jpg"),
        RecipeEntity(id = 633959, title = "Balti Chicken", image = "Balti-Chicken-633959.jpg"),
        RecipeEntity(id = 634476, title = "Bbq Chicken", image = "Bbq-Chicken-634476.jpg"),
        RecipeEntity(id = 637942, title = "Chicken Arrozcaldo", image = "Chicken-Arrozcaldo-637942.jpg"),
        RecipeEntity(id = 637999, title = "Chicken Burritos", image = "Chicken-Burrito-By-Bing-637999.jpg"),
        RecipeEntity(id = 638002, title = "Chicken Cacciatore", image = "Chicken-Cacciatore-638002.jpg"),
        RecipeEntity(id = 638086, title = "Chicken Fingers", image = "Chicken-Fingers-638086.jpg"),
        RecipeEntity(id = 638125, title = "Chicken In A Pot", image = "Chicken-In-A-Pot-638125.jpg")
    )

    fun launchFragment(callback: (Fragment) -> Unit) {
        launchFragmentInHiltContainer<RecipeListFragment>(
            Bundle(),
            themeResId = com.artkachenko.ui_utils.R.style.Theme_CalorieTracker
        ) {
            GlobalScope.launch(Dispatchers.Main) {
                viewLifecycleOwnerLiveData.observeForever { viewLifeCycleOwner ->
                    if (viewLifeCycleOwner != null) {
                        Navigation.setViewNavController(
                            this@launchFragmentInHiltContainer.requireView(),
                            navController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun movesToDetailFragment_whenListItemIsClicked() = run {
        every { viewModel.recipes } returns mockResponse

        step("1. Launch fragment") {
            launchFragment {}
        }

        step("2. Get non-empty response from viewModel") {
            viewModel.viewModelScope.launch {
                mockMutableReponse.emit(list)
            }
        }

        step("3. Click on item") {
            RecipeListFragmentScreen.recycler.firstChild<RecipeListAdapterItem> {
                click()
            }
        }

        step("4. Check if navController was called with correct destination") {
            verify {
                navController.navigate(R.id.recipe_to_detail, any())
            }
            confirmVerified(navController)
        }
    }

    @Test
    fun recyclerIsNotEmpty_whenResponseIsNotEmpty() = run {
        every { viewModel.recipes } returns mockResponse

        val initialLoadSize = list.size

        step("1. Launch fragment") {
            launchFragment {}
        }

        step("2. Get non-empty response from viewModel") {
            viewModel.viewModelScope.launch {
                mockMutableReponse.emit(list)
            }
        }

        step("3. Check if recycler is not empty") {
            val recycler = RecipeListFragmentScreen.recycler

            recycler.hasSize(initialLoadSize)
        }
    }

    @Test
    fun recyclerIsEmpty_whenResponseIsEmpty() = run {
        every { viewModel.recipes } returns mockResponse

        step("1. Launch fragment") {
            launchFragment { }
        }

        step("2. Emit empty list in viewModel") {
            viewModel.viewModelScope.launch {
                mockMutableReponse.emit(emptyList())
            }
        }

        step("3. Check if recycler is empty") {
            val recycler = RecipeListFragmentScreen.recycler

            recycler.hasSize(0)
        }
    }
}