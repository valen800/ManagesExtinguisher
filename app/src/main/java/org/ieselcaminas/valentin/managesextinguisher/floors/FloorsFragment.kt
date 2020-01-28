package org.ieselcaminas.valentin.managesextinguisher.floors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ieselcaminas.valentin.managesextinguisher.R

/**
 * A simple [Fragment] subclass.
 */
class FloorsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_floors, container, false)
    }


}
