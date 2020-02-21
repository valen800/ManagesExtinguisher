package org.ieselcaminas.valentin.managesextinguisher.floors

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor

class FloorsFragmentViewModel(
    private val databaseFloor: FloorDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToFloorDialog = MutableLiveData<Boolean>()
    val navigateToFloorDialog: LiveData<Boolean>
        get() = _navigateToFloorDialog

    private var __navigateToFragmentElements = MutableLiveData<Long>()
    val navigateToFragmentElements: LiveData<Long>
        get() = __navigateToFragmentElements

    private var _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    fun startRefresh() {
        _refresh.value = true
    }

    fun doneRefresh() {
        _refresh.value = false
    }

    fun startNavigatingToFloorDialog() {
        _navigateToFloorDialog.value = true
    }

    fun doneNavigatingToFloorDialog() {
        _navigateToFloorDialog.value = false
    }

    fun startNavigatingToFragmentElements(FloorId: Long) {
        __navigateToFragmentElements.value = FloorId
    }

    fun doneNavigatingToFragmentElements() {
        __navigateToFragmentElements.value = null
    }

    fun getFloorsFromDataBase(buildingId: Long): LiveData<List<Floor>> {
            val FloorsList = databaseFloor.getFloorByBuildingId(buildingId)
            return FloorsList
    }

    fun insertFloor(floor: Floor) {
        uiScope.launch {
            insertFloorCou(floor)
        }
    }

    private suspend fun insertFloorCou(floor: Floor) {
        withContext(Dispatchers.IO) {
            databaseFloor.insertFloor(floor)
        }
    }

    fun deleteFloor(floorId: Long) {
        uiScope.launch {
            deleteFloorCou(floorId)
        }
    }

    private suspend fun deleteFloorCou(floorId: Long) {
        withContext(Dispatchers.IO) {
            databaseFloor.deleteById(floorId)
        }
    }

    fun updateFloor(floor: Floor) {
        uiScope.launch {
            updateFloorCou(floor)
        }
    }

    private suspend fun updateFloorCou(floor: Floor) {
        withContext(Dispatchers.IO) {
            databaseFloor.updateFloor(floor)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}