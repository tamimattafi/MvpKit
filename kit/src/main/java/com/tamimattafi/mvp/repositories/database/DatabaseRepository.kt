package com.tamimattafi.mvp.repositories.database

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.Callback
import com.tamimattafi.mvp.MvpBaseContract.DatabaseRepository
import com.tamimattafi.mvp.repositories.database.async.ItemActionAsync
import com.tamimattafi.mvp.repositories.database.async.ItemActionAsync.Companion.DELETE
import com.tamimattafi.mvp.repositories.database.async.ItemActionAsync.Companion.INSERT
import com.tamimattafi.mvp.repositories.database.async.ItemActionAsync.Companion.UPDATE
import com.tamimattafi.mvp.repositories.database.async.ItemAsync
import com.tamimattafi.mvp.repositories.database.async.ListActionAsync
import com.tamimattafi.mvp.repositories.database.async.ListActionAsync.Companion.INSERT_ALL
import com.tamimattafi.mvp.repositories.database.async.ListAsync
import io.reactivex.Flowable


open class DatabaseRepository<T>(private val dao: BaseDao<T>) : BaseRepository(),
    DatabaseRepository<T> {

    override fun getRxList(query: SupportSQLiteQuery): Flowable<List<T>> = dao.getRxList(query)

    override fun getRxItem(query: SupportSQLiteQuery): Flowable<T> = dao.getRxItem(query)

    override fun getList(query: SupportSQLiteQuery): com.tamimattafi.mvp.core.Callback<ArrayList<T>> = createCallback {
        ListAsync(it, dao).execute(query)
    }

    override fun getItem(query: SupportSQLiteQuery): com.tamimattafi.mvp.core.Callback<T> = createCallback {
        ItemAsync(it, dao).execute(query)
    }

    override fun delete(item: T): com.tamimattafi.mvp.core.Callback<Int> = createCallback {
        ItemActionAsync(it, dao).execute(Pair(DELETE, item))
    }

    override fun update(item: T): com.tamimattafi.mvp.core.Callback<Int> = createCallback {
        ItemActionAsync(it, dao).execute(Pair(UPDATE, item))
    }

    override fun insert(item: T): com.tamimattafi.mvp.core.Callback<Long> = createCallback {
        ItemActionAsync(it, dao).execute(Pair(INSERT, item))
    }

    override fun insertAll(items: ArrayList<T>): com.tamimattafi.mvp.core.Callback<List<Long>> = createCallback {
        ListActionAsync(it, dao).execute(Pair(INSERT_ALL, items))
    }

}