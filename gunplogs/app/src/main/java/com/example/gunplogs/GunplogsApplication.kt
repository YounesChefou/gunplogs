package com.example.gunplogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gunplogs.ui.GunplogInfoPage
import com.example.gunplogs.viewmodels.GunplogViewModel
import com.example.gunplogs.ui.GunplogsMainScreen

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
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge, // TODO() : faire le theme principal
                        color = Color(0xFF22054D),
                    )
                }
            )
        }
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