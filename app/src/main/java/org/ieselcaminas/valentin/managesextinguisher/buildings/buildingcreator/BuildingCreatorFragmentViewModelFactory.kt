package org.ieselcaminas.valentin.managesextinguisher.buildings.buildingcreator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.buildings.BuildingFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.database.Building.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao


class BuildingCreatorFragmentViewModelFactory(
    private val dataBaseBuilding: BuildingDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingCreatorFragmentViewModel::class.java)) {
            return BuildingCreatorFragmentViewModel(dataBaseBuilding, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}