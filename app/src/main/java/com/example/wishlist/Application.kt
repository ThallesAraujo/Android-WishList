package com.example.wishlist

import android.app.Application
import com.example.wishlist.data.Graph

//Equivalente ao AppDelegate do iOS
class WishListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}