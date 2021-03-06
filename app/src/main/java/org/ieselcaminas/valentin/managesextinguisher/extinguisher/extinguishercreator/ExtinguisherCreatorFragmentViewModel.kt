package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator

import android.app.Application
import android.util.Log
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

    fun insertExtinguisher(
        extinguisherFloorId: Long, nExtinguisher: String,
        situation: String, tradeMark: String, model: String,
        descriptionLocation: String, weight: Int,
        factory_date: Long, dateLastRevision: Long, dateNextRevision: Long) {


        uiScope.launch {
            var extinguisher = Extinguisher()
            extinguisher.extinguisherFloorId = extinguisherFloorId
            extinguisher.nExtinguisher = nExtinguisher
            extinguisher.situation = situation
            extinguisher.trademark = tradeMark
            extinguisher.model = model
            extinguisher.descriptionLocation = descriptionLocation
            extinguisher.weight = weight
            extinguisher.factoryDate = factory_date
            extinguisher.dateLastRevision = dateLastRevision
            extinguisher.dateNextRevision = dateNextRevision

            insertExtinguisher(extinguisher)
        }
    }

    fun updateExt(
        extinguisherFloorId: Long, nExtinguisher: String,
        situation: String, tradeMark: String, model: String,
        descriptionLocation: String, weight: Int,
        factory_date: Long, dateLastRevision: Long, dateNextRevision: Long) {

        Log.i("nExtinguisher", nExtinguisher)

        uiScope.launch {
            var extinguisher = Extinguisher()
            extinguisher.extinguisherFloorId = extinguisherFloorId
            extinguisher.nExtinguisher = nExtinguisher
            extinguisher.situation = situation
            extinguisher.trademark = tradeMark
            extinguisher.model = model
            extinguisher.descriptionLocation = descriptionLocation
            extinguisher.weight = weight
            extinguisher.factoryDate = factory_date
            extinguisher.dateLastRevision = dateLastRevision
            extinguisher.dateNextRevision = dateNextRevision

            updateExtinguisherCou(extinguisher)
        }
    }

    private suspend fun updateExtinguisherCou(extinguisher: Extinguisher) {
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