package com.example.market.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Visibility
import com.example.market.R
import com.example.market.databinding.ActivityMainBinding
import com.example.market.ui.shopping_cart.ShoppingCartFragment
import com.example.market.ui.shopping_cart.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val EMAIL = "email"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        checkAndGetUsername()
    }

    private fun checkAndGetUsername() {
        CoroutineScope(Dispatchers.IO).launch {
            getEmail().take(1).collect { d ->
                if (d != null) {
                    binding.tvActualEmail.visibility = View.VISIBLE
                    binding.tvActualEmail.text = d
                } else {
                    binding.tvActualEmail.visibility = View.GONE
                }
            }
        }
    }

    private fun getEmail(): Flow<String?> {
        return baseContext.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(ShoppingCartFragment.EMAIL)]
        }
    }

    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}