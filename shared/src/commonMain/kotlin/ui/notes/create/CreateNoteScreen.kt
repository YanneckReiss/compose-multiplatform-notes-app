package ui.notes.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.koinViewModel
import ui.navigation.Navigator
import ui.platform.BackHandler

@Composable
fun CreateNoteScreen(
    navigator: Navigator,
    viewModel: CreateNoteViewModel = koinViewModel()
) {

    val state: CreateNoteState by viewModel.state.collectAsState()

    BackHandler {
        navigator.navigateBack()
    }

    LaunchedEffect(state.onNoteCreated) {
        if (state.onNoteCreated) {
            viewModel.onNoteCreatedConsumed()
            navigator.navigateBack()
        }
    }

    CreateNoteContent(
        noteText = state.noteText,
        onNoteTextUpdated = viewModel::onNoteTextUpdated,
        onCreateNote = { viewModel.createNote() },
        onNavBack = { navigator.navigateBack() }
    )
}

@Composable
private fun CreateNoteContent(
    noteText: String,
    onNoteTextUpdated: (String) -> Unit,
    onCreateNote: () -> Unit,
    onNavBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.secondary,
                title = { Text("Create Note") },
                navigationIcon = {
                    IconButton(
                        onClick = onNavBack
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding: PaddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "What's in your mind?",
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        value = noteText,
                        onValueChange = onNoteTextUpdated,
                        label = { Text("Start typing..") }
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateNote,
                enabled = noteText.isNotBlank()
            ) {
                Icon(Icons.Default.Create, contentDescription = "Save")
                Text("Save")
            }
        }
    }
}
