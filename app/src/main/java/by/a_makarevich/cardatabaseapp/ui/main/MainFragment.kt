package by.a_makarevich.cardatabaseapp.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.cardatabaseapp.R
import by.a_makarevich.cardatabaseapp.databinding.MainFragmentBinding
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.ui.Router
import by.a_makarevich.cardatabaseapp.ui.main.adapter.CarListAdapter
import by.a_makarevich.cardatabaseapp.ui.main.adapter.CarViewHolder
import by.a_makarevich.cardatabaseapp.ui.main.adapter.SwipeHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.ClassCastException

class MainFragment : Fragment(),
    CarViewHolder.CarViewListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var listener: CarClickedListener? = null

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

            deleteSwiper(carList)

            addFab.setOnClickListener {
                parentRouter?.openAddFragment(-1, "", "", "")
            }
        }

        when (getDefaultSharedPreferences(requireContext())
            .getString(resources.getString(R.string.data_source), "")) {

            resources.getStringArray(R.array.data_source)[0] -> {         //ROOM
                viewModel.cars.onEach(::renderCars).launchIn(lifecycleScope)
            }
            resources.getStringArray(R.array.data_source)[1] -> {         //SQLite
                viewModel.getCarsCursor(requireContext()).onEach(::renderCars)
                    .launchIn(lifecycleScope)
            }
        }

    }


    private fun renderCars(cars: List<Car>) {
        val pref = getDefaultSharedPreferences(context)

        if (pref.contains(resources.getString(R.string.sortList))) {
            val sort = pref.getString(resources.getString(R.string.sortList), "anything wrong")
            when (sort) {
                resources.getStringArray(R.array.sort_by)[0] -> {
                    adapter?.submitList(cars.sortedBy { it.model })
                    this.cars = cars
                }
                resources.getStringArray(R.array.sort_by)[1] -> {
                    adapter?.submitList(cars.sortedBy { it.color })
                    this.cars = cars
                }
                resources.getStringArray(R.array.sort_by)[2] -> {
                    adapter?.submitList(cars.sortedBy { it.year })
                    this.cars = cars
                }
            }
        } else {
            adapter?.submitList(cars.sortedBy { it.model })
            this.cars = cars
            Log.d("MyLog", "pref has not the sort_list ")
            return
        }
        // Log.d("MyLog", pref.getString(resources.getString(R.string.data_source), "data_source is empty").toString())
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
            listener?.onCarClicked(car)
        } else return
    }


    private fun deleteSwiper(carList: RecyclerView) {


        when (getDefaultSharedPreferences(requireContext())
            .getString(resources.getString(R.string.data_source), "")) {

            resources.getStringArray(R.array.data_source)[0] -> {         //ROOM
                SwipeHelper(viewModel::delete).attachToRecyclerView(carList)
            }
            resources.getStringArray(R.array.data_source)[1] -> {         //SQLite
                SwipeHelper(viewModel::deleteCarCursor).attachToRecyclerView(carList)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)



        if (activity is CarClickedListener) {
            listener = activity as CarClickedListener
        } else {
             ClassCastException(
                activity.toString()
                        + " must implemenet MainFragment.CarClickedListener"
            )
        }
    }
}

interface CarClickedListener {
    fun onCarClicked(car: Car)
}
