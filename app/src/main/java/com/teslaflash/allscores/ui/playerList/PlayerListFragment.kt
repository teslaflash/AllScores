package com.teslaflash.allscores.ui.playerList

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.teslaflash.allscores.EditPlayerActivity
import com.teslaflash.allscores.R
import com.teslaflash.allscores.database.ScoresMatch
import com.teslaflash.allscores.database.ScoresPlayer
import com.teslaflash.allscores.repository.ScoresViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class PlayerListFragment : Fragment() {

    open val EDIT: Int = 0
    open val NEW: Int = 1

    private lateinit var scoresViewModel: ScoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scoresViewModel = ViewModelProvider(this).get(ScoresViewModel::class.java)
        val v = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)

        val recyclerView: RecyclerView = v.recyclerView
        val adapter = PlayerListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        scoresViewModel.getAllScorePlayer().observe(viewLifecycleOwner, Observer { t ->
            adapter.submitList(t)
        })

        adapter.setOnClickerListener(object : PlayerListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, EditPlayerActivity::class.java)
                intent.putExtra("requestCode", EDIT)
                intent.putExtra("id", adapter.currentList[position].id)
                intent.putExtra("name", adapter.currentList[position].name)
                intent.putExtra("balance", adapter.currentList[position].balance)
                intent.putExtra("phone", adapter.currentList[position].phone)
                intent.putExtra("games", adapter.currentList[position].games)
                intent.putExtra("fuckUps", adapter.currentList[position].fuckUps)
                startActivity(intent)
            }
        })

        adapter.setDeleteListener(object : PlayerListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                scoresViewModel.delete(adapter.currentList[position])
            }
        })

        adapter.setOnIncreaseListener(object : PlayerListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                var item = adapter.currentList[position]
                item.balance = item.balance + 100
                scoresViewModel.update(item)
                adapter.notifyDataSetChanged()
            }
        })

        adapter.setOnDecreaseListener(object : PlayerListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                var item = adapter.currentList[position]
                item.balance = item.balance - 100
                scoresViewModel.update(item)
                adapter.notifyDataSetChanged()
            }
        })

        return v
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_add) {
            val intent = Intent(activity, EditPlayerActivity::class.java)
            intent.putExtra("requestCode", NEW)
            startActivity(intent)
            return true
        } else return false
    }

    
}