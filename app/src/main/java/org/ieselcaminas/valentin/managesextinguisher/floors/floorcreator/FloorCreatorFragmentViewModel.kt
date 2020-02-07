package org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor

class FloorCreatorFragmentViewModel(
    private val databaseFloor: FloorDao,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToFloor = MutableLiveData<Boolean>()
    val navigateToFloor: LiveData<Boolean>
        get() = _navigateToFloor

    fun startNavigatingToFloor() {
        _navigateToFloor.value = true
    }

    fun doneNavigatingToFloor() {
        _navigateToFloor.value = false
    }

    private suspend fun getFloorsFromDataBase(): LiveData<List<Floor>> {
        return withContext(Dispatchers.IO) {
            var floors = databaseFloor.getAllFloors()
            floors
        }
    }

    fun onStartTracking(FloorName: String, FloorNumber: Long, buildingID: Long) {
        uiScope.launch {
            var floor = Floor()
            floor.nameFloor = FloorName
            floor.nFloor = FloorNumber
            floor.buildingId = buildingID

            insertFloor(floor)
        }
    }

    private suspend fun updateFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            databaseFloor.updateFloor(floor)
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