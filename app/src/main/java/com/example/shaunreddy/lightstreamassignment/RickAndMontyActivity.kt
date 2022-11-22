package com.example.shaunreddy.lightstreamassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmontyActivityBinding

class RickAndMontyActivity : AppCompatActivity() {

    private lateinit var binding: RickandmontyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RickandmontyActivityBinding.inflate(layoutInflater)
        with(binding){
            setContentView(root)
            setSupportActionBar(rickandmortyToolbar)

            supportActionBar?.title = getString(R.string.tool_bar_title)
            RickAndMortyRootNavFragment().also {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.rickandmorty_root_fragment_container, it)
                    .setPrimaryNavigationFragment(it)
                    .commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}