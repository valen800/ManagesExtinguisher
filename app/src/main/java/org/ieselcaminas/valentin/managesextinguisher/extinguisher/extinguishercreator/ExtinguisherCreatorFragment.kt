package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ieselcaminas.valentin.managesextinguisher.R

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherCreatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extinguisher_creator, container, false)
    }


}