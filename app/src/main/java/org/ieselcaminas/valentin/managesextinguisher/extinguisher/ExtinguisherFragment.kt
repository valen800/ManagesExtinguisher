package org.ieselcaminas.valentin.managesextinguisher.extinguisher


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ieselcaminas.valentin.managesextinguisher.R

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extinguisher, container, false)
    }


}
