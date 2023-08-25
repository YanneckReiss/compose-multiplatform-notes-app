package data.db

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.myapplication.Database
import com.myapplication.Note
import kotlinx.datetime.Instant

expect object DriverFactory {
    fun createDriver(databaseName: String): SqlDriver
}

val instantAdapter = object : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long) = Instant.fromEpochMilliseconds(databaseValue)
    override fun encode(value: Instant): Long = value.toEpochMilliseconds()
}

fun createDatabase(sqlDriver: SqlDriver): Database {
    return Database(
        driver = sqlDriver,
        noteAdapter = Note.Adapter(
            date_createdAdapter = instantAdapter,
        )
    )
}
