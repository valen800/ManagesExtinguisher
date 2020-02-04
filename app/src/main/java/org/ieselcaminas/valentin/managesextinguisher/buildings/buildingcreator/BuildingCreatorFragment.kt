package org.ieselcaminas.valentin.managesextinguisher.buildings.buildingcreator


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
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentBuildingCreatorBinding

/**
 * A simple [Fragment] subclass.
 */
class BuildingCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBuildingCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_building_creator, container, false)

        val application = requireNotNull(this.activity).application
        val databaseBuilding: BuildingDao = ManagesExtinguisherDatabase.getInstance(application).buildingDao
        val viewModelFactory = BuildingCreatorFragmentViewModelFactory(databaseBuilding, application)
        val buildingCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            BuildingCreatorFragmentViewModel::class.java)
        binding.buildingCreatorViewModel = buildingCreatorViewModel
        binding.setLifecycleOwner(this)

        binding.buttonSubmitBuilding.setOnClickListener() {
            buildingCreatorViewModel.onStartTracking(binding.editTextBuildingName.text.toString(), binding.editTextAmountFloor.text.toString().toInt())
            buildingCreatorViewModel.startNavigatingToBuilding()
        }

        buildingCreatorViewModel.navigateToBuilding.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(R.id.action_buildingCreatorFragment_to_buildingFragment)
                buildingCreatorViewModel.doneNavigating()
            }
        })

        return binding.root
    }


}
