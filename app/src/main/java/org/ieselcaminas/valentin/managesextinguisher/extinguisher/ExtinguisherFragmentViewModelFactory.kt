package org.ieselcaminas.valentin.managesextinguisher.extinguisher

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao

class ExtinguisherFragmentViewModelFactory (
    private val dataBaseExtinguisher: ExtinguisherDao,
    val activity: FragmentActivity?,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExtinguisherFragmentViewModel::class.java)) {
            return ExtinguisherFragmentViewModel(dataBaseExtinguisher, activity, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}