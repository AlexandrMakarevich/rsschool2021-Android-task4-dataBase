package by.a_makarevich.cardatabaseapp.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars")
    fun getAll(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Update
    suspend fun update(car: Car)

    @Query("SELECT * FROM cars WHERE id = :idCar")
    suspend fun getCar(idCar: Int): Car
}