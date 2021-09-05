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

    /*  private val _runningFragment = MutableLiveData<RunningFragment>(RunningFragment.MainFragment)
      val runningFragment: LiveData<RunningFragment> = _runningFragment

      fun setRunningFragment(fragment: RunningFragment){
          _runningFragment.value = fragment
      }*/

    private val repository: Repository by locateLazy()

    val cars = repository.getAll().asLiveDataFlow()

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


}


