package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao

class ExtinguisherCreatorFragmentViewModel(private val databaseExtinguisher: ExtinguisherDao, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToElements = MutableLiveData<Boolean>()
    val navigateToElements: LiveData<Boolean>
    get() = _navigateToElements

    fun startNavigatingToElements() {
        _navigateToElements.value = true
    }

    fun doneNavigatingToElements() {
        _navigateToElements.value = false
    }

    private suspend fun getExtinguishersFromDataBase(): LiveData<List<Extinguisher>> {
        return withContext(Dispatchers.IO) {
            var extinguishers = databaseExtinguisher.getAllExtinguisher()
            extinguishers
        }
    }

    fun onStartTracking(FloorName: String, FloorNumber: Long, buildingID: Long) {
        uiScope.launch {
            /*var floor = Extinguisher()
            insertExtinguisher(floor)*/
        }
    }

    private suspend fun updateExtinguisher(extinguisher: Extinguisher) {
        withContext(Dispatchers.IO) {
            databaseExtinguisher.updateExt(extinguisher)
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