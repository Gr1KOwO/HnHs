package com.example.lessnon3_igor.presentation



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.presentation.data.repository.PreferenceStorage
import com.example.lessnon3_igor.presentation.ui.signin.SignInFragmentDirections
import dagger.android.AndroidInjection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Получаем NavController после установки макета
        try {
            // Получаем NavController из NavHostFragment
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            // Проверяем наличие токена
            val preferenceStorage = PreferenceStorage(applicationContext)
            val userToken = preferenceStorage.userToken
            Log.i("Token",userToken)
            if (userToken.isNotEmpty()) {
                // Токен есть, переходим к CatalogFragment
                val action = SignInFragmentDirections.actionFragmentSignInToFragmentCatalog()
                navController.navigate(action)
            }
        }catch (e:Exception)
        {
            Log.i("ERROOOOORRRR", e.toString())
        }

        fitContentView()
    }
    private fun fitContentView()
    {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}