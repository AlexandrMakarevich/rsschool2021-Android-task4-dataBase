package by.a_makarevich.cardatabaseapp.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val model: String,
    val year: String,
    val color: String
)

