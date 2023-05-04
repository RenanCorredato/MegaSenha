package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import co.tiagoaguiar.ganheinamega.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("db",Context.MODE_PRIVATE)
       val result = prefs.getString("result",null)
        result?.let {
            binding.txtResult.text = "Ultimo aposta: $it"
        }

        binding.btnGenerate.setOnClickListener {
            val edtNumber = binding.edtNumber.text.toString()
            val result = binding.txtResult
            numberGenerator(
                text = edtNumber,
                txtResult = result
            )
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        //aqui e falha
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() // convertendo string para inteiro
        //aqui e falha
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e15", Toast.LENGTH_LONG).show()
            return
        }
        //qui e o sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random()
        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ") // colo um seprado ma string
//        val editor = prefs.edit()
//        editor.putString("result",txtResult.text.toString())
//        editor.apply()

        prefs.edit().apply{
            putString("result",txtResult.text.toString())
            apply()
        }
    }
}