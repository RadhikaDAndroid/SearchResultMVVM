package com.radhika.code.fidelity.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.radhika.code.fidelity.R
import com.radhika.code.fidelity.data.api.ApiHelper
import com.radhika.code.fidelity.data.api.ApiServiceImpl
import com.radhika.code.fidelity.data.model.SearchResult
import com.radhika.code.fidelity.ui.base.ViewModelFactory
import com.radhika.code.fidelity.ui.main.adapter.MainAdapter
import com.radhika.code.fidelity.ui.main.viewmodel.MainViewModel
import com.radhika.code.fidelity.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_edittext_view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        search_edit_text.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT
            ) {
                search_edit_text.clearFocus()
                return@setOnEditorActionListener true
            } else {
                search_edit_text.text = null
                return@setOnEditorActionListener false
            }
        }
        search_edit_text.addTextChangedListener(watcher)
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                when (it.length >= 3) {
                    true -> {
                        search_edit_text.imeOptions = EditorInfo.IME_ACTION_SEARCH
                        setupViewModel()
                        mainViewModel.fetchSearchResults(it.toString())
                        setupObserver()
                    }
                    false -> {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.minimum_length_searchtext_message,
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun setupObserver() {

        mainViewModel.getSearchData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { searchResults -> renderList(searchResults.results) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun renderList(searchResultList: List<SearchResult>) {
        adapter.addData(searchResultList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}
