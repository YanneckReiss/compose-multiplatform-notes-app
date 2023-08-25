package data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.myapplication.Database
import core.appContext

actual object DriverFactory {

    actual fun createDriver(databaseName: String): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, appContext, databaseName)
    }
}
