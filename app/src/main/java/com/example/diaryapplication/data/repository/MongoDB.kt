package com.example.diaryapplication.data.repository


import com.example.diaryapplication.model.Diary
import com.example.diaryapplication.util.Constants.APP_ID
import com.example.diaryapplication.util.RequestState
import com.example.diaryapplication.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId

object MongoDB : MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            //SyncConfiguration is Setup Our Realm Database
            //Data can be synchronized between devices using the Atlas device sync.
            val config = SyncConfiguration.Builder(user, setOf(Diary::class))
                .initialSubscriptions { sub ->
                    add(
                        query = sub.query<Diary>(query = "ownerId == $0", user.id),
                        name = "User's Diary"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }


    override fun getAllDiaries(): Flow<Diaries> {
        return if (user != null){
            try {
                realm.query<Diary>(query = "ownerId == $0", user.id)
                    .sort(property = "date", sortOrder = Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            data = result.list.groupBy {
                                it.date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                        )
                    }
            }catch (e: Exception){
                flow { emit(RequestState.Error(e)) }
            }
        }else{
            flow { emit(RequestState.Error(com.example.diaryapplication.data.repository.UserNotAuthenticatedException())) }
        }
    }
}


private class UserNotAuthenticatedException : Exception("User is not Log in")