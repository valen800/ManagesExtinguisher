package org.ieselcaminas.valentin.managesextinguisher.flask.flaskcreator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao

class FlaskCreatorFragmentViewModel(private val databaseFlask: FlaskDao, application: Application): AndroidViewModel(application) {

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

    private suspend fun getFlaskFromDataBase(): LiveData<List<Flask>> {
        return withContext(Dispatchers.IO) {
            var flasks = databaseFlask.getFlaskByFloorID(SingletonFloorId.floorIdSingleton)
            flasks
        }
    }

    fun insertFlask(
        extinguisherFloorId: Long, nExtinguisher: String,
        situation: String, tradeMark: String, model: String,
        descriptionLocation: String,
        emptyWeight: Int, totalWeight: Int,
        factory_date: Long, dateLastRevision: Long, dateNextRevision: Long
    ) {

        uiScope.launch {
            var flask = Flask()
            flask.flaskFloorId = extinguisherFloorId
            flask.nFlask = nExtinguisher
            flask.situation = situation
            flask.trademark = tradeMark
            flask.model = model
            flask.descriptionLocation = descriptionLocation
            flask.emptyWeight = emptyWeight
            flask.totalWeight = totalWeight
            flask.factoryDate = factory_date
            flask.dateLastRevision = dateLastRevision
            flask.dateNextRevision = dateNextRevision

            insertFlask(flask)
        }
    }

    private suspend fun updateFlask(flask: Flask) {
        withContext(Dispatchers.IO) {
            databaseFlask.updateFlask(flask)
        }
    }

    private suspend fun insertFlask(flask: Flask) {
        withContext(Dispatchers.IO) {
            databaseFlask.insertFlask(flask)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}