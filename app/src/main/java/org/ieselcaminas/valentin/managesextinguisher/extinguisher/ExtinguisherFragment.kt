package org.ieselcaminas.valentin.managesextinguisher.extinguisher


import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.tabFragmentDirections
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherBinding
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorAdapter
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorListener
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorsFragmentDirections
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherFragment : Fragment() {

    private lateinit var extinguisher : LiveData<Extinguisher>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher, container, false)
        val activity: FragmentActivity? = activity
        val application = requireNotNull(this.activity).application

        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherFragmentViewModelFactory(databaseExtinguisher, activity, application)
        val extinguisherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherFragmentViewModel::class.java)

        binding.extinguisherViewModel = extinguisherViewModel

        //Adapter RecyclerView

        val manager = LinearLayoutManager(context)
        binding.extinguisherList.layoutManager = manager

        val adapter = ExtinguisherAdapter(ExtinguisherListener { extinguisherId ->
            extinguisherViewModel.getExtinguisher(extinguisherId)
        }, activity, databaseExtinguisher, viewLifecycleOwner, extinguisherViewModel)
        binding.extinguisherList.adapter = adapter

        extinguisherViewModel.getExtinguishersFromDataBase(SingletonFloorId.floorIdSingleton).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        extinguisherViewModel.refresh.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(tabFragmentDirections.actionTabFragmentSelf(SingletonFloorId.floorIdSingleton))
                extinguisherViewModel.doneRefresh()
            }
        })
        //Adapter RecyclerView

        binding.fabCreatorExtinguisher.setOnClickListener() {
            extinguisherViewModel.startNavigatingToExtinguisherCreator()
        }

        extinguisherViewModel.navigateToExtinguisherCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(tabFragmentDirections.actionTabFragmentToExtinguisherCreatorFragment2(SingletonFloorId.floorIdSingleton))
                extinguisherViewModel.doneNavigatingToExtinguisherCreator()
            }
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }
}
