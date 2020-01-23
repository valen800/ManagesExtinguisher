package org.ieselcaminas.valentin.managesextinguisher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.ExtinguisherDao

@Database(entities = [Extinguisher::class], version = 1, exportSchema = false)
abstract class ExtinguisherDatabase : RoomDatabase() {

    abstract val ExtinguisherDao: ExtinguisherDao

    companion object {

        @Volatile
        private var INSTANCE: ExtinguisherDatabase? = null

        fun getInstance(context: Context) : ExtinguisherDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExtinguisherDatabase::class.java,
                        "extinguisher_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}