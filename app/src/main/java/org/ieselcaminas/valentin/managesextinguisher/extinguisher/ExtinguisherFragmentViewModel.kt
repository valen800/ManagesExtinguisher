package org.ieselcaminas.valentin.managesextinguisher.extinguisher

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import java.lang.IllegalStateException

class ExtinguisherFragmentViewModel(private val databaseExtinguisher: ExtinguisherDao, val activity: FragmentActivity?, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToExtinguisherCreator = MutableLiveData<Boolean>()
    val navigateToExtinguisherCreator: LiveData<Boolean>
        get() = _navigateToExtinguisherCreator

    fun startNavigatingToExtinguisherCreator() {
        _navigateToExtinguisherCreator.value = true
    }

    fun doneNavigatingToExtinguisherCreator() {
        _navigateToExtinguisherCreator.value = false
    }

    fun getExtinguishersFromDataBase(floorId: Long): LiveData<List<Extinguisher>> {
        val ExtinguishersList = databaseExtinguisher.getExintinguisherByFloorID(floorId)
        return ExtinguishersList
    }

    fun getExtinguisher(extinguisherId: Long): LiveData<Extinguisher> {
        val extinguisher = databaseExtinguisher.getExintinguisherByID(extinguisherId)
        return extinguisher

    }

    private suspend fun updateExtinguisher(extinguisher: Extinguisher) {
        withContext(Dispatchers.IO) {
            databaseExtinguisher.updateExt(extinguisher)
        }
    }

    private suspend fun deleteExtinguisher(extinguisher: Extinguisher) {
        withContext(Dispatchers.IO) {
            databaseExtinguisher.delete(extinguisher)
        }
    }

    private suspend fun insertExtinguisher(extinguisher: Extinguisher) {
        withContext(Dispatchers.IO) {
            databaseExtinguisher.insertExt(extinguisher)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}