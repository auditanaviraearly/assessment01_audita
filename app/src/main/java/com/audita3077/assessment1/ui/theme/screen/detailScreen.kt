package com.audita3077.assessment1.ui.theme.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.audita3077.assessment1.R
import com.audita3077.assessment1.ui.theme.Assessment1Theme
import com.audita3077.assessment1.model.HerbalItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, herbalItem: HerbalItem) {
    val herbalKey = herbalItem.name.lowercase()

    val descriptions = mapOf(
        stringResource(id = R.string.category_history) to stringResourceByName("history_$herbalKey"),
        stringResource(id = R.string.category_usage) to stringResourceByName("usage_$herbalKey"),
        stringResource(id = R.string.category_side_effects) to stringResourceByName("side_effects_$herbalKey"),
        stringResource(id = R.string.category_how_to_use) to stringResourceByName("how_to_use_$herbalKey")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = herbalItem.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Image(
                    painter = painterResource(id = herbalItem.imageRes),
                    contentDescription = herbalItem.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = herbalItem.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Menampilkan daftar kategori yang bisa diperluas
            items(descriptions.toList()) { (category, description) ->
                var expanded by remember { mutableStateOf(false) }

                Column {
                    Button(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = category)
                    }
                    if (expanded) {
                        Text(text = description, modifier = Modifier.padding(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun stringResourceByName(name: String): String {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(name, "string", context.packageName)
    return if (resId != 1) stringResource(id = resId) else "Data tidak ditemukan"
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    val sampleHerbal = HerbalItem("Jahe", android.R.drawable.ic_menu_gallery, "Bermanfaat untuk daya tahan tubuh.")

    Assessment1Theme {
        DetailScreen(navController = navController, herbalItem = sampleHerbal)
    }
}
