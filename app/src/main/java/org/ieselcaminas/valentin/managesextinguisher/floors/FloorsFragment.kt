package org.ieselcaminas.valentin.managesextinguisher.floors


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.buildings.*
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
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
    private lateinit var floorsViewModel: FloorsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFloorsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_floors, container, false)
        val application = requireNotNull(this.activity).application
        val args = FloorsFragmentArgs.fromBundle(arguments!!)

        val databaseBuilding: BuildingDao = ManagesExtinguisherDatabase.getInstance(application).buildingDao
        val databaseFloor: FloorDao = ManagesExtinguisherDatabase.getInstance(application).floorDao
        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao
        val databaseFlask: FlaskDao = ManagesExtinguisherDatabase.getInstance(application).flaskDao

        val viewModelFactory = FloorsFragmentViewModelFactory(databaseBuilding, databaseFloor, databaseExtinguisher, databaseFlask, application)
        val floorsViewModel = ViewModelProviders.of(this, viewModelFactory).get(FloorsFragmentViewModel::class.java)
        binding.floorsViewModel = floorsViewModel
        binding.setLifecycleOwner(this)

        //Adapter RecyclerView

        val manager = GridLayoutManager(activity, 2)
        binding.floorList.layoutManager = manager

        val adapter = FloorAdapter(FloorListener {
                floorId -> floorsViewModel.startNavigatingToFragmentElements(floorId)
        })
        binding.floorList.adapter = adapter

        floorsViewModel.getFloorsFromDataBase(args.buildingId).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //Adapter RecyclerView

        binding.fabCreatorFloor.setOnClickListener() {
            floorsViewModel.startNavigatingToFloorCreator()
        }

        floorsViewModel.navigateToFragmentElements.observe(this, Observer { floorId ->
            floorId?.let {
                this.findNavController().navigate(FloorsFragmentDirections.actionFloorsFragmentToTabFragment(floorId))
                floorsViewModel.doneNavigatingToFragmentElements()
            }
        })

        floorsViewModel.navigateToFloorCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(FloorsFragmentDirections.actionFloorsFragmentToFloorCreatorFragment(args.buildingId))
                floorsViewModel.doneNavigatingToFloorCreator()
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floorsViewModel = ViewModelProviders.of(this).get(FloorsFragmentViewModel::class.java)
    }
}
