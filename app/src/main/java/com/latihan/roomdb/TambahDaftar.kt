package com.latihan.roomdb

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import com.latihan.roomdb.database.daftarBelanja
import com.latihan.roomdb.database.daftarBelanjaDB
import com.latihan.roomdb.ui.theme.RoomDBTheme
import com.latihan.roomdb.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TambahDaftar : ComponentActivity() {
    var DB  = daftarBelanjaDB.getDatabase(this)
    var iID : Int = 0
    var iAddEdit : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)

        var _etItem = findViewById<EditText>(R.id.etItem)
        var _etJumlah = findViewById<EditText>(R.id.etJumlah)
        var _btnTambah = findViewById<Button>(R.id.btnTambah)
        var _btnUpdate = findViewById<Button>(R.id.btnUpdate)



        var tanggal = getCurrentDate()

        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit",0)

        if (iAddEdit == 0){
            _btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etItem.isEnabled = true
        }else{
            _btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iID)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }

        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString(),

                    )

                )

                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }
        _btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    pilihid = iID,
                    isi_status = 1
                )

                withContext(Dispatchers.Main){
                    finish()
                }
            }

        }


    }
}
