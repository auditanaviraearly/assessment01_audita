package com.audita3077.assessment1.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.audita3077.assessment1.model.Language
import com.audita3077.assessment1.ui.theme.screen.DetailScreen
import com.audita3077.assessment1.ui.theme.screen.HomeScreen
import com.audita3077.assessment1.ui.theme.screen.getHerbalList

@Composable
fun SetupNavGraph(navController: NavHostController, selectedLanguage: Language, onLanguageChange: (Language) -> Unit) {
    val herbalList = getHerbalList(selectedLanguage)

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, selectedLanguage, onLanguageChange)
        }
        composable("detail/{herbalName}") { backStackEntry ->
            val herbalName = backStackEntry.arguments?.getString("herbalName")
            val herbalItem = getHerbalList(selectedLanguage).find { it.name == herbalName }
            if (herbalItem != null) {
                DetailScreen(navController, herbalItem)
            } else {
                Text("Herbal not found")
            }
        }
    }
}
