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
    private val databaseBuilding: BuildingDao,
    private val databaseFloor: FloorDao,
    private val databaseExtinguisher: ExtinguisherDao,
    private val databaseFlask: FlaskDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToFloorCreator = MutableLiveData<Boolean>()
    val navigateToFloorCreator: LiveData<Boolean>
        get() = _navigateToFloorCreator

    private var __navigateToFragmentElements = MutableLiveData<Long>()
    val navigateToFragmentElements: LiveData<Long>
        get() = __navigateToFragmentElements

    fun startNavigatingToFloorCreator() {
        _navigateToFloorCreator.value = true
    }

    fun doneNavigatingToFloorCreator() {
        _navigateToFloorCreator.value = false
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

    private suspend fun updateBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.updateBuilding(building)
        }
    }

    private suspend fun updateFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            databaseFloor.updateFloor(floor)
        }
    }

    private suspend fun insertBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.insertBuilding(building)
        }
    }

    private suspend fun insertFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            databaseFloor.insertFloor(floor)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}