package br.edu.ifsp.scl.tradutorsdmkt

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.tradutorsdmkt.MainActivity.codigosMensagen.RESPOSTA_TRADUCAO
import br.edu.ifsp.scl.tradutorsdmkt.volley.Tradutor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    object codigosMensagen {
        val RESPOSTA_TRADUCAO = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tradutoHandler = TradutoHandler()

        val tradutor: Tradutor = Tradutor(this)
        tradutor.getLanguages()
    }

    val idiomas = listOf("pt", "en")
    // Handler da thread de UI
    lateinit var tradutoHandler: TradutoHandler
    inner class TradutoHandler: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == RESPOSTA_TRADUCAO) {
                // Preenche o spinner com o retorno do WS
                idiomasSp.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    msg.obj as MutableList<String>
                )
                idiomasSp.setSelection(0) //pt
            }
        }
    }

}

