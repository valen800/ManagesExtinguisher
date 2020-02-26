package org.ieselcaminas.valentin.managesextinguisher.title


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.firebase.ui.auth.AuthUI
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTitleBinding
import org.ieselcaminas.valentin.managesextinguisher.login.LoginViewModel
import java.util.Observer


class TitleFragment : Fragment() {

    companion object {
        const val TAG = "TitleFragment"
        const val SIGN_IN_REQUEST_CODE = 1001
    }

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)


        context?.let {
            FirebaseApp.initializeApp(it)
        }

        binding.buttonSinConexion.setOnClickListener() {
            Navigation.findNavController(it)
                .navigate(TitleFragmentDirections.actionTitleFragmentToBuildingFragment())
        }

        binding.buttonLogin.setOnClickListener() {
            launchSignInFlow()
            //Navigation.findNavController(it).navigate(TitleFragmentDirections.actionTitleFragmentToBuildingFragment())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observeAuthenticationState() {
        val factToDisplay = viewModel.getFactToDisplay(requireContext())

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.buttonLogin.text = getString(R.string.logout_button_text)
                    binding.buttonLogin.setOnClickListener {
                        // implement logging out user in next step
                    }

                    // 2. If the user is logged in,
                    Log.i("Logged", "User is logged")
                    binding.welcomeText.text = getFactWithPersonalization(factToDisplay)

                }
                else -> {
                    // 3. Lastly, if there is no logged-in user,
                    // auth_button should display Login and
                    //  launch the sign in screen when clicked.
                    binding.buttonLogin.text = getString(R.string.login_button_text)
                    binding.buttonLogin.setOnClickListener() { launchSignInFlow() }
                    binding.welcomeText.text = factToDisplay
                }
            }
        })
    }

    private fun getFactWithPersonalization(fact: String): String {
        return String.format(
            resources.getString(
                R.string.welcome_message_authed,
                FirebaseAuth.getInstance().currentUser?.displayName,
                Character.toLowerCase(fact[0]) + fact.substring(1)
            )
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.i(
                    TAG,
                    "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }


}
