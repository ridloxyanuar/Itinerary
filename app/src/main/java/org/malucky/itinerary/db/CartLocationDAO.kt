package org.malucky.itinerary.db

import androidx.room.*
import io.reactivex.Single

@Dao
interface CartLocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: CartLocation)

    @Update
    fun update(cart: CartLocation)

    @Query("SELECT * from cartLocation WHERE locId = :key")
    fun get(key: Long): CartLocation?

    @Query("DELETE FROM cartLocation")
    fun clear()

    @Query("SELECT * FROM cartLocation ORDER BY locId DESC ")
    fun getLocation(): CartLocation?

    @Query("SELECT * FROM cartLocation")
    fun getAllLocation(): Single<List<CartLocation>>

    @Delete
    fun deleteID(cart: CartLocation)

    //Delete one item by id
    @Query("DELETE FROM cartLocation WHERE locId = :itemId")
    fun deleteByItemId(itemId: Long)
}