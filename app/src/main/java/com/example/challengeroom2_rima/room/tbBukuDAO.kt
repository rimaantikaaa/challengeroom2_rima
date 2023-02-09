package com.example.challengeroom2_rima.room

import androidx.room.*

@Dao
interface tbBukuDAO {
    @Insert
    fun addtbBuku(buku: tbBuku)

    @Update
    fun UpdatetbBuku(buku: tbBuku)

    @Delete
    fun DeletetbBuku(buku: tbBuku)

    @Query("SELECT * FROM tbBuku")
    fun tampilBuku():List<tbBuku>

    @Query("SELECT*FROM tbBuku WHERE id_buku=:id_buku")
    fun getbuku(id_buku: Int) : List<tbBuku>
}