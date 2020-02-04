package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao


class BuildingFragmentViewModelFactory(
    private val dataBaseBuilding: BuildingDao,
    private val dataBaseFloor: FloorDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingFragmentViewModel::class.java)) {
            return BuildingFragmentViewModel(dataBaseBuilding, dataBaseFloor, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}