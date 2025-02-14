package com.capgemini.profiler.data

import androidx.activity.ComponentActivity
import com.capgemini.profiler.ui.auth.AuthState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    override suspend fun sendOtp(
        phoneNumber: String,
        activity: ComponentActivity
    ): Flow<AuthState> = callbackFlow {
        trySend(AuthState.Loading)

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                trySend(AuthState.Success)
                close()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                trySend(AuthState.Error(e.message ?: "Verification Failed"))
                close()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                trySend(AuthState.OtpSent)
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+1" + phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose { }
    }

    override suspend fun verifyOtp(otp: String): Flow<AuthState> = callbackFlow {
        trySend(AuthState.Loading)

        val credential = storedVerificationId?.let { PhoneAuthProvider.getCredential(it, otp) }
        credential?.let {
            auth.signInWithCredential(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthState.CheckCred)
                } else {
                    trySend(AuthState.Error("Login Failed"))
                }
                close()
            }
        } ?: run {
            trySend(AuthState.Error("Invalid OTP"))
            close()
        }
        awaitClose { }
    }

    override suspend fun checkAdminCredentials(useName: String, passWord: String): Flow<AuthState> =
        callbackFlow {
            trySend(AuthState.Loading)
            firestore.collection("admins")
                .whereEqualTo("userName", useName)
                .whereEqualTo("password", passWord)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        trySend(AuthState.Success)
                    } else {
                        trySend(AuthState.Error("Invalid Admin Credentials"))
                    }
                    close()
                }
                .addOnFailureListener { e ->
                    trySend(AuthState.Error(e.message ?: "Error fetching admin details"))
                    close()
                }

            awaitClose { }
        }
}
