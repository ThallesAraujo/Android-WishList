package com.example.wishlist.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlist.ui.components.AppBarView
import com.example.wishlist.R
import com.example.wishlist.data.Wish
import com.example.wishlist.data.WishViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditDetailView(id: Long, viewModel: WishViewModel, navController: NavController){

    val snackMessage = remember{
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    if (id != 0L){
        val wish = viewModel.getWish(id).collectAsState(initial = Wish(0L, title = "", description = ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = { AppBarView(title = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(id = R.string.add_wish), onBackNavClick = {
            navController.navigateUp()
        } )
        }, scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChanged = {
                viewModel.onWishTitleChange(it)
            })
            Spacer(modifier = Modifier.height(8.dp))
            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChange(it)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                    if (id != 0L){
                        viewModel.updateWish(Wish(id, viewModel.wishTitleState.trim(), viewModel.wishDescriptionState.trim()))
                    }else{
                        viewModel.addWish(Wish(title = viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim()))
                        snackMessage.value = context.getString(R.string.wish_created_message)
                    }
                }else{
                    snackMessage.value = context.getString(R.string.empty_wish_fields_error)
                }
                scope.launch {
                    //pode-se remover a snackbar para tornar o app mais rapido
                    //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    //voltar uma tela
                    navController.navigateUp()
                }
            }) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(
                        id = R.string.add_wish
                    ), style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
fun WishTextField(label: String, value: String, onValueChanged: (String) -> Unit){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth()
//        //Opções de teclado
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//        //Customizar textfield
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            // using predefined Color
//            textColor = Color.Black,
//            // using our own colors in Res.Values.Color
//            focusedBorderColor = colorResource(id = R.color.black),
//            unfocusedBorderColor = colorResource(id = R.color.black),
//            cursorColor = colorResource(id = R.color.black),
//            focusedLabelColor = colorResource(id = R.color.black),
//            unfocusedLabelColor = colorResource(id = R.color.black),
//        )
    )
}