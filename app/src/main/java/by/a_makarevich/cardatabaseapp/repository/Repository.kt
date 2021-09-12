package by.a_makarevich.cardatabaseapp.repository

import android.content.Context
import by.a_makarevich.cardatabaseapp.repository.cursor.CarSQLiteOpenHelper
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.repository.room.CarDatabase
import kotlinx.coroutines.flow.Flow

class Repository (private val db: CarDatabase) {

    private val dao get() = db.carDao

    fun getAll(): Flow<List<Car>> = dao.getAll()
    suspend fun save (car: Car) = dao.add(car)
    suspend fun delete(car: Car) = dao.delete(car)
    suspend fun update(car: Car) = dao.update(car)
    suspend fun getCar(id: Int) = dao.getCar(id)

    //==========================CursorBelow

    fun getCarsCursor(context: Context): Flow<List<Car>> {
         val carsSqlOpenHelper = CarSQLiteOpenHelper(context)
         return carsSqlOpenHelper.listOfCar
    }





}

