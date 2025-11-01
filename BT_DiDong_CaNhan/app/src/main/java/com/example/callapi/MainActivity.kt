package com.example.callapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callapi.adapter.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupObservers()

        // Gọi API khi activity được tạo
        viewModel.loadProducts()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        recyclerViewProducts.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.products.collectLatest { products ->
                adapter.submitList(products)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                // Hiển thị/ẩn progress bar
                progressBar.isVisible = isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { errorMessage ->
                errorMessage?.let {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
