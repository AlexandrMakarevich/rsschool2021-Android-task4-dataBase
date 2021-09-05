package by.a_makarevich.cardatabaseapp.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import by.a_makarevich.cardatabaseapp.repository.room.Car

class CarListAdapter (private val listener: CarViewHolder.CarViewListener) : ListAdapter<Car, CarViewHolder>(CarDifferCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder.create(parent, listener)

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }



}