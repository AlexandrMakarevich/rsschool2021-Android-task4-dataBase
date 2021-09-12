package by.a_makarevich.cardatabaseapp.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.cardatabaseapp.locateLazy
import by.a_makarevich.cardatabaseapp.repository.Repository
import by.a_makarevich.cardatabaseapp.repository.cursor.CarSQLiteOpenHelper
import by.a_makarevich.cardatabaseapp.repository.room.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    private val repository: Repository by locateLazy()

    val cars = repository.getAll().asLiveDataFlow()

    /* fun getcarsCursor(context: Context): List<Car> {
         var cars = listOf<Car>()
         viewModelScope.launch {
             repository.getCarsCursor(context).collect {
                 cars = it
             }
         }
         return cars
     }*/


    fun save(car: Car) {
        viewModelScope.launch { repository.save(createCar(car)) }
    }

    fun delete(car: Car) {
        viewModelScope.launch { repository.delete(car) }
    }

    fun update(car: Car) {
        viewModelScope.launch { repository.update(car) }
    }

    suspend fun getCar(id: Int): Car {
        return repository.getCar(id)
    }

    private fun createCar(car: Car) = Car(
        model = car.model,
        year = car.year,
        color = car.color
    )

    fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    //========================================= Cursor Below

    fun getCarsCursor(context: Context): Flow<List<Car>> = repository.getCarsCursor(context)

    fun saveCarCursor(car: Car) {
        viewModelScope.launch { repository.addCarCursor(car) }
    }

    fun updateCarCursor(car: Car) {
        viewModelScope.launch { repository.updateCarCursor(car) }
    }

    fun deleteCarCursor(car: Car) {
        viewModelScope.launch { repository.deleteCarCursor(car) }
    }

}


