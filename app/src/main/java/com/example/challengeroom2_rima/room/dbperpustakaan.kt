package com.example.challengeroom2_rima.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [tbBuku::class], version = 1)
abstract class dbperpustakaan : RoomDatabase(){

    abstract fun tbBukDAO(): tbBukuDAO

    companion object{

        @Volatile private var instance :dbperpustakaan? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also{
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            dbperpustakaan::class.java,
            "db.perpustakaan"
        ).build()
    }
}