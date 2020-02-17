package org.ieselcaminas.valentin.managesextinguisher.flask

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao

class FlaskFragmentViewModelFactory (
    private val dataBaseFlask: FlaskDao,
    val activity: FragmentActivity?,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlaskFragmentViewModel::class.java)) {
            return FlaskFragmentViewModel(dataBaseFlask, activity, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}