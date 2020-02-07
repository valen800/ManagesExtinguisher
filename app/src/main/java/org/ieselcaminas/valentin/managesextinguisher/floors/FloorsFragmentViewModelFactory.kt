package org.ieselcaminas.valentin.managesextinguisher.floors

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao

class FloorsFragmentViewModelFactory (
    private val dataBaseBuilding: BuildingDao,
    private val dataBaseFloor: FloorDao,
    private val dataBaseExtinguisher: ExtinguisherDao,
    private val dataBaseFlask: FlaskDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FloorsFragmentViewModel::class.java)) {
            return FloorsFragmentViewModel(dataBaseBuilding, dataBaseFloor, dataBaseExtinguisher, dataBaseFlask, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}