package com.example.challengeroom2_rima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom2_rima.room.Constant
import com.example.challengeroom2_rima.room.dbperpustakaan
import com.example.challengeroom2_rima.room.tbBuku
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { dbperpustakaan (this) }
    lateinit var perpusAdapter: perpus_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.tbBukDAO().tampilBuku()
            Log.d("MainActivity","dbperpustakaan: $buku")
            withContext(Dispatchers.Main) {
                perpusAdapter.setData(buku)
            }
        }
    }

    fun setupListener(){
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(id_buku: Int,intentType: Int){
        val pindah = Intent(applicationContext,EditActivity::class.java)
        startActivity(pindah
            .putExtra("intent_id", id_buku)
            .putExtra("intent_type",intentType)
        )
    }

    private fun setupRecyclerView(){
        perpusAdapter = perpus_adapter(arrayListOf(), object : perpus_adapter.onAdapterListener{
            override fun onClick(buku: tbBuku) {
                Toast.makeText(applicationContext, buku.kategori,Toast.LENGTH_SHORT).show()
                //read detail buku
                intentEdit(buku.id_buku,Constant.TYPE_READ)
            }

            override fun onUpdate(buku: tbBuku) {
                intentEdit(buku.id_buku,Constant.TYPE_UPDATE)
            }

            override fun onDelete(buku: tbBuku) {
                deleteDialog(buku)
            }
        })
        inputData.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = perpusAdapter
        }
    }

    private fun deleteDialog(buku: tbBuku) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${buku.kategori}?")
            setNegativeButton("Batal"){dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBukDAO().DeletetbBuku(buku)
                }
            }
        }
        alertDialog.show()
    }
}
