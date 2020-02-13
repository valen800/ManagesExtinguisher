package org.ieselcaminas.valentin.managesextinguisher.extinguisher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao

class ExtinguisherFragmentViewModel(private val databaseExtinguisher: ExtinguisherDao, application: Application): AndroidViewModel(application) {

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

    fun dialogExtinguisher(extinguisherId: Long) {

    }

    fun getExtinguishersFromDataBase(floorId: Long): LiveData<List<Extinguisher>> {
        val ExtinguishersList = databaseExtinguisher.getExintinguisherByID(floorId)
        return ExtinguishersList
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