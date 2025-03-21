package com.audita3077.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.audita3077.assessment1.ui.theme.Assessment1Theme
import com.audita3077.assessment1.navigation.SetupNavGraph
import com.audita3077.assessment1.model.Language

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var selectedLanguage by remember { mutableStateOf(Language.ID) }

            Assessment1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SetupNavGraph(
                        navController = navController,
                        selectedLanguage = selectedLanguage,
                        onLanguageChange = { selectedLanguage = it }
                    )
                }
            }
        }
    }
}
