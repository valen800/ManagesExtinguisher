package org.ieselcaminas.valentin.managesextinguisher.flask.flaskcreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFlaskCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator.ExtinguisherCreatorFragmentDirections

/**
 * A simple [Fragment] subclass.
 */
class FlaskCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlaskCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flask_creator, container, false)

        val application = requireNotNull(this.activity).application
        val databaseFlask: FlaskDao = ManagesExtinguisherDatabase.getInstance(application).flaskDao

        val viewModelFactory = FlaskCreatorFragmentViewModelFactory(databaseFlask, application)
        val flaskCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            FlaskCreatorFragmentViewModel::class.java)

        binding.flaskViewModel = flaskCreatorViewModel
        binding.setLifecycleOwner(this)

        binding.buttonSubmitExtinguisherInsertFlask.setOnClickListener() {
            flaskCreatorViewModel.insertFlask(
                SingletonFloorId.floorIdSingleton, binding.editTextInsertnFlask.text.toString(), binding.editTextInsertFlaskSituation.text.toString(),
                binding.editTextInsertFlaskTradeMark.text.toString(), binding.editTextInsertFlaskModel.text.toString(),
                binding.editTextInsertFlaskDescriptionLocation.text.toString(), binding.editTextInsertFlaskEmptyWeight.text.toString().toInt(),
                binding.editTextInsertFlaskTotalWeight.text.toString().toInt(),
                System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis())

            flaskCreatorViewModel.startNavigatingToElements()
        }

        flaskCreatorViewModel.navigateToElements.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    FlaskCreatorFragmentDirections.actionFlaskCreatorFragment2ToTabFragment(
                        SingletonFloorId.floorIdSingleton))
                flaskCreatorViewModel.doneNavigatingToElements()
            }
        })

        return binding.root
    }


}
