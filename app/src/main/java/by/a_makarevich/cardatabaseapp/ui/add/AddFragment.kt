package by.a_makarevich.cardatabaseapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import by.a_makarevich.cardatabaseapp.MainActivity.Companion.COLOR
import by.a_makarevich.cardatabaseapp.MainActivity.Companion.ID
import by.a_makarevich.cardatabaseapp.MainActivity.Companion.MODEL
import by.a_makarevich.cardatabaseapp.MainActivity.Companion.YEAR
import by.a_makarevich.cardatabaseapp.R
import by.a_makarevich.cardatabaseapp.databinding.AddFragmentBinding
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.ui.Router
import by.a_makarevich.cardatabaseapp.ui.main.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AddFragment : Fragment() {

    private val parentRouter: Router? get() = (activity as? Router)

    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun newInstance() = AddFragment()
    }

    private var binding: AddFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = AddFragmentBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getString(MODEL) == "") {
            binding?.buttonSave?.text = "ADD"
            binding?.buttonSave?.setOnClickListener { saveCar() }
        } else {
            binding?.apply {
                buttonSave.text = "UPDATE"
                modelEditText.setText(arguments?.getString(MODEL, ""))
                colorEditText.setText(arguments?.getString(COLOR, ""))
                yearEditText.setText(arguments?.getString(YEAR, ""))
                buttonSave.setOnClickListener {
                    updateCar(
                        Car(
                            id = arguments?.getInt(ID)!!,
                            model = modelEditText.text.toString().takeIf { it.isNotBlank() }
                                .toString(),
                            color = colorEditText.text.toString().takeIf { it.isNotBlank() }
                                .toString(),
                            year = yearEditText.text.toString().takeIf { it.isNotBlank() }
                                .toString()
                        )
                    )
                }
            }
        }
    }

    private fun saveCar() {
        binding?.apply {
            val carModel = modelEditText.text.toString().takeIf { it.isNotBlank() } ?: return@apply
            val carColor = colorEditText.text.toString().takeIf { it.isNotBlank() } ?: return@apply
            val carYear = yearEditText.text.toString().takeIf { it.isNotBlank() } ?: return@apply
            val car = Car(model = carModel, color = carColor, year = carYear)


            when (PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getString(resources.getString(R.string.data_source), "")) {

                resources.getStringArray(R.array.data_source)[0] -> {         //ROOM
                    viewModel.save(car)
                    parentRouter?.openMainFragment()
                }

                resources.getStringArray(R.array.data_source)[1] -> {         //SQLite
                    viewModel.saveCarCursor(car)
                    parentRouter?.openMainFragment()
                }
            }
        }
    }

    private fun updateCar(car: Car) {

        when (PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString(resources.getString(R.string.data_source), "")) {

            resources.getStringArray(R.array.data_source)[0] -> {         //ROOM
                viewModel.update(car)
                parentRouter?.openMainFragment()
            }
            resources.getStringArray(R.array.data_source)[1] -> {         //SQLite
                viewModel.updateCarCursor(car)
                parentRouter?.openMainFragment()
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}


