package com.frankmorara.mvvmshoppinglist.ui.shoppinglist

import com.frankmorara.mvvmshoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}