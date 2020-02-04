package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao


class BuildingFragmentViewModel(
    val databaseBuilding: BuildingDao,
    val databaseFloor: FloorDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    lateinit var buildingsList: List<Building>

    private var _navigateToBuildingCreator = MutableLiveData<Boolean>()
    val navigateToBuildingCreator: LiveData<Boolean>
        get() = _navigateToBuildingCreator

    private var __navigateToFloors = MutableLiveData<Long>()
    val navigateToFloors: LiveData<Long>
        get() = __navigateToFloors

    fun startNavigatingToBuildingCreator() {
        _navigateToBuildingCreator.value = true
    }

    fun doneNavigating() {
        _navigateToBuildingCreator.value = false
    }

    init {
        initializeBuildings()
    }

    private fun initializeBuildings() {
        uiScope.launch {
            buildingsList = getBuildingsFromDataBase()
        }
    }

    private suspend fun getBuildingsFromDataBase(): List<Building> {
        return withContext(Dispatchers.IO) {
            var buildings = databaseBuilding.getAllBuilding()
            buildings
        }
    }

    fun onStartTracking(buildingName: String, amountFloors: Int) {
        uiScope.launch {
            var building = Building()
            building.nameBuildings = buildingName
            building.amountFloor = amountFloors

            insertBuilding(building)
        }
    }

    fun onStopTracking() {
        uiScope.launch {

        }
    }

    private suspend fun clearBuilding() {
        withContext(Dispatchers.IO) {
            databaseBuilding.clearBuilding()
        }
    }

    private suspend fun clearFloor() {
        withContext(Dispatchers.IO) {
            databaseFloor.clearFloor()
        }
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

    fun onBuildingClicked(id: Long) {
        __navigateToFloors.value = id
    }

    fun onBuildingDataFloorNavigated() {
        __navigateToFloors.value = null
    }

    /*private suspend fun getBuildingWithFloors(): LiveData<List<BuildingWithFloors>> {
        return withContext(Dispatchers.IO) {
            var buildingWithFloors = databaseFloor.getFloorsFromBuilding()
            buildingWithFloors
        }
    }*/

    fun onClear() {
        uiScope.launch {
            clearBuilding()
            clearFloor()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}