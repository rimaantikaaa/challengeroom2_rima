 package com.example.challengeroom2_rima

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challengeroom2_rima.room.Constant
import com.example.challengeroom2_rima.room.dbperpustakaan
import com.example.challengeroom2_rima.room.tbBuku
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class EditActivity : AppCompatActivity() {

     val db by lazy { dbperpustakaan (this)}
     private var idBuku: Int = 0

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
         setupView()
         setupListener()

         idBuku = intent.getIntExtra("intent_id",0)
         Toast.makeText(this,idBuku.toString(),Toast.LENGTH_SHORT).show()

         supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

     fun setupView(){
         supportActionBar!!.setDisplayHomeAsUpEnabled(true)
         val intentType = intent.getIntExtra("intent_type",0)
         when (intentType){
             Constant.TYPE_CREATE -> {
                 btn_update.visibility = View.GONE
             }
             Constant.TYPE_READ -> {
                 btn_simpan.visibility = View.GONE
                 btn_update.visibility = View.GONE
                 ET_id.visibility = View.GONE
                 getbuku()
             }
             Constant.TYPE_UPDATE ->{
                 btn_simpan.visibility = View.GONE
                 getbuku()
             }
         }
     }

     fun setupListener(){
         btn_simpan.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch {
                 db.tbBukDAO().addtbBuku(
                     tbBuku(0,ET_judul.text.toString(),
                     ET_kategori.text.toString(),
                     ET_penerbit.text.toString(),
                     ET_pengarang.text.toString(),
                     ET_jmlBuku.text.toString().toInt())
                 )
                 finish()
             }
         }
     }
     fun getbuku(){
         idBuku = intent.getIntExtra("intent_id",0)
         CoroutineScope(Dispatchers.IO).launch {
             val buk = db.tbBukDAO().getbuku(idBuku)[0]
             val dataId: String = buk.id_buku.toString()
             val dataJml: String = buk.jml_buku.toString()

             ET_id.setText(dataId)
             ET_kategori.setText(buk.kategori)
             ET_judul.setText(buk.judul)
             ET_pengarang.setText(buk.pengarang)
             ET_penerbit.setText(buk.penerbit)
             ET_jmlBuku.setText(dataJml)
         }
     }
     override fun onSupportNavigateUp(): Boolean {
         onBackPressed()
         return super.onSupportNavigateUp()
     }
 }