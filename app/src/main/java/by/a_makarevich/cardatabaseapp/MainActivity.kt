package by.a_makarevich.cardatabaseapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.ui.Router
import by.a_makarevich.cardatabaseapp.ui.add.AddFragment
import by.a_makarevich.cardatabaseapp.ui.main.CarClickedListener
import by.a_makarevich.cardatabaseapp.ui.main.MainFragment
import by.a_makarevich.cardatabaseapp.ui.main.adapter.CarViewHolder

class MainActivity : AppCompatActivity(), Router, CarClickedListener {

    private var runningFragment: RunningFragment = RunningFragment.MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        title = "CARS"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment(this))
                .commitNow()
        }
    }

    override fun openMainFragment() {
        openFragment(MainFragment(this))
        runningFragment = RunningFragment.MainFragment
        title = "CARS"
    }

    override fun openAddFragment(id: Int, model: String, color: String, year: String) {
        val args = Bundle()
        args.putInt(ID, id)
        args.putString(MODEL, model)
        args.putString(COLOR, color)
        args.putString(YEAR, year)
        val fragment = AddFragment.newInstance()
        fragment.arguments = args
        openFragment(fragment)
        runningFragment = RunningFragment.AddFragment
        title = "ADD NEW CAR"
    }

    override fun openPreferenceFragment() {
        TODO("Not yet implemented")
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun onBackPressed() {
        if (runningFragment == RunningFragment.AddFragment) openMainFragment()
        else super.onBackPressed()

    }

    override fun onCarClicked(car: Car) {
        Log.d("MyLog",  "${car.color}")
        openAddFragment(car.id, car.model, car.color, car.year)
    }
companion object{
    const val ID = "id"
    const val MODEL = "model"
    const val COLOR = "color"
    const val YEAR = "year"
}

}

sealed class RunningFragment {
    object MainFragment : RunningFragment()
    object AddFragment : RunningFragment()
    object PreferenceFragment : RunningFragment()
}

