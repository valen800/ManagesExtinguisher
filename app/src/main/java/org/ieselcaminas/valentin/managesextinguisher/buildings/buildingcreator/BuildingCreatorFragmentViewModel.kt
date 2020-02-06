package org.ieselcaminas.valentin.managesextinguisher.buildings.buildingcreator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao

class BuildingCreatorFragmentViewModel(
    val databaseBuilding: BuildingDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToBuilding = MutableLiveData<Boolean>()
    val navigateToBuilding: LiveData<Boolean>
        get() = _navigateToBuilding

    fun startNavigatingToBuilding() {
        _navigateToBuilding.value = true
    }

    fun doneNavigating() {
        _navigateToBuilding.value = false
    }

    private suspend fun getBuildingsFromDataBase(): LiveData<List<Building>> {
        return withContext(Dispatchers.IO) {
            var buildings = databaseBuilding.getAllBuilding()
            buildings
        }
    }

    fun onStartTracking(buildingName: String) {
        uiScope.launch {
            var building = Building()
            building.nameBuildings = buildingName

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

    private suspend fun updateBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.updateBuilding(building)
        }
    }

    private suspend fun insertBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.insertBuilding(building)
        }
    }

    fun onClear() {
        uiScope.launch {
            clearBuilding()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}