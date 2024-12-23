package com.latihan.roomdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.latihan.roomdb.database.daftarBelanja
import com.latihan.roomdb.database.daftarBelanjaDB
import com.latihan.roomdb.database.historyBelanja
import com.latihan.roomdb.database.historyBelanjaDB
import com.latihan.roomdb.ui.theme.RoomDBTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var  DB : daftarBelanjaDB
    private lateinit var DB2 : historyBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        var _fabHis = findViewById<FloatingActionButton>(R.id.cekHistory)

        adapterDaftar = adapterDaftar(arDaftar)

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvNotes)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar


        adapterDaftar.setOnItemClickCallback(object: adapterDaftar.OnItemClickCallback{
            override fun delData(dtBelanja: daftarBelanja) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main){
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

            override fun doneData(dtBelanja: daftarBelanja) {
                Log.d("DEBUG","doneData Ke Trigger")
                CoroutineScope(Dispatchers.IO).async {
                    Log.d("DEBUG", "CoroutineScope Ke Trigger")
                    DB2.funhistoryBelanjaDAO().insert(
                        historyBelanja(
                            tanggal = dtBelanja.tanggal,
                            item = dtBelanja.item,
                            jumlah = dtBelanja.jumlah
                            )
                    )
                    Log.d("DEBUG","Insert Done")
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()

                    Log.d("DEBUG","Delete Done")

                    withContext(Dispatchers.Main){
                        adapterDaftar.isiData(daftar)
                    }
                }
            }
        })



        DB = daftarBelanjaDB.getDatabase(this)
        DB2 = historyBelanjaDB.getDatabase(this)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }
        _fabHis.setOnClickListener{
            startActivity(Intent(this, History::class.java))
        }

    }

    override fun onStart(){
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterDaftar.isiData(daftarBelanja)
        }

    }

}


