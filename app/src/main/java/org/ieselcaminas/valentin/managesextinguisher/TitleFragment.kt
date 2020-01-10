package org.ieselcaminas.valentin.managesextinguisher


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentLoginBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)

        binding.buttonToFragmentLogin.setOnClickListener() {
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_loginFragment)
        }


        return binding.root
    }


}
