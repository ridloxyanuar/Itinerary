package org.malucky.itinerary.db

import androidx.lifecycle.LiveData
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
}