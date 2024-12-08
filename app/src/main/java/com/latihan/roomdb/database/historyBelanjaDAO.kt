package com.latihan.roomdb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface historyBelanjaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBelanja)

    @Query("UPDATE historyBelanja SET tanggal=:isi_tanggal, item=:isi_item, jumlah=:isi_jumlah, status=:isi_status WHERE id=:pilihid")
    fun update(isi_tanggal:String, isi_item:String, isi_jumlah:String, isi_status: Int, pilihid: Int)

    @Delete
    fun delete(history: historyBelanja)

    @Query("SELECT * FROM historyBelanja ORDER BY id asc")
    fun selectAll(): MutableList<historyBelanja>

    @Query("SELECT * FROM historyBelanja WHERE id =:isi_id")
    suspend fun getItem(isi_id : Int) : historyBelanja


}