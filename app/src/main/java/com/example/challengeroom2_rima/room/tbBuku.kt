package com.example.challengeroom2_rima.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbBuku(
    @PrimaryKey
    val id_buku: Int,
    val kategori: String,
    val judul: String,
    val pengarang: String,
    val penerbit: String,
    val jml_buku: Int
)