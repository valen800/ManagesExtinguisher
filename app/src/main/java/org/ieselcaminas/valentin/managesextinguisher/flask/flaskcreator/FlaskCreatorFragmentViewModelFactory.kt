package org.ieselcaminas.valentin.managesextinguisher.flask.flaskcreator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao

class FlaskCreatorFragmentViewModelFactory (
    private val dataBaseFlask: FlaskDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlaskCreatorFragmentViewModel::class.java)) {
            return FlaskCreatorFragmentViewModel(dataBaseFlask, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
