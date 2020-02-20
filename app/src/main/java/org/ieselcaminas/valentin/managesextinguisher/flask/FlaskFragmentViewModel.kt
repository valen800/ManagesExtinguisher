package org.ieselcaminas.valentin.managesextinguisher.flask

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao

class FlaskFragmentViewModel(private val databaseFlask: FlaskDao, val activity: FragmentActivity?, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    private var _navigateToFlaskCreator = MutableLiveData<Boolean>()
    val navigateToFlaskCreator: LiveData<Boolean>
        get() = _navigateToFlaskCreator

    fun startRefresh() {
        _refresh.value = true
    }

    fun doneRefresh() {
        _refresh.value = false
    }

    fun startNavigateToFlaskCreator() {
        _navigateToFlaskCreator.value = true
    }

    fun doneNavigateToFlaskCreator() {
        _navigateToFlaskCreator.value = false
    }

    fun getFlasksFromDataBase(floorId: Long): LiveData<List<Flask>> {
        val FlaskList = databaseFlask.getFlaskByFloorID(floorId)
        return FlaskList
    }

    fun getFlask(flaskId: Long): LiveData<Flask> {
        val flask = databaseFlask.getFlaskByID(flaskId)
        return flask

    }

    fun deleteFlask(flaskId: Long) {
        uiScope.launch {
            deleteFlaskCou(flaskId)
        }
    }

    fun modifyFlask(flask: Flask) {
        uiScope.launch {
            updateFlask(flask)
        }
    }

    fun introduceFlask(flask: Flask) {
        uiScope.launch {
            insertFlask(flask)
        }
    }

    private suspend fun deleteFlaskCou(flaskId: Long) {
        withContext(Dispatchers.IO) {
            databaseFlask.deleteById(flaskId)
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