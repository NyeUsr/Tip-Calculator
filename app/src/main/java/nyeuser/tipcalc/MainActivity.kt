package nyeuser.tipcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextBillAmount: EditText
    private lateinit var sliderTipPercentage: Slider
    private lateinit var textViewTipPercentage: TextView
    private lateinit var textViewTipAmount: TextView
    private lateinit var textViewTotalAmount: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextBillAmount = findViewById(R.id.editTextBillAmount)
        sliderTipPercentage = findViewById(R.id.sliderTipPercentage)
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage)
        textViewTipAmount = findViewById(R.id.textViewTipAmount)
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount)
        val tenPercentBtn = findViewById<Button>(R.id.buttonTenPercent)
        val twentyPercentBtn = findViewById<Button>(R.id.buttonTwentyPercent)

        sliderTipPercentage.value = 15f
        textViewTipPercentage.text = "${getString(R.string.tip_percentage)} 15%"

        sliderTipPercentage.addOnChangeListener { _, value, _ ->
            textViewTipPercentage.text = "${getString(R.string.tip_percentage)} ${value.toInt()}%"
            updateTipAndTotal()
        }

        editTextBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                updateTipAndTotal()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // update tip and total amounts
                updateTipAndTotal()
            }

            override fun afterTextChanged(s: Editable?) {
                updateTipAndTotal()
            }
        })
        tenPercentBtn.setOnClickListener {
            sliderTipPercentage.value = 10f
            textViewTipPercentage.text = "${getString(R.string.tip_percentage)} 10%"
        }

        twentyPercentBtn.setOnClickListener {
            sliderTipPercentage.value = 20f
            textViewTipPercentage.text = "${getString(R.string.tip_percentage)} 20%"
        }
    }

    private fun updateTipAndTotal() {
        val billAmount = editTextBillAmount.text.toString().toDoubleOrNull() ?: 0.0
        val tipPercentage = sliderTipPercentage.value.toInt()
        val tipAmount = billAmount * tipPercentage / 100.0
        val totalAmount = billAmount + tipAmount

        val formattedTipAmount = NumberFormat.getCurrencyInstance().format(tipAmount)
        val formattedTotalAmount = NumberFormat.getCurrencyInstance().format(totalAmount)

        textViewTipAmount.text = getString(R.string.tip_amount, formattedTipAmount)
        textViewTotalAmount.text = getString(R.string.total_amount, formattedTotalAmount)
    }
}