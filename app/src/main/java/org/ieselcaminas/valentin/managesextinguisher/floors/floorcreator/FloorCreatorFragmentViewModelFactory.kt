package org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorsFragmentViewModel

class FloorCreatorFragmentViewModelFactory (
private val dataBaseFloor: FloorDao,
private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FloorCreatorFragmentViewModel::class.java)) {
            return FloorCreatorFragmentViewModel(dataBaseFloor, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}