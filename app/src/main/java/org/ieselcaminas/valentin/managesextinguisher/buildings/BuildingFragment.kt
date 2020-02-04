package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentBuildingBinding

class BuildingFragment : Fragment() {

    companion object {
        fun newInstance() = BuildingFragment()
    }

    private lateinit var appDatabase: ManagesExtinguisherDatabase
    private lateinit var buildingViewModel: BuildingFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBuildingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_building, container, false)

        val application = requireNotNull(this.activity).application
        val databaseBuilding: BuildingDao = ManagesExtinguisherDatabase.getInstance(application).buildingDao
        val databaseFloor: FloorDao = ManagesExtinguisherDatabase.getInstance(application).floorDao
        val viewModelFactory = BuildingFragmentViewModelFactory(databaseBuilding, databaseFloor, application)
        val buildingViewModel = ViewModelProviders.of(this, viewModelFactory).get(BuildingFragmentViewModel::class.java)
        binding.buildingViewModel = buildingViewModel

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> 3
                else -> 1
            }
        }
        binding.buildingList.layoutManager = manager

        val adapter = BuildingAdapter(BuildingListener { buildingId ->
            buildingViewModel.onBuildingClicked(buildingId)
        })
        binding.buildingList.adapter = adapter

        binding.fabCreatorBuilding.setOnClickListener() {
            buildingViewModel.startNavigatingToBuildingCreator()
        }

        buildingViewModel.navigateToBuildingCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(R.id.action_buildingFragment_to_buildingCreatorFragment)
                buildingViewModel.doneNavigating()
            }
        })

        //buildingViewModel.onStartTracking()
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buildingViewModel = ViewModelProviders.of(this).get(BuildingFragmentViewModel::class.java)
    }
}
