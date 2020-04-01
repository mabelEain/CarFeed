package com.android.mabel.UI

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mabel.adapter.FeedListAdapter
import com.android.mabel.R
import com.android.mabel.database.AppDatabase
import com.android.mabel.database.FeedDao
import com.android.mabel.model.FeedModel
import com.android.mabel.viewmodel.ListViewModel

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ListViewModel
    private val carsAdapter = FeedListAdapter(arrayListOf())
    lateinit var db: FeedDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        db = AppDatabase.getInstance(this)?.feedDao()!!


        feedsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = carsAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.articles.observe(this, Observer { feeds ->
            feeds?.let {
                feedsList.visibility = View.VISIBLE
                carsAdapter.updateFeed(it)
                db.insertAllFeeds(it)
            }
        })
        viewModel.feedLoadError.observe(this, Observer { isError ->
            isError?.let {
                if(it){
                    if(db.gelAllFeeds().isEmpty()){
                        list_error.text = "Need to load data for the first time"
                        list_error.visibility = View.VISIBLE
                    }else{
                        list_error.visibility = View.GONE
                        var listFromdb : List<FeedModel> = db.gelAllFeeds()
                        Collections.reverse(listFromdb)
                        viewModel.articles.value = listFromdb
                    }
                }

            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    feedsList.visibility = View.GONE
                }
            }
        })
    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false
            )
            if (notConnected) {
                viewModel.refresh()
                observeViewModel()
            } else {
                viewModel.refresh()
                observeViewModel()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
    }
}
