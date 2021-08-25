package by.a_makarevich.cardatabaseapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.cardatabaseapp.locateLazy
import by.a_makarevich.cardatabaseapp.repository.Repository
import by.a_makarevich.cardatabaseapp.repository.room.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repository: Repository by locateLazy()

    val cars = repository.getAll().asLiveDataFlow()

    fun save(car: Car) {
        viewModelScope.launch { repository.save(createCar(car)) }
    }

    fun delete(car: Car) {
        viewModelScope.launch { repository.delete(car) }
    }

    private fun createCar(car: Car) = Car(
        model = car.model,
        year = car.year
    )

    fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}