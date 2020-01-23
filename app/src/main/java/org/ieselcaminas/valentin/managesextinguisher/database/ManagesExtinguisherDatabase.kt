package org.ieselcaminas.valentin.managesextinguisher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.Building.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.Flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.FloorDao

@Database(entities = [Extinguisher::class], version = 1, exportSchema = false)
abstract class ManagesExtinguisherDatabase : RoomDatabase() {

    abstract val extinguisherDao: ExtinguisherDao
    abstract val flaskDao: FlaskDao
    abstract val buildingDao: BuildingDao
    abstract val floorDao: FloorDao

    companion object {

        @Volatile
        private var INSTANCE: ManagesExtinguisherDatabase? = null

        fun getInstance(context: Context) : ManagesExtinguisherDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ManagesExtinguisherDatabase::class.java,
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