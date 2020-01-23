package org.ieselcaminas.valentin.managesextinguisher.Buildings

import android.app.Application
import android.app.job.JobInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.Building.Building
import org.ieselcaminas.valentin.managesextinguisher.database.Building.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.database.Relations.BuildingWithFloors


class BuildingFragmentViewModel(
    val databaseBuilding: BuildingDao,
    val databaseFloor: FloorDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var buildingsList = databaseBuilding.getAllBuilding()

    init {

    }

    private fun initialize() {
        uiScope.launch {

        }
    }

    /*private suspend fun getBuildingsFromDataBase(): Building? {
        return withContext(Dispatchers.IO) {

        }
    }*/

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

    private suspend fun getBuildingWithFloors(): LiveData<List<BuildingWithFloors>> {
        return withContext(Dispatchers.IO) {
            var buildingWithFloors = databaseFloor.getFloorsFromBuilding()
            buildingWithFloors
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            var building1: Building = Building(0, "Edificio1", 0)
            insertBuilding(building1)

        }
    }

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