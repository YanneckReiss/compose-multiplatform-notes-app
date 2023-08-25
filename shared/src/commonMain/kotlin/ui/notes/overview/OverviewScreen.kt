package ui.notes.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myapplication.Note
import core.koinViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ui.navigation.Navigator

@Composable
fun OverviewScreen(
    navigator: Navigator,
    viewModel: NotesOverviewViewModel = koinViewModel()
) {

    val notes: ImmutableList<Note> by viewModel.notes.collectAsState()

    OverviewContent(
        notes = notes,
        onNavToCreateNote = { navigator.navigateTo(Navigator.Routes.CREATE_NOTES) }
    )
}

@Composable
private fun OverviewContent(
    notes: ImmutableList<Note>,
    onNavToCreateNote: () -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavToCreateNote) {
                Icon(Icons.Default.Add, contentDescription = "Create Note")
            }
        }
    ) { innerPadding: PaddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            item(key = "header") {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Notes",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            if (notes.isNotEmpty()) {
                items(notes) { note ->
                    NoteItem(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        note = note
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text("No notes yet")
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteItem(
    modifier: Modifier,
    note: Note
) {

    val timeStamp: String by remember(note.date_created) {
        val timestampDate: LocalDate = note.date_created.toLocalDateTime(TimeZone.currentSystemDefault()).date
        mutableStateOf(timestampDate.toString())
    }

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min),
                contentAlignment = Alignment.TopStart,
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomEnd = 16.dp))
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.25f))
                )

                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        text = timeStamp,
                        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Text(
                modifier = Modifier.padding(8.dp),
                text = note.content,
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
            )
        }
    }
}
