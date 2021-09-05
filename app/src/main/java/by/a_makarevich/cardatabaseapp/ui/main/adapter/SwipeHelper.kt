package by.a_makarevich.cardatabaseapp.ui.main.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import by.a_makarevich.cardatabaseapp.repository.room.Car

class SwipeHelper(onSwiped: (Car) -> Unit): ItemTouchHelper(SwipeCallBack(onSwiped))