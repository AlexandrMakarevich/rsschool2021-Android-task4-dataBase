package by.a_makarevich.cardatabaseapp.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.cardatabaseapp.databinding.CarListItemBinding
import by.a_makarevich.cardatabaseapp.repository.room.Car

class CarViewHolder(private val binding: CarListItemBinding, private val listener: CarViewListener) :
    RecyclerView.ViewHolder(binding.root) {

    var item: Car? = null
    private set     // приватный сеттер, чтобы изменять item мог только CarViewHolder

    fun onBind(item: Car) {

        this.item = item
        binding.apply {
            modelTextView.text = item.model
            colorTextView.text = item.color
            yearTextView.text = item.year
        }
        itemView.setOnClickListener {
            Log.d("MyLog", "cars id =  ${item.id}")
            listener.onCarClicked(item.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: CarViewListener) = CarListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {CarViewHolder(it, listener)}
    }

    interface CarViewListener{
        fun onCarClicked(id: Int)
    }

}