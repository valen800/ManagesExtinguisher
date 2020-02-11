package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFloorsBinding
import org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator.FloorCreatorFragmentArgs
import org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator.FloorCreatorFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator.FloorCreatorFragmentViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher_creator, container, false)

        val application = requireNotNull(this.activity).application
        val args = FloorCreatorFragmentArgs.fromBundle(arguments!!)

        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherCreatorFragmentViewModelFactory(databaseExtinguisher, application)
        val extinguisherCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherCreatorFragmentViewModel::class.java)

        binding.extinguisherCreatorViewModel = extinguisherCreatorViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }


}
