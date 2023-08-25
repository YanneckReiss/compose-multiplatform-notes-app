package core.injection

import com.myapplication.Database
import com.myapplication.Note
import data.db.DriverFactory
import data.db.NotesRepository
import data.db.createDatabase
import domain.usecases.CreateNoteUseCase
import domain.usecases.WatchNotesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.navigation.Navigator
import ui.notes.create.CreateNoteViewModel
import ui.notes.overview.NotesOverviewViewModel

val mainModule = module {
    single { createDatabase(DriverFactory.createDriver("notes.db")) }
    single { get<Database>().noteQueries }
    singleOf(::NotesRepository)
    factoryOf(::CreateNoteViewModel)
    factoryOf(::NotesOverviewViewModel)
    singleOf(::WatchNotesUseCase)
    singleOf(::CreateNoteUseCase)
    singleOf(::Navigator)
}
