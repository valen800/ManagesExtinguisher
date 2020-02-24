package org.ieselcaminas.valentin.managesextinguisher.flask


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.tabFragmentDirections

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFlaskBinding
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherListener

/**
 * A simple [Fragment] subclass.
 */
class FlaskFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlaskBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flask, container, false)

        val activity: FragmentActivity? = activity
        val application = requireNotNull(this.activity).application

        val databaseFlask: FlaskDao = ManagesExtinguisherDatabase.getInstance(application).flaskDao

        val viewModelFactory = FlaskFragmentViewModelFactory(databaseFlask, activity, application)
        val flaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            FlaskFragmentViewModel::class.java)

        binding.flaskViewModel = flaskViewModel

        //Adapter RecyclerView

        val manager = LinearLayoutManager(context)
        binding.flaskList.layoutManager = manager

        val adapter = FlaskAdapter(FlaskListener { flaskId ->
            flaskViewModel.getFlask(flaskId)
        }, activity, databaseFlask, viewLifecycleOwner, flaskViewModel)
        binding.flaskList.adapter = adapter

        flaskViewModel.getFlasksFromDataBase(SingletonFloorId.floorIdSingleton).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        //Observer to navigate TabFragmentSelf and refresh this fragment
        flaskViewModel.refresh.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    tabFragmentDirections.actionTabFragmentSelf(
                        SingletonFloorId.floorIdSingleton))
                flaskViewModel.doneRefresh()
            }
        })
        //Adapter RecyclerView

        binding.fabCreatorFlask.setOnClickListener() {
            flaskViewModel.startNavigateToFlaskCreator()
        }

        flaskViewModel.navigateToFlaskCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    tabFragmentDirections.actionTabFragmentToFlaskCreatorFragment2(SingletonFloorId.floorIdSingleton))
                flaskViewModel.doneNavigateToFlaskCreator()
            }
        })

        flaskViewModel.navigateToFlaskCreatorFromAdapter.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(tabFragmentDirections.actionTabFragmentToFlaskCreatorFragment2(SingletonFloorId.floorIdSingleton))
                flaskViewModel.doneNavigatingToFlaskCreatorFromAdapter()
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }
}