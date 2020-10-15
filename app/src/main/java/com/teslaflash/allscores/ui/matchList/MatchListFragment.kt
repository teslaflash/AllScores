package com.teslaflash.allscores.ui.matchList

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
import com.teslaflash.allscores.EditMatchActivity
import com.teslaflash.allscores.EditPlayerActivity
import com.teslaflash.allscores.R
import com.teslaflash.allscores.database.ScoresMatch
import com.teslaflash.allscores.repository.ScoresViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class MatchListFragment : Fragment() {

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
        val adapter = MatchListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        scoresViewModel.getAllScoresMatch().observe(viewLifecycleOwner, Observer { t ->
            adapter.submitList(
                t
            )
        })

        adapter.setOnClickerListener(object : MatchListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, EditMatchActivity::class.java)
                intent.putExtra("requestCode", EDIT)
                intent.putExtra("id", adapter.currentList[position].id)
                intent.putExtra("title", adapter.currentList[position].title)
                intent.putExtra("date", adapter.currentList[position].date)
                intent.putExtra("price", adapter.currentList[position].price)
                intent.putExtra("playerCount", adapter.currentList[position].playerCount)
                intent.putExtra("isComplete", adapter.currentList[position].isComplete)
                startActivity(intent)
            }
        })

        adapter.setDeleteListener(object : MatchListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                scoresViewModel.delete(adapter.currentList[position])
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
            val intent = Intent(activity, EditMatchActivity::class.java)
            intent.putExtra("requestCode", NEW)
            startActivity(intent)
            return true
        } else return false

    }
}