package com.example.a2in1app



import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_numbers_game.*
import kotlin.random.Random

class NumbersGameActivity : AppCompatActivity() {


   // private  lateinit var rvMessages:RecyclerView
    private lateinit var clRootNumbersGame: ConstraintLayout
    private lateinit var guessField: EditText
    private lateinit var guessButton: Button
    private lateinit var messages: ArrayList<String> // declaring variable

    private var answer = 0
    private var guesses = 3
    private var newGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_game)
        answer = Random.nextInt(10)

        clRootNumbersGame = findViewById(R.id.clRootNumbersGame)
        messages = ArrayList() //initialize variable

        rvMessages.adapter = MessageAdapter(this, messages)
        rvMessages.layoutManager = LinearLayoutManager(this)

        guessField = findViewById(R.id.etGuessField)
        guessButton = findViewById(R.id.btGuessButton)

        guessButton.setOnClickListener { addMessage() }

        title = "Numbers Game"
    }

    private fun addMessage(){
        val msg = guessField.text.toString()
        if(msg.isNotEmpty()){
            if(guesses>0){
                if(msg.toInt() == answer){
                    disableEntry()
                    CustomAlertDialog("You win!\n\nPlay again?")
                }else{
                    guesses--
                    messages.add("You guessed $msg")
                    messages.add("You have $guesses guesses left")
                }
                if(guesses==0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $answer")
                    messages.add("Game Over")
                    CustomAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            guessField.text.clear()
            guessField.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clRootNumbersGame, "Please enter a number", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun CustomAlertDialog( message: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(message)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }

    private fun disableEntry(){
        guessButton.isEnabled = false
        guessButton.isClickable = false
        guessField.isEnabled = false
        guessField.isClickable = false
    }

    override fun recreate() {
        super.recreate()
        answer = Random.nextInt(10)
        guesses = 3
        messages.clear()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_number, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numberItem_newGame -> {

            }
            R.id.numberItem_guessThePhrase -> {
                val intent = Intent(this, GuessThePhraseActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.numberItem_mainMenu -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}