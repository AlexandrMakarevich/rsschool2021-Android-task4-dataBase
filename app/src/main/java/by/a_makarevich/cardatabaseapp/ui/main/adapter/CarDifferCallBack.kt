package by.a_makarevich.cardatabaseapp.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import by.a_makarevich.cardatabaseapp.repository.room.Car

class CarDifferCallBack : DiffUtil.ItemCallback<Car>() {
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.color == newItem.color
                && oldItem.model == newItem.model && oldItem.year == newItem.year
    }
}