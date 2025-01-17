package com.example.wishlist.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.wishlist.R

@Composable
fun AppBarView(
    isHomeScreen: Boolean = false,
    title: String,
    onBackNavClick: () -> Unit = {}
) {

    val navigationIcon : (@Composable () -> Unit)? = 
        if (!isHomeScreen){
            {
                IconButton(onClick = onBackNavClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }else{
            null
        }

    TopAppBar(
        title = {
            Text(title, color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 4.dp)
                .heightIn(max = 24.dp))
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
    )
}