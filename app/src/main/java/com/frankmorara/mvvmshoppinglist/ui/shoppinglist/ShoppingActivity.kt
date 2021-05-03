package com.frankmorara.mvvmshoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.frankmorara.mvvmshoppinglist.data.db.ShoppingDatabase
import com.frankmorara.mvvmshoppinglist.data.db.entities.ShoppingItem
import com.frankmorara.mvvmshoppinglist.data.repos.ShoppingRepository
import com.frankmorara.mvvmshoppinglist.other.ShoppingItemAdapter
import com.frankmorara.mvvmshoppinglist.ui.shoppinglist.AddDialogListener
import com.frankmorara.mvvmshoppinglist.ui.shoppinglist.AddShoppingItemDialog
import com.frankmorara.mvvmshoppinglist.ui.shoppinglist.ShoppingViewModel
import com.frankmorara.mvvmshoppinglist.ui.shoppinglist.ShoppingViewModelFactory
import kotlinx.android.synthetic.main.activity_shopping.*
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database as ShoppingDatabase)
        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProvider(this,factory)
                .get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(),viewModel)
        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
        fab.setOnClickListener {
            AddShoppingItemDialog(this,
            object : AddDialogListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)

                }
            }).show()
        }
    }
}