package com.example.gunplogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gunplogs.ui.GunplogInfoPage
import com.example.gunplogs.ui.GunplogSearchBar
import com.example.gunplogs.ui.GunplogsMainScreen
import com.example.gunplogs.viewmodels.GunplogViewModel

enum class GunplogsScreenType {
    Main,
    InfoPage
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GunplogsApp(
    viewModel: GunplogViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val category = uiState.value.category
    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        ), // TODO() : faire le theme principal
                        color = Color(0xFF22054D),
                    )
                }
            )
            GunplogSearchBar (
                onSearchChange = { viewModel.onSearchValueChanged(it)}
            )
        },
        bottomBar = {
            BottomAppBar(windowInsets = BottomAppBarDefaults.windowInsets) {
                // Leading icons should typically have a high content alpha
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                }
                // The actions should be at the end of the BottomAppBar. They use the default medium
                // content alpha provided by BottomAppBar
                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
            }
        }
//        bottomBar = {
//            BottomAppBar (
//                windowInsets = AppBarDefaults.bottomAppBarWindowInsets),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent)
//                    .navigationBarsPadding(),
//                contentPadding = PaddingValues.Zero
//            ){
////                    GunplogMenuBar(
////                        modifier = Modifier
////                            .fillMaxSize(),
////                        onCategoryChange = { viewModel.changeCategory(it) },
////                        currentCategory = category
////                    )
//                }
//            }
    ) { innerPadding ->
        var navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = GunplogsScreenType.Main.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(GunplogsScreenType.Main.name) {
                GunplogsMainScreen(
                    viewModel = viewModel,
                    showInfoPage = {
                        navController.navigate("InfoPage/$it")
                    }
                )
            }
            composable(
                route = "InfoPage/{uid}",
                arguments = listOf(
                    navArgument(name = "uid"){
                        type = NavType.IntType
                    }
                )
            ) { navBackStackEntry ->
                navBackStackEntry.arguments?.getInt("uid").let {
                    val kit = viewModel.loadKit(it)
                    GunplogInfoPage(
                        kit = kit,
                        onClickAddKit = { viewModel.addKitToUserCollection(kit) },
                        onClickAddWishlist = { viewModel.addKitToUserWishlist(kit) },
                        onClickReturnToMainScreen = { navController.navigate(GunplogsScreenType.Main.name) }
                    )
                }
            }
        }
    }


}