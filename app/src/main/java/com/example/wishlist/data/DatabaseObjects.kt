package com.example.wishlist.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
//DAO
@Dao
abstract class WishDAO{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity: Wish)

    @Query("Select * from `wish-table`")
    abstract fun getWishes(): Flow<List<Wish>>

    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    @Delete
    abstract suspend fun delete(wishEntity: Wish)

    @Query("Select * from `wish-table` where id=:id")
    abstract fun getWishByID(id: Long): Flow<Wish>
}

//Database
@Database(
    entities = [Wish::class],
    version = 1,
    exportSchema = false
)
abstract class WishDatabase: RoomDatabase(){
    abstract fun wishDao(): WishDAO
}

//Database Graph
// object = singleton
object Graph{
    lateinit var database: WishDatabase

    val wishDAO by lazy {
        database.wishDao()
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist-db").build()
    }

}