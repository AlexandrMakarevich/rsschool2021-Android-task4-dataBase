package by.a_makarevich.cardatabaseapp.repository

import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.repository.room.CarDatabase
import kotlinx.coroutines.flow.Flow

class Repository (private val db: CarDatabase) {

    private val dao get() = db.carDao

    fun getAll(): Flow<List<Car>> = dao.getAll()
    suspend fun save (car: Car) = dao.add(car)
    suspend fun delete(car: Car) = dao.delete(car)
}