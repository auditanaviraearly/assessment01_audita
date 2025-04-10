package com.audita3077.assessment1.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.audita3077.assessment1.R
import com.audita3077.assessment1.model.Language
import com.audita3077.assessment1.navigation.Screen
import com.audita3077.assessment1.ui.theme.Assessment1Theme
import kotlin.random.Random
import com.audita3077.assessment1.model.HerbalItem


// daftar herbal berdasarkan bahasa yang dipilih
fun getHerbalList(language: Language): List<HerbalItem> {
    return if (language == Language.ID) {
        listOf(
            HerbalItem("Jahe", R.drawable.image1, "Bermanfaat untuk meningkatkan daya tahan tubuh."),
            HerbalItem("Kunyit", R.drawable.kunyit, "Membantu mengurangi peradangan dan meningkatkan pencernaan."),
            HerbalItem("Lengkuas", R.drawable.lengkuas, "Baik untuk kesehatan pencernaan dan mengurangi nyeri."),
            HerbalItem("Temulawak", R.drawable.temulawak, "Membantu meningkatkan nafsu makan dan kesehatan hati."),
            HerbalItem("Serai", R.drawable.serai, "Memiliki sifat antibakteri dan baik untuk detoksifikasi.")
        )
    } else {
        listOf(
            HerbalItem("Ginger", R.drawable.image1, "Beneficial for boosting the immune system."),
            HerbalItem("Turmeric", R.drawable.kunyit, "Helps reduce inflammation and improve digestion."),
            HerbalItem("Galangal", R.drawable.lengkuas, "Good for digestive health and pain relief."),
            HerbalItem("Java Turmeric", R.drawable.temulawak, "Helps increase appetite and liver health."),
            HerbalItem("Lemongrass", R.drawable.serai, "Has antibacterial properties and is good for detoxification.")
        )
    }
}

// Enum untuk jenis pengurutan
enum class SortType {
    AZ, ZA, RANDOM
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    selectedLanguage: Language,
    onLanguageChange: (Language) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var sortType by remember { mutableStateOf(SortType.AZ) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val herbalList = getHerbalList(selectedLanguage)

    val filteredList = herbalList
        .filter { it.name.contains(searchText, ignoreCase = true) }
        .let { list ->
            when (sortType) {
                SortType.AZ -> list.sortedBy { it.name }
                SortType.ZA -> list.sortedByDescending { it.name }
                SortType.RANDOM -> list.shuffled(Random.Default)
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (selectedLanguage == Language.ID) "Tanaman Herbal" else "Herbal Plants") },
                actions = {
                    TextButton(onClick = {
                        onLanguageChange(if (selectedLanguage == Language.EN) Language.ID else Language.EN)
                    }) {
                        Text(if (selectedLanguage == Language.ID) "ID" else "EN")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            // Search Bar
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(if (selectedLanguage == Language.ID) "Cari herbal..." else "Search herbs...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Button(onClick = { isDropdownExpanded = true }) {
                    Text(if (selectedLanguage == Language.ID) "Urutkan" else "Sort")
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("A - Z") },
                        onClick = {
                            sortType = SortType.AZ
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Z - A") },
                        onClick = {
                            sortType = SortType.ZA
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(if (selectedLanguage == Language.ID) "Acak" else "Random") },
                        onClick = {
                            sortType = SortType.RANDOM
                            isDropdownExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Herbal List
            LazyColumn {
                items(filteredList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate(Screen.Detail.createRoute(item.name)) },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = item.imageRes),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    Assessment1Theme {
        HomeScreen(navController = navController, selectedLanguage = Language.ID, onLanguageChange = {})
    }
}
