package br.edu.ifsp.scl.sdm.pa1.cicloactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.sdm.pa1.cicloactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    companion object{
        val CICLO_ACTIVITY: String = "CICLO_ACTIVITY"
    }

    private lateinit var nomeEt: EditText
    private lateinit var sobrenomeEt: EditText
    private lateinit var editarActivityResultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = "Principal"


        nomeEt = EditText(this)
        nomeEt.width = LinearLayout.LayoutParams.MATCH_PARENT
        nomeEt.height = LinearLayout.LayoutParams.WRAP_CONTENT
        nomeEt.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        nomeEt.hint = "Nome"

        activityMainBinding.root.addView(nomeEt)

        sobrenomeEt = EditText(this)
        sobrenomeEt.width = LinearLayout.LayoutParams.MATCH_PARENT
        sobrenomeEt.height = LinearLayout.LayoutParams.WRAP_CONTENT
        sobrenomeEt.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        sobrenomeEt.hint = "Sobrenome"

        activityMainBinding.root.addView(sobrenomeEt)

        savedInstanceState?.getString("nome").takeIf { it != null }.apply {
            nomeEt.setText(this)
        }

        savedInstanceState?.getString("sobrenome").takeIf { it != null }.apply {
            sobrenomeEt.setText(this)
        }

        editarActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                /*val nome: String? = result?.data?.getStringExtra("nome").toString()
                            val sobrenome: String? = result?.data?.getStringExtra("sobrenome").toString()
                            nomeEt.setText(nome)
                            sobrenomeEt.setText(sobrenome)*/

                with(result) {
                    data?.getStringExtra("nome").takeIf { it != null }.let { nomeEt.setText(it) }
                    data?.getStringExtra("sobrenome").takeIf { it != null }
                        .run { sobrenomeEt.setText(this) }
                }
            }
        }

        Log.v(CICLO_ACTIVITY, "onCrete: Iniciando ciclo de vida completo")
    }

    override fun onStart() {
        super.onStart()
        Log.v(CICLO_ACTIVITY, "onStart: Iniciando ciclo de vida Visível")
    }

    override fun onResume() {
        super.onResume()
        Log.v(CICLO_ACTIVITY, "onResume: Iniciando ciclo de vida Primeiro Plano")
    }

    override fun onPause() {
        super.onPause()
        Log.v(CICLO_ACTIVITY, "onPause: Finalizando ciclo de vida Primeiro Plano")
    }

    override fun onStop() {
        super.onStop()
        Log.v(CICLO_ACTIVITY, "onStop: Finalizando ciclo de vida visível")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(CICLO_ACTIVITY, "onDestroy: Finalizando ciclo de vida Primeiro Plano")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(CICLO_ACTIVITY, "onRestart: Preparando execução do onStart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nome", nomeEt.text.toString())
        outState.putString("sobrenome", sobrenomeEt.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
     /*   val nome: String? = savedInstanceState.getString("nome")
        if (nome != null){
            nomeEt.setText(nome)
        }*/
        savedInstanceState.getString("nome").takeIf { it != null }.apply {
            nomeEt.setText(this)
        }
        savedInstanceState.getString("sobrenome").takeIf { it != null }.apply {
            sobrenomeEt.setText(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editarMi ->{
                val editarIntent: Intent = Intent(this, EditarActivity::class.java)
                editarActivityResultLauncher.launch(editarIntent)
                true
            }else ->{
                false
            }
        }
    }
}