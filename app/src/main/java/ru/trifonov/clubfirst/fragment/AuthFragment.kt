package ru.trifonov.clubfirst.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.dto.PositionsPaginated
import ru.trifonov.clubfirst.data.dto.User
import ru.trifonov.clubfirst.di.ApiModule


class AuthFragment : Fragment() {

    private lateinit var authBtn: CardView
    private lateinit var textTop:TextView
    private lateinit var textBottom:TextView
    val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authBtn = view.findViewById(R.id.authBtn)
        textTop = view.findViewById(R.id.top_text)
        textBottom = view.findViewById(R.id.bottom_text)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestId().requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        authBtn.setOnClickListener {
            launcher.launch(googleSignInClient.signInIntent)
        }

    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
            if (result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        else{
            Toast.makeText(requireContext(), "Упс... Произошла ошибка, проверьте соединение с Интернетом", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        CoroutineScope(Dispatchers.IO).launch {

                            var user: Any? = null
                            try {
                                user = ApiModule.provideApi().login(it.result.user!!.email!!)
                            }
                            catch (e: Exception){
                            }
                            if (user is User){
                                requireActivity().runOnUiThread {
                                    findNavController().navigate(R.id.action_auth_to_main)
                                }
                            }
                            else{
                                requireActivity().runOnUiThread {
                                    findNavController().navigate(R.id.action_auth_to_registration)
                                }
                            }
                        }

                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Упс... Произошла ошибка, проверьте соединение с Интернетом", Toast.LENGTH_SHORT).show()

            }
        }
        else{
            Toast.makeText(requireContext(), "Упс... Произошла ошибка, проверьте соединение с Интернетом", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onStart() {
        super.onStart()
        val textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.auth_text)
        textTop.startAnimation(textAnimation)
        textBottom.startAnimation(textAnimation)
    }
}