package com.example.wishlist.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private var wishDAO: WishDAO = Graph.wishDAO
): ViewModel() {

    //TODO: a parte de repository nao vai ser feita. As chamadas são direto no VM

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newString: String){
        wishTitleState = newString
    }

    fun onWishDescriptionChange(newString: String){
        wishDescriptionState = newString
    }


    lateinit var getWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getWishes = wishDAO.getWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishDAO.addWish(wish)
        }
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishDAO.updateWish(wish)
        }
    }

    fun getWish(id: Long): Flow<Wish>{
        return wishDAO.getWishByID(id)
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishDAO.delete(wish)
        }
    }


}
