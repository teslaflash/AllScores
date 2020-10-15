package com.teslaflash.allscores

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.teslaflash.allscores.database.ScoresPlayer
import com.teslaflash.allscores.repository.ScoresViewModel
import com.teslaflash.allscores.ui.playerList.PlayerListFragment
import kotlinx.android.synthetic.main.activity_edit_player.*

class EditPlayerActivity : AppCompatActivity() {



    lateinit var scoresViewModel:ScoresViewModel
    lateinit var name:TextInputEditText
    lateinit var balance:TextInputEditText
    lateinit var phone:TextInputEditText
    lateinit var fuckUps: TextView
    lateinit var addFuckUp: ImageButton
    lateinit var reduceFuckUp: ImageButton
    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_player)


        scoresViewModel = ViewModelProvider(this).get(ScoresViewModel::class.java)

        name = findViewById(R.id.nameET)
        balance = findViewById(R.id.balanceET)
        phone = findViewById(R.id.phoneET)
        fuckUps = findViewById(R.id.fuckUpsTV)
        addFuckUp = findViewById(R.id.addFuckUpBT)
        reduceFuckUp = findViewById(R.id.reduceFuckUpBT)

        addFuckUp.setOnClickListener(View.OnClickListener {
            val newFuckUpValue = fuckUps.text.toString().toInt() + 1
            fuckUps.text = newFuckUpValue.toString()
        })

        reduceFuckUp.setOnClickListener(View.OnClickListener {
            val newFuckUpValue = fuckUps.text.toString().toInt() - 1
            fuckUps.text = newFuckUpValue.toString()
        })

        val _intent = intent
        val requestCode: Int = intent.getIntExtra("requestCode", -1)

        when (requestCode) {
                -1 -> Toast.makeText(this,"Error request code",Toast.LENGTH_SHORT).show()
                0 -> {
                    var _phone = _intent.getIntExtra("phone",0).toString()
                    var _games = _intent.getIntExtra("games",0)
                    var _fuckUps = _intent.getIntExtra("fuckUps",0)

                    var crm: Double = _games.toDouble() / _fuckUps.toDouble()
                    id = _intent.getIntExtra("id", 0)
                    name.setText(_intent.getStringExtra("name"))
                    balance.setText(_intent.getIntExtra("balance", 0).toString())
                    if (_phone == "0" || _phone.isNullOrBlank()) {phone.setText("")} else {phone.setText(_phone)}
                    fuckUps.text = _fuckUps.toString()

                    setTitle("${_intent.getStringExtra("name")} #${_intent.getIntExtra("id", 0)}, GP: ${_games}, CRM: ${0}")

                }
                1 -> {

                    balance.setText("0")
                    phone.setText("")
                    fuckUps.text = "0"
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

            var _name = name.text.toString()
            var _balance = balance.text.toString()
            var _phone: String? = phone?.text?.toString()
            var _fuckUps = fuckUps.text.toString()

            if (_name.isNullOrBlank() || _balance.isNullOrBlank()) {
                Toast.makeText(this,"Please insert Player info",Toast.LENGTH_SHORT).show()
            } else {
                scoresViewModel.insert(
                    ScoresPlayer(
                        id = id,
                        name = _name,
                        balance = _balance.toInt(),
                        phone = _phone,
                        fuckUps = _fuckUps.toInt()
                    )
                )
                finish()
            }


            return true
        } else return false
    }

}