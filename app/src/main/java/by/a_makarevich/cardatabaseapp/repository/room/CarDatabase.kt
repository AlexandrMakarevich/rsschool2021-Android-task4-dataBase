package by.a_makarevich.cardatabaseapp.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase: RoomDatabase(){
    abstract val carDao: CarsDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context,
            CarDatabase::class.java,
            "cars-database"
        )
    }

}