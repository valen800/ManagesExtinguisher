package org.ieselcaminas.valentin.managesextinguisher.flask.flaskcreator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator.ExtinguisherCreatorFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator.FloorCreatorFragmentViewModel

/*
class FlaskCreatorFragmentViewModelFactory (
    private val dataBaseFlask: FlaskDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExtinguisherCreatorFragmentViewModel::class.java)) {
            return ExtinguisherCreatorFragmentViewModel(dataBaseExtinguisher, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}*/
