package by.a_makarevich.cardatabaseapp.repository.cursor

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.a_makarevich.cardatabaseapp.repository.room.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    "cars",
    null,
    1
) {


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    private fun getCursorWithCars(): Cursor {
        return writableDatabase.rawQuery("SELECT * FROM cars", null)
    }

    @SuppressLint("Range")
    val listOfCar: Flow<List<Car>> = flow {
        getCursorWithCars().use { cursor ->
            if (cursor.moveToFirst()) {
                val cars = mutableListOf<Car>()
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val model = cursor.getString(cursor.getColumnIndex("model"))
                    val color = cursor.getString(cursor.getColumnIndex("color"))
                    val year = cursor.getString(cursor.getColumnIndex("year"))
                    val car = Car(id, "SQlite - $model", year, color)
                    cars.add(car)
                    emit(cars)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
    }

    suspend fun addCar(car: Car) {
        val newCar = ContentValues()
        newCar.put("model", car.model)
        newCar.put("color", car.color)
        newCar.put("year", car.year)
        val id = this.readableDatabase.insert("cars", null, newCar)
        Log.d("MyLog", "$id")
    }

    suspend fun updateCar(car: Car) {
        val newCar = ContentValues()
        newCar.put("model", car.model)
        newCar.put("color", car.color)
        newCar.put("year", car.year)
        val where = "id=${car.id}"
        this.readableDatabase.update("cars", newCar, where, null)
    }

    suspend fun deleteCar(car: Car) {
        val where = "id=${car.id}"
        this.readableDatabase.delete("cars", where, null)
    }

}



