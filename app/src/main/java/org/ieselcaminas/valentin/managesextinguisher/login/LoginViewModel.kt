package org.ieselcaminas.valentin.managesextinguisher.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import org.ieselcaminas.valentin.managesextinguisher.database.firebase.FirebaseUserLiveData

class LoginViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FirebaseUserLiveData()
        .map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

}