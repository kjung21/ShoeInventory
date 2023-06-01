package com.kryptopass.shoeinventory.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryptopass.shoeinventory.models.Shoe

class ShoeListViewModel : ViewModel() {

    private val _shoes = MutableLiveData<MutableList<Shoe>>()
    val shoes: LiveData<MutableList<Shoe>>
        get() = _shoes

    private val _addShoeEvent = MutableLiveData<Boolean>()
    val addShoeEvent: LiveData<Boolean>
        get() = _addShoeEvent

    init {
        resetShoes()
    }

    fun onAdd() {
        _addShoeEvent.value = true
    }

    fun addShoe(shoe: Shoe) {
        val list = _shoes.value
        list?.let { shoes: MutableList<Shoe> ->
            shoes.add(shoe)
            _shoes.value = shoes
        }
    }

    fun addShoeEventCompleted() {
        _addShoeEvent.value = false
    }

    private fun resetShoes() {
        _shoes.value = mutableListOf(
            Shoe("Off-White Dunk Low Pine Green", 8.5, "Nike", "Nike and designer Virgil Abloh collaboration"),
            Shoe("Ultraboost 23", 9.0, "Adidas", "lightest, springiest model thus far"),
            Shoe("UA HOVR™ Mega 3 Clone", 9.5, "Under Armour", "most cushioned running shoe"),
            Shoe("GEL-NIMBUS 25", 10.0, "Asics", "softer and smoother running experience"),
        )
    }
}