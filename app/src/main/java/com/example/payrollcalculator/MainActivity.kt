package com.example.payrollcalculator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    //Runs when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Full screen
        enableEdgeToEdge()
        //Load the layout from activity_main.xml
        setContentView(R.layout.activity_main)
        //Make sure system bars don't cover application
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
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
        //Input fields
        val hoursInput =
            findViewById<EditText>(R.id.editHours)
        val rateInput =
            findViewById<EditText>(R.id.editRate)
        val taxInput =
            findViewById<EditText>(R.id.editTaxRate)
        //Output fields
        val payText =
            findViewById<TextView>(R.id.textPay)
        val overtimeText =
            findViewById<TextView>(R.id.textOvertime)
        val totalText =
            findViewById<TextView>(R.id.textTotal)
        val taxText =
            findViewById<TextView>(R.id.textTax)
        //Calculate button
        val calculateButton =
            findViewById<Button>(R.id.buttonCalculate)
        //setOnClickListener executes this when calculate button is clicked
        calculateButton.setOnClickListener {
            //Read user input
            val hours = hoursInput.text.toString()
                .toDoubleOrNull()
            val rate = rateInput.text.toString()
                .toDoubleOrNull()
            val taxRate = taxInput.text.toString()
                .toDoubleOrNull()
            //Validate user input
            if (hours == null ||
                !hours.isFinite() ||
                hours < 0
            ) {
                hoursInput.error = "Enter valid hours"
                hoursInput.requestFocus()
                return@setOnClickListener
            }
            //Make sure rate isn't negative
            if (rate == null ||
                !rate.isFinite() ||
                rate < 0
            ) {
                rateInput.error = "Enter a valid hourly rate"
                rateInput.requestFocus()
                return@setOnClickListener
            }
            //Tax rate must be between 0 and 1
            if (taxRate == null ||
                !taxRate.isFinite() ||
                taxRate < 0 ||
                taxRate > 1
            ) {
                taxInput.error = "Enter a tax rate from 0 to 1"
                taxInput.requestFocus()
                return@setOnClickListener
            }
            //Calculate regular and overtime pay
            val pay: Double
            val overtimePay: Double
            //If employee worked 40 hours or less, then normal pay
            if (hours <= 40) {
                pay = hours * rate
                overtimePay = 0.0
            } else { //If employee worked more than 40 hours, then overtime pay for additional hours
                pay = 40 * rate
                overtimePay = (hours - 40) * rate * 1.5
            }
            //Calculate tax and total pay
            val totalPay = pay + overtimePay
            val tax = pay * taxRate
            //Formatting
            val currency = NumberFormat.getCurrencyInstance(
                Locale.CANADA
            )
            //Display
            payText.text =
                "Pay: ${currency.format(pay)}"
            overtimeText.text =
                "Overtime Pay: ${currency.format(overtimePay)}"
            totalText.text =
                "Total Pay: ${currency.format(totalPay)}"
            taxText.text =
                "Tax: ${currency.format(tax)}"
        }
        //About button
        val aboutButton =
            findViewById<Button>(R.id.buttonAbout)
        //Run when user clicks about button
        aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
