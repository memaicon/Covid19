package br.com.tecdev.covid19.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.tecdev.covid19.model.TotalResponse

@Dao
interface TotalCasesDao {

    @Query("SELECT * FROM TotalResponse LIMIT 1")
    fun getTotalCases(): TotalResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(totalResponse: TotalResponse)

    @Query("DELETE FROM TotalResponse")
    fun deleteAll()
}