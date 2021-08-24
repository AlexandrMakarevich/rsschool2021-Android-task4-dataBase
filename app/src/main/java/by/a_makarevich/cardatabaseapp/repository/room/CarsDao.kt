package by.a_makarevich.cardatabaseapp.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars")
    fun getAll(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(car: Car)

    @Delete
    fun delete(car: Car)
}