package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator


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
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding
import java.util.*
import android.app.DatePickerDialog
import android.util.Log
import android.widget.*
import java.sql.Struct

class ExtinguisherCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher_creator, container, false)

        val application = requireNotNull(this.activity).application
        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherCreatorFragmentViewModelFactory(databaseExtinguisher, application)
        val extinguisherCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherCreatorFragmentViewModel::class.java)

        binding.extinguisherCreatorViewModel = extinguisherCreatorViewModel
        binding.setLifecycleOwner(this)

        // SPINNERS

        var spinnerWeightResult = 0
        var spinnerModelResult = ""


        context?.let {
            val contentWeight = arrayListOf(5, 6, 9, 10, 12, 25, 50)
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentWeight)
            binding.spinnerWeight.adapter = arrayAdapter

            binding.spinnerWeight.onItemSelectedListener = object :
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
            val contentModel = arrayListOf("Agua pulverizada", "Agua desmineralizada", "Agua y espuma (AFFF)",
                "Dióxido de carbono (CO2)", "Polvo químico universal – ABC", "Polvo químico seco – BC",
                "Polvo químico – D")
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentModel)
            binding.spinnerModels.adapter = arrayAdapter


            binding.spinnerModels.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    spinnerModelResult = "Modelo no seleccionado"
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinnerModelResult = contentModel[p2].toString()
                }
            }
        }

        // SPINNERS

        // DATEPICKER

        val factoryDate = dialogDatePicker(binding.buttonFactoryDateExtinguisher, binding.LabelFactoryDate)
        val dateLastRevision = dialogDatePicker(binding.buttonDateLastRevisionExtinguisher, binding.LabelDateLastRevision)
        val dateNextRevision = dialogDatePicker(binding.buttonDateNextRevisionExtinguisher, binding.LabelDateNextRevision)
        // DATEPICKER

        // NAVIGATES
        binding.buttonSubmitExtinguisher.setOnClickListener() {
            Log.i("factoryDate", factoryDate.toString())
            Log.i("DateLast", dateLastRevision.toString())
            Log.i("DateNext", dateNextRevision.toString())
            Log.i("Model", spinnerModelResult)
            Log.i("Weight", spinnerWeightResult.toString())
            extinguisherCreatorViewModel.insertExtinguisher(
                SingletonFloorId.floorIdSingleton, binding.editTextnExtinguisher.text.toString(), binding.editTextSituation.text.toString(),
                binding.editTextTradeMark.text.toString(), spinnerModelResult, binding.textAreaDescriptionLocationExtinguisher.text.toString(),
                spinnerWeightResult, factoryDate, dateLastRevision, dateNextRevision)
            extinguisherCreatorViewModel.startNavigatingToElements()
        }

        binding.buttonCancelExtinguisher.setOnClickListener() {
            extinguisherCreatorViewModel.startNavigatingToElements()
        }

        extinguisherCreatorViewModel.navigateToElements.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(ExtinguisherCreatorFragmentDirections.actionExtinguisherCreatorFragment2ToTabFragment(SingletonFloorId.floorIdSingleton))
                extinguisherCreatorViewModel.doneNavigatingToElements()
            }
        })
        // NAVIGATES

        return binding.root
    }

    private fun dialogDatePicker(button: Button, textView: TextView): Long {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        button.setOnClickListener() {
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

        }
        if (c.timeInMillis == null) {
            return System.currentTimeMillis()
        }
        return c.timeInMillis
    }
}
