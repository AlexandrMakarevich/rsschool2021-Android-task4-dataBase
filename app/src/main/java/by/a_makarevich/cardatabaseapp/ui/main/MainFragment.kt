package by.a_makarevich.cardatabaseapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.a_makarevich.cardatabaseapp.databinding.MainFragmentBinding
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.ui.Router
import by.a_makarevich.cardatabaseapp.ui.main.adapter.CarListAdapter
import by.a_makarevich.cardatabaseapp.ui.main.adapter.CarViewHolder
import by.a_makarevich.cardatabaseapp.ui.main.adapter.SwipeHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment(private val listener: CarClickedListener) : Fragment(),
    CarViewHolder.CarViewListener {

    /* companion object {
         fun newInstance() = MainFragment()
     }*/

    private var cars = listOf<Car>()

    private val parentRouter: Router? get() = (activity as? Router)

    private val viewModel: MainViewModel by viewModels()

    private var binding: MainFragmentBinding? = null

    private val adapter: CarListAdapter? get() = binding?.carList?.adapter as? CarListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            carList.adapter = CarListAdapter(this@MainFragment)
            SwipeHelper(viewModel::delete).attachToRecyclerView(carList)

            addFab.setOnClickListener {
                parentRouter?.openAddFragment(-1, "", "", "")
            }
        }

        viewModel.cars.onEach(::renderCars).launchIn(lifecycleScope)
    }

    private fun renderCars(cars: List<Car>) {
        adapter?.submitList(cars)
        this.cars = cars
    }

    override fun onDestroy() {
        binding = null
        cars = emptyList()
        super.onDestroy()
    }

    override fun onCarClicked(_id: Int) {
        this.cars.forEach { Log.d("MyLog", it.model) }
        val car = this.cars.findLast { it.id == _id }
        if (car != null) {
            listener.onCarClicked(car)
        } else return

    }
}

interface CarClickedListener {
    fun onCarClicked(car: Car)
}



