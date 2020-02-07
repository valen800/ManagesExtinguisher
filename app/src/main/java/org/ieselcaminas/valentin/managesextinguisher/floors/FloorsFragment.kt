package org.ieselcaminas.valentin.managesextinguisher.floors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.buildings.*
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFloorsBinding

/**
 * A simple [Fragment] subclass.
 */
class FloorsFragment : Fragment() {

    companion object {
        fun newInstance() = FloorsFragment()
    }

    private lateinit var appDatabase: ManagesExtinguisherDatabase
    private lateinit var FloorsViewModel: BuildingFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFloorsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_floors, container, false)
        val application = requireNotNull(this.activity).application

        val databaseBuilding: BuildingDao = ManagesExtinguisherDatabase.getInstance(application).buildingDao
        val databaseFloor: FloorDao = ManagesExtinguisherDatabase.getInstance(application).floorDao

        val viewModelFactory = BuildingFragmentViewModelFactory(databaseBuilding, databaseFloor, application)
        val FloorsViewModel = ViewModelProviders.of(this, viewModelFactory).get(FloorsFragmentViewModel::class.java)
        binding.floorsViewModel = FloorsViewModel

        //Adapter RecyclerView


        //Adapter RecyclerView

        binding.fabCreatorFloor.setOnClickListener() {
            FloorsViewModel.startNavigatingToFloorCreator()
        }

        FloorsViewModel.navigateToFloorCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(BuildingFragmentDirections.actionBuildingFragmentToBuildingCreatorFragment())
                FloorsViewModel.doneNavigatingToFloorCreator()
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        FloorsViewModel = ViewModelProviders.of(this).get(BuildingFragmentViewModel::class.java)
    }
}
