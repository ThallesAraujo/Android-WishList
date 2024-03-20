package com.example.wishlist.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Card
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.material.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist.ui.components.AppBarView
import com.example.wishlist.ui.navigation.Screen
import com.example.wishlist.data.WishViewModel
import com.example.wishlist.data.DummyWish
import com.example.wishlist.data.Wish
import com.example.wishlist.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current
    Scaffold (
        topBar = { AppBarView(title = stringResource(id = R.string.app_name), isHomeScreen = true)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                },
                backgroundColor = Color.Black) {
                Icon(imageVector = Icons.Default.Add, tint = Color.White, contentDescription = null)
            }
        }
    ){

        val wishlist = viewModel.getWishes.collectAsState(initial = listOf())
        val listState = rememberLazyListState()

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            state = listState){
            items(wishlist.value, key = {wish -> wish.id}){
                    wish ->

                val dismissState = rememberDismissState(confirmStateChange = {
                    if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                        viewModel.deleteWish(wish)
                    }
                    true
                })


                SwipeToDismiss(state = dismissState, background = {
                    val color by animateColorAsState(
                        if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent, label = ""
                    )
                    val alignment = Alignment.CenterEnd
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 20.dp), contentAlignment = alignment){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
                    }
                }, directions = setOf(DismissDirection.EndToStart), dismissThresholds = {FractionalThreshold(0.25f)}, dismissContent = {
                    WishItem(wish = wish) {
                        val id = wish.id
                        navController.navigate(Screen.AddScreen.route+"/$id")
                    }
                } )


            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable { onClick() },
        elevation = 10.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(wish.title, fontWeight = FontWeight.ExtraBold)
            Text(wish.description)
        }
    }
}