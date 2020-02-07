package org.ieselcaminas.valentin.managesextinguisher.floors.floorcreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.buildings.buildingcreator.BuildingCreatorFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.buildings.buildingcreator.BuildingCreatorFragmentViewModelFactory
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFloorCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorsFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class FloorCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFloorCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_floor_creator, container, false)

        val application = requireNotNull(this.activity).application
        val args = FloorsFragmentArgs.fromBundle(arguments!!)

        val databaseFloor: FloorDao = ManagesExtinguisherDatabase.getInstance(application).floorDao

        val viewModelFactory = FloorCreatorFragmentViewModelFactory(databaseFloor, application)
        val floorCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            FloorCreatorFragmentViewModel::class.java)

        binding.floorCreatorViewModel = floorCreatorViewModel
        binding.setLifecycleOwner(this)

        binding.buttonSubmitBuilding.setOnClickListener() {
            floorCreatorViewModel.onStartTracking(binding.editTextFloorName.text.toString(), binding.editTextFloorName.text.toString().toLong(), args.buildingId)
            floorCreatorViewModel.startNavigatingToFloor()
        }

        floorCreatorViewModel.navigateToFloor.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(R.id.action_buildingCreatorFragment_to_buildingFragment)
                floorCreatorViewModel.doneNavigatingToFloor()
            }
        })

        return binding.root
    }


}
