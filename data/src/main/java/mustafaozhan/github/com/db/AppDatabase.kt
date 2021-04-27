package mustafaozhan.github.com.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mustafaozhan.github.com.model.Exercise

@Suppress("MagicNumber")
@Database(entities = [(Exercise::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "application_database.sqlite"

        internal fun createAppDatabase(
            context: Context
        ): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    abstract fun exerciseDao(): ExerciseDao
}
