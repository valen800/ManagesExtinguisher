package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.Building.Building
import org.ieselcaminas.valentin.managesextinguisher.database.Building.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao


class BuildingFragmentViewModel(
    val databaseBuilding: BuildingDao,
    val databaseFloor: FloorDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    lateinit var buildingsList: List<Building>

    private var _navigateToBuildingCreator = MutableLiveData<Building>()
    var navigateToBuildingCreator: LiveData<Building> = _navigateToBuildingCreator

    fun doneNavigating() {
        _navigateToBuildingCreator.value = null
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

    fun onStartTracking() {
        uiScope.launch {
            var building = Building()
            building.nameBuildings = "Edificio1"
            building.buildingId = 11
            building.amountFloor = 2

            insertBuilding(building)

            /*var buildingList = getBuildingsFromDataBase()
            for (i in 0 until buildingList.size) {
                if (buildingList.get(i).nameBuildings == null) {
                    Log.i("edificios", buildingList.get(i).nameBuildings)
                } else {
                    Log.i("edificios", "null")
                }
            }*/
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