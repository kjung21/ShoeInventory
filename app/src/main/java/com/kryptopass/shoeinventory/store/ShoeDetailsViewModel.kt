package com.kryptopass.shoeinventory.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryptopass.shoeinventory.models.Shoe

class ShoeDetailsViewModel : ViewModel() {

    val shoe = MutableLiveData(Shoe("", 0.0, "", ""))

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    private val _cancelEvent = MutableLiveData<Boolean>()
    val cancelEvent: LiveData<Boolean>
        get() = _cancelEvent

    private val _validationFailedEvent = MutableLiveData<Boolean>()
    val validationFailedEvent: LiveData<Boolean>
        get() = _validationFailedEvent

    fun onSave() {
        val shoe = shoe.value ?: return onValidationFailed()

        if (validShoeDetails(shoe)) {
            _saveEvent.value = true
        } else {
            onValidationFailed()
        }
    }

    fun onValidationFailedEventCompleted() {
        _validationFailedEvent.value = false
    }

    fun onSaveEventCompleted() {
        _saveEvent.value = false
    }

    fun onCancel() {
        _cancelEvent.value = true
    }

    fun onCancelEventCompleted() {
        _cancelEvent.value = false
    }

    private fun onValidationFailed() {
        _validationFailedEvent.value = true
    }

    private fun validShoeDetails(shoe: Shoe) : Boolean {
        return shoe.name.isNotEmpty() &&
                shoe.company.isNotEmpty() &&
                shoe.size.toString().isNotEmpty() &&
                shoe.company.isNotEmpty()
    }
}