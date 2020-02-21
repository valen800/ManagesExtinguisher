package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao


class BuildingFragmentViewModel(
    val databaseBuilding: BuildingDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val buildingsList = databaseBuilding.getAllBuilding()

    private var _navigatingToInserDialog = MutableLiveData<Boolean>()
    val navigatingToInserDialog: LiveData<Boolean>
        get() = _navigatingToInserDialog

    private var __navigateToFloors = MutableLiveData<Long>()
    val navigateToFloors: LiveData<Long>
        get() = __navigateToFloors

    private var _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    fun startRefresh() {
        _refresh.value = true
    }

    fun doneRefresh() {
        _refresh.value = false
    }

    fun startNavigatingToInserDialog() {
        _navigatingToInserDialog.value = true
    }

    fun doneNavigatingToInserDialog() {
        _navigatingToInserDialog.value = false
    }

    fun startNavigatingToFloors(BuildingId: Long) {
        __navigateToFloors.value = BuildingId
    }

    fun doneNavigatingToFloors() {
        __navigateToFloors.value = null
    }

    fun deleteBuilding(buildingId: Long) {
        uiScope.launch {
            deleteBuildingCou(buildingId)
        }
    }

    private suspend fun deleteBuildingCou(BuildingId: Long) {
        withContext(Dispatchers.IO) {
            databaseBuilding.deleteById(BuildingId)
        }
    }

    private suspend fun getBuildingsFromDataBase(): LiveData<List<Building>> {
        return withContext(Dispatchers.IO) {
            var buildings = databaseBuilding.getAllBuilding()
            buildings
        }
    }

    fun updateBuilding(building: Building) {
        uiScope.launch {
            updateBuildingCou(building)
        }
    }

    private suspend fun updateBuildingCou(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.updateBuilding(building)
        }
    }

    fun insertBuilding(building: Building) {
        uiScope.launch {
            insertBuildingCou(building)
        }
    }

    private suspend fun insertBuildingCou(building: Building) {
        withContext(Dispatchers.IO) {
            databaseBuilding.insertBuilding(building)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}