package com.saitejajanjirala.mynote.presentation.add_edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saitejajanjirala.mynote.R
import com.saitejajanjirala.mynote.presentation.add_edit.AddEditNoteEvent
import com.saitejajanjirala.mynote.presentation.add_edit.AddEditNoteViewModel
import com.saitejajanjirala.mynote.presentation.add_edit.TransparentHintTextField
import com.saitejajanjirala.mynote.presentation.add_edit.UiEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController : NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
){

    val titleState = viewModel.noteTitle.value
    val descriptionState = viewModel.noteDescription.value
    val scope = rememberCoroutineScope()
    val scaffoldState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true){
        scope.launch {
            viewModel.eventFLow.collect{
                when(it){
                    UiEvent.SaveNote -> {
                        navController.navigateUp()
                    }
                    is UiEvent.ShowSnackBar -> {
                        scaffoldState.showSnackbar(it.msg)
                    }
                }

            }
        }
    }
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            }, modifier = Modifier.background(Color.Transparent)) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_save),
                    contentDescription = stringResource(id = R.string.menu_save)
                )
            }
        }
    ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            Column {
                TransparentHintTextField(
                    isHintVisible = titleState.isHintVisible,
                    onFocusChange = {
                                    viewModel.onEvent(AddEditNoteEvent.OnTitleFocusChanged(it))
                    },
                    onValueChange = {
                                    viewModel.onEvent(AddEditNoteEvent.OnTitleEntered(it))
                    },
                    hint = titleState.hint,
                    text = titleState.text
                )
                TransparentHintTextField(
                    isHintVisible = descriptionState.isHintVisible,
                    onFocusChange = {
                                    viewModel.onEvent(AddEditNoteEvent.OnDescriptionFocusChanged(it))
                    },
                    onValueChange = {
                                    viewModel.onEvent(AddEditNoteEvent.OnDescriptionEntered(it))
                    },
                    hint = descriptionState.hint,
                    text = descriptionState.text
                )
            }
        }
    }
}