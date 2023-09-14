package com.example.diaryapplication.data.repository


import com.example.diaryapplication.model.Diary
import com.example.diaryapplication.util.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>
interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries() : Flow<Diaries>
}

//[Service][AuthError(4346)] expected field 'picture' to be in token metadata. Server log entry: https://realm.mongodb.com/groups/64fe63d6bbf999426553832c/apps/64fe68bb746e2f645ef21cdb/logs?co_id=65029bf2174ff42593009d67
//[Service][AuthError(4346)] expected field 'picture' to be in token metadata. Server log entry: https://realm.mongodb.com/groups/64fe63d6bbf999426553832c/apps/64fe68bb746e2f645ef21cdb/logs?co_id=65029c62174ff4259300d374