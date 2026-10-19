package com.example.payrollcalculator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : AppCompatActivity() {

    //Runs when AboutActivity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Full screen
        enableEdgeToEdge()
        //Load interface from activity_about.xml
        setContentView(R.layout.activity_about)
        //Make sure content isnt hidden behind bars
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.aboutRoot)
        ) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        val toolbar =
            findViewById<Toolbar>(R.id.aboutToolbar)
        setSupportActionBar(toolbar)
        //Back arrow in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    //ActionBar back arrow
    override fun onSupportNavigateUp(): Boolean {
        //Closes AboutActivity.
        finish()
        return true
    }
}