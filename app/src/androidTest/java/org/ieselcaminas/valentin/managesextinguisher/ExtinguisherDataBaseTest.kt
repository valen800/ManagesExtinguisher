package org.ieselcaminas.valentin.managesextinguisher

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.ieselcaminas.valentin.managesextinguisher.database.ExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.ExtinguisherDatabaseDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var extDadp: ExtinguisherDatabaseDao
    private lateinit var db: ExtinguisherDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ExtinguisherDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        extDadp = db.ExtinguisherDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        //val night = SleepNight()
        //sleepDao.insert(night)
        //val tonight = sleepDao.getTonight()
        //assertEquals(tonight?.sleepQuality, -1)
    }
}