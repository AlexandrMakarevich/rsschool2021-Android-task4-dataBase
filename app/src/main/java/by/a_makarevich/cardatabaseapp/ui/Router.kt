package by.a_makarevich.cardatabaseapp.ui

interface Router {
    fun openMainFragment()
    fun openAddFragment(id: Int, model: String, color: String, year: String)
    fun openPreferenceFragment()
}