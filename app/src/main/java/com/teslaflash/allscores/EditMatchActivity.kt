package com.teslaflash.allscores

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.teslaflash.allscores.database.ScoresMatch
import com.teslaflash.allscores.database.ScoresPlayer
import com.teslaflash.allscores.repository.ScoresViewModel
import com.teslaflash.allscores.ui.playerList.PlayerListAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class EditMatchActivity : AppCompatActivity() {



    lateinit var scoresViewModel:ScoresViewModel
    lateinit var title:TextInputEditText
    lateinit var date:TextInputEditText
    lateinit var price:TextInputEditText
    lateinit var playerCount: TextView
    lateinit var fab: FloatingActionButton
    lateinit var reduceFuckUp: ImageButton
    var id:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_match)


        scoresViewModel = ViewModelProvider(this).get(ScoresViewModel::class.java)

        title = findViewById(R.id.titleET)
        date = findViewById(R.id.dateET)
        price = findViewById(R.id.priceET)
        playerCount = findViewById(R.id.playerCountTV)
        fab = findViewById(R.id.fab)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = PlayerListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        fab.setOnClickListener(View.OnClickListener {
            adapter.submitList(listOf(ScoresPlayer(id = 0, name = "rock"),ScoresPlayer(id = 1, name = "rocker")))
            playerCount.setText(adapter.currentList.size.toString())
        })

        val _intent = intent
        val requestCode: Int = intent.getIntExtra("requestCode", -1)

        when (requestCode) {
            -1 -> Toast.makeText(this,"Error request code",Toast.LENGTH_SHORT).show()
            0 -> {

                id = _intent.getIntExtra("id", 0)
                title.setText(_intent.getStringExtra("title"))
                date.setText(_intent.getStringExtra("date"))
                price.setText(_intent.getIntExtra("price",0).toString())
//                playerCount.setText(_intent.getIntExtra("playerCount",0))

            }
            1 -> {
                title.setText("")
                date.setText("")
                price.setText("0")
                playerCount.text = "0"
            }
        }



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.accept_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_accept) {

            var _title = title.text.toString()
            var _date = date.text.toString()
            var _price: Int = price.text.toString().toInt()
            var _playerCount = playerCount.text.toString()

            if (_title.isNullOrBlank() || _date.isNullOrBlank()) {
                Toast.makeText(this,"Please insert Player info",Toast.LENGTH_SHORT).show()
            } else {
                scoresViewModel.insert(
                    ScoresMatch(
                        id = id,
                        title = _title,
                        date = _date,
                        price = _price
                    )
                )
                finish()
            }


            return true
        } else return false
    }

}