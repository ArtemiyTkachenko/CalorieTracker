package com.artkachenko.recipe_list

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.artkachenko.recipe_list.recipe_list.RecipeListFragment
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher

object RecipeListFragmentScreen : KScreen<RecipeListFragmentScreen>() {

    override val layoutId: Int = R.layout.fragment_recipe_list

    override val viewClass: Class<*> = RecipeListFragment::class.java

    val recycler = KRecyclerView(builder = { withId(R.id.recycler) },
        itemTypeBuilder = { itemType(::RecipeListAdapterItem) })
}

class RecipeListAdapterItem(parent: Matcher<View>) : KRecyclerItem<RecipeListAdapterItem>(parent)