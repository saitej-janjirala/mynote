package com.saitejajanjirala.mynote.presentation.notes.component

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saitejajanjirala.mynote.presentation.MainViewModel
import com.saitejajanjirala.mynote.presentation.notes.NoteItem
import com.saitejajanjirala.mynote.presentation.notes.NotesEvent
import com.saitejajanjirala.mynote.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel : MainViewModel = hiltViewModel()
) {

    val state = viewModel.notesState.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember{
        SnackbarHostState()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick =  {
                navController.navigate(Screen.AddEditNoteScreen.route)
            }
            , modifier = Modifier.background(Color.Transparent)){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your note",
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSelection)
                }) {

                    Icon(
                        painter = painterResource(R.drawable.ic_menu_sort_by_size),
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSelectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(onOrderChange = {
                    viewModel.onEvent(NotesEvent.OrderEvent(it))
                }, noteOrder = state.noteOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
               items(state.notes){note->
                   NoteItem(
                       note = note,
                       onDeleteClick = {
                           viewModel.onEvent(NotesEvent.DeleteNote(note))
                           scope.launch {
                               val result = snackBarHostState.showSnackbar(
                                   message = "Note Deleted",
                                   actionLabel = "Undo"
                               )
                               if (result == SnackbarResult.ActionPerformed) {
                                   viewModel.onEvent(NotesEvent.RestoreNoteEvent)
                               }
                           }
                       },
                       modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                       .clip(RoundedCornerShape(8.dp)).clickable {
                       navController.navigate(
                           Screen.AddEditNoteScreen.route + "?noteId=${note.id}"
                       )
                   }

                   )
                   Spacer(modifier = Modifier.height(16.dp))
               }
            }
        }
    }
}