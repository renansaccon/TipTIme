package saccon.renan.br.tiptime

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import saccon.renan.br.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding  //incluído no build.gradle.kts app


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate( layoutInflater ) //layoutInflater engloba os itens da tela, como se inflasse por toda a a tela.

        setContentView( binding.root ) // Não é necessário mais utilizar o R.layout.activity_main

        binding.calculateButton.setOnClickListener{
            calculateButtonOnClick()
        }

        if (savedInstanceState != null) {
            val tip = savedInstanceState.getString("tip")
            binding.tipResult.text = tip
        } else{
            binding.tipResult.text = getString( R.string.tip_amount_s, "-" )
        }

    }

    private fun calculateButtonOnClick() {

        val stringInTextField = binding.costOfService.text.toString()

        if ( stringInTextField.isEmpty() ) {
            Toast.makeText(this,"Insert the cost",Toast.LENGTH_LONG).show()
            return
        }

        val cost = stringInTextField.toDouble()


     //   val cost = stringInTextField.toDoubleOrNull() ?: return //Também utilizado para o app não dar crash caso o campo seja vazio.

        val selectedId = binding.tipOption.checkedRadioButtonId

        val tipPercentage = when ( selectedId ){
            R.id.option_twenty_percent-> 0.20
            R.id.opton_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage

        val roundUp = binding.roundUpSwitch.isChecked

        if ( roundUp ) {
            tip = kotlin.math.ceil( tip ) //Biblioteca Kotlin no qual arredenda o valor de tip para cima
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format( tip )  //Formata a variável tip para monetário de acordo com o idioma do aparelho do usuário
        binding.tipResult.text = getString( R.string.tip_amount_s, formattedTip) //Puxa novamente o valor da string tip_amount_s e substituirá o %s pelo tip.toString() (Quando não formatado)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("tip", binding.tipResult.text.toString())
    }

}