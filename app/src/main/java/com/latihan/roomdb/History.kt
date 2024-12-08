package com.latihan.roomdb

import android.os.Bundle
import android.util.Log
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
import com.latihan.roomdb.database.daftarBelanja
import com.latihan.roomdb.database.historyBelanja
import com.latihan.roomdb.database.historyBelanjaDB
import com.latihan.roomdb.ui.theme.RoomDBTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class History : ComponentActivity() {
    private lateinit var DB2 : historyBelanjaDB
    private lateinit var adapterHistory: adapterHistory
    private var arDaftar : MutableList<historyBelanja> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        adapterHistory = adapterHistory(arDaftar)

        var _rvHistory = findViewById<RecyclerView>(R.id.rv_history)

        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory

        DB2 = historyBelanjaDB.getDatabase(this)



    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBelanja = DB2.funhistoryBelanjaDAO().selectAll()
            Log.d("data ROOM", historyBelanja.toString())
            adapterHistory.isiData(historyBelanja)
        }
    }
}

