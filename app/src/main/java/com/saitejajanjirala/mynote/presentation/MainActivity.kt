package com.saitejajanjirala.mynote.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saitejajanjirala.mynote.presentation.add_edit.component.AddEditNoteScreen
import com.saitejajanjirala.mynote.presentation.notes.component.NotesScreen
import com.saitejajanjirala.mynote.presentation.util.Screen
import com.saitejajanjirala.mynote.ui.theme.MynoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MynoteTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ){
                        composable(Screen.NotesScreen.route){
                            NotesScreen(navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route + "" +
                                    "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            AddEditNoteScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

