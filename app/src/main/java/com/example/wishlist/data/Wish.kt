package com.example.wishlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title : String = "",
    @ColumnInfo(name = "wish-desc")
    val description : String = ""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Teste 1", description = "Descrição 1"),
        Wish(title = "Teste 2", description = "Descrição 2"),
        Wish(title = "Teste 3", description = "Descrição 3")
    )
}


