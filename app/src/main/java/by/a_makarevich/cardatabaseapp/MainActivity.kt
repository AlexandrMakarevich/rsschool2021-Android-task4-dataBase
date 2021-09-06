package by.a_makarevich.cardatabaseapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.a_makarevich.cardatabaseapp.repository.room.Car
import by.a_makarevich.cardatabaseapp.ui.Router
import by.a_makarevich.cardatabaseapp.ui.add.AddFragment
import by.a_makarevich.cardatabaseapp.ui.main.CarClickedListener
import by.a_makarevich.cardatabaseapp.ui.main.MainFragment
import by.a_makarevich.cardatabaseapp.ui.pref.PreferenceFragment

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
        runningFragment = RunningFragment.MainFragment
        title = "CARS"
        invalidateOptionsMenu()
        openFragment(MainFragment(this))
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
        title = if (model == "")
            "ADD NEW CAR"
        else "UPDATE CAR"
        invalidateOptionsMenu()
    }

    override fun openPreferenceFragment() {
        runningFragment = RunningFragment.PreferenceFragment
        title = "OPTIONS"
        invalidateOptionsMenu()
        openFragment(PreferenceFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun onBackPressed() {
        if (runningFragment == RunningFragment.AddFragment || runningFragment == RunningFragment.PreferenceFragment) openMainFragment()
        else super.onBackPressed()
    }

    override fun onCarClicked(car: Car) {
        Log.d("MyLog", "${car.color}")
        openAddFragment(car.id, car.model, car.color, car.year)
    }

    companion object {
        const val ID = "id"
        const val MODEL = "model"
        const val COLOR = "color"
        const val YEAR = "year"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (runningFragment) {
            RunningFragment.MainFragment -> menu?.findItem(R.id.menu_filter)?.isVisible = true
            else -> menu?.findItem(R.id.menu_filter)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> openPreferenceFragment()
        }
        return super.onOptionsItemSelected(item)
    }
}

sealed class RunningFragment {
    object MainFragment : RunningFragment()
    object AddFragment : RunningFragment()
    object PreferenceFragment : RunningFragment()
}

