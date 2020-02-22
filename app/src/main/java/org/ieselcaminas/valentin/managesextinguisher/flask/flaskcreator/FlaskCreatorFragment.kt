package org.ieselcaminas.valentin.managesextinguisher.flask.flaskcreator


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.SingletonExt
import org.ieselcaminas.valentin.managesextinguisher.SingletonFlask
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFlaskCreatorBinding
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator.ExtinguisherCreatorFragmentArgs
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator.ExtinguisherCreatorFragmentDirections
import java.util.*

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
        val args = ExtinguisherCreatorFragmentArgs.fromBundle(arguments!!)

        binding.flaskViewModel = flaskCreatorViewModel
        binding.setLifecycleOwner(this)

        if (args.condition == "1") {
            binding.editTextInsertnFlask.setText(SingletonFlask.itemFlask.nFlask)
            binding.editTextInsertFlaskTradeMark.setText(SingletonFlask.itemFlask.trademark)
            binding.editTextSituationFlask.setText(SingletonFlask.itemFlask.situation)
            binding.textAreaDescriptionLocationFlask.setText(SingletonFlask.itemFlask.descriptionLocation)
            binding.editTextInsertEmptyWeight.setText(SingletonFlask.itemFlask.emptyWeight.toString())
            /*binding.LabelFactoryDate.setText(DateFormat.getInstance().format(itemExt.value?.factoryDate))
            binding.LabelDateLastRevision.setText(DateFormat.getInstance().format(itemExt.value?.dateLastRevision))*/
        }

        // SPINNERS
        var spinnerWeightResult = 0
        var spinnerModelResult = ""

        context?.let {
            val contentWeight = arrayListOf(300, 1000, 2000, 3000)
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentWeight)
            binding.spinnerContentWeightFlask.adapter = arrayAdapter

            binding.spinnerContentWeightFlask.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    spinnerWeightResult = 5
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinnerWeightResult = contentWeight[p2]
                }
            }
        }

        context?.let {
            val contentModel = arrayListOf("Nitrogeno" ,"Dióxido de carbono (C02)")
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentModel)
            binding.spinnerModelFlask.adapter = arrayAdapter


            binding.spinnerModelFlask.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    spinnerModelResult = "Modelo no seleccionado"
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinnerModelResult = contentModel[p2]
                }
            }
        }

        // SPINNERS

        // DATEPICKER
        var factoryDate = System.currentTimeMillis()
        var dateLastRevision = System.currentTimeMillis()
        var dateNextRevision = System.currentTimeMillis()

        binding.buttonFactoryDateFlask.setOnClickListener() {
            factoryDate = dialogDatePicker(binding.LabelFactoryDate)
        }

        binding.buttonDateLastRevisionFlask.setOnClickListener() {
            dateLastRevision = dialogDatePicker(binding.LabelDateLastRevision)
            dateNextRevision = dateLastRevision
        }

        // DATEPICKER

        binding.buttonSubmitExtinguisherInsertFlask.setOnClickListener() {
            if (args.condition == "1") {
                flaskCreatorViewModel.updateFlask(
                    SingletonFloorId.floorIdSingleton, binding.editTextInsertnFlask.text.toString(), binding.editTextSituationFlask.text.toString(),
                    binding.editTextInsertFlaskTradeMark.text.toString(), spinnerModelResult,
                    binding.textAreaDescriptionLocationFlask.text.toString(), binding.editTextInsertEmptyWeight.text.toString().toInt(),
                    spinnerWeightResult, factoryDate, dateLastRevision, dateNextRevision)

                flaskCreatorViewModel.startNavigatingToElements()
            } else if (args.condition == "0") {

                flaskCreatorViewModel.insertFlask(
                    SingletonFloorId.floorIdSingleton, binding.editTextInsertnFlask.text.toString(), binding.editTextSituationFlask.text.toString(),
                    binding.editTextInsertFlaskTradeMark.text.toString(), spinnerModelResult,
                    binding.textAreaDescriptionLocationFlask.text.toString(), binding.editTextInsertEmptyWeight.text.toString().toInt(),
                    spinnerWeightResult, factoryDate, dateLastRevision, dateNextRevision)

                flaskCreatorViewModel.startNavigatingToElements()
            }
        }

        binding.buttonCancelInsertFlask.setOnClickListener() {
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

    private fun dialogDatePicker(textView: TextView): Long {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        context?.let {
            val dpd = DatePickerDialog(it, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                //set to TextView
                if ((mMonth+1) > 9 ) {
                    textView.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                } else {
                    textView.setText("" + mDay + "/0" + (mMonth + 1) + "/" + mYear)
                }
            }, year, month, day)
            dpd.show()
        }
        return c.timeInMillis
    }
}
