package br.edu.ifsp.scl.tradutorsdmkt.volley

import br.edu.ifsp.scl.tradutorsdmkt.Constantes.APP_ID_FIELD
import br.edu.ifsp.scl.tradutorsdmkt.Constantes.APP_ID_VALUE
import br.edu.ifsp.scl.tradutorsdmkt.Constantes.APP_KEY_FIELD
import br.edu.ifsp.scl.tradutorsdmkt.Constantes.APP_KEY_VALUE
import br.edu.ifsp.scl.tradutorsdmkt.Constantes.END_POINT
import br.edu.ifsp.scl.tradutorsdmkt.Constantes.URL_BASE
import br.edu.ifsp.scl.tradutorsdmkt.MainActivity
import br.edu.ifsp.scl.tradutorsdmkt.MainActivity.codigosMensagen.RESPOSTA_TRADUCAO
import br.edu.ifsp.scl.tradutorsdmkt.model.LanguagesResponse
import br.edu.ifsp.scl.tradutorsdmkt.model.Translation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.snackbar
import org.json.JSONException
import org.json.JSONObject


class Tradutor(val mainActivity: MainActivity) {
// As funções abaixo devem ser adicionadas aqui

    fun getLanguages() {
        // Monta uma String com uma URL a partir das constantes e parâmetros do usuário
        val urlSb = StringBuilder(URL_BASE)
        urlSb.append("languages")
        val url = urlSb.toString()
        // Cria uma fila de requisições Volley para enviar a requisição
        val filaRequisicaoTraducao: RequestQueue = Volley.newRequestQueue(mainActivity)
        // Monta a requisição que será colocada na fila. Esse objeto é uma instância de uma classe anônima
        var traducaoJORequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET, // Método HTTP de requisição
                url, // URL
                null, // Objeto de requisição - somente em POST
                LanguagesListener(), // Listener para tratar resposta
                ErroListener() // Listener para tratar erro
            ) {
                // Corpo do objeto
                // Sobreescrevendo a função para passar cabeçalho na requisição
                override fun getHeaders(): MutableMap<String, String> {
                    // Cabeçalho composto por Map com app_id, app_key e seus valores
                    var parametros: MutableMap<String, String> = mutableMapOf()
                    parametros.put(APP_ID_FIELD, APP_ID_VALUE)
                    parametros.put(APP_KEY_FIELD, APP_KEY_VALUE)
                    return parametros
                }
            }
        // Adiciona a requisição a fila
        filaRequisicaoTraducao.add(traducaoJORequest)
    }

    /* Trata a resposta de uma requisição quando o acesso ao WS foi realizado. Complexidade de O(N^5).
    Pode causar problemas de desempenho com respostas muito grandes */
    /*inner class RespostaListener : Response.Listener<JSONObject> {
        override fun onResponse(response: JSONObject?) {
            try {
                // Cria um objeto Gson que consegue fazer reflexão de um Json para Data Class
                val gson: Gson = Gson()
                // Reflete a resposta (que é um Json) num objeto da classe Resposta
                val resposta: Resposta = gson.fromJson(response.toString(), Resposta::class.java)
                // StringBuffer para armazenar o resultado das traduções
                var traduzidoSb = StringBuffer()
                // Parseando o objeto e adicionando as traduções ao StringBuffer, O(N^5)
                resposta.results?.forEach {
                    it?.lexicalEntries?.forEach {
                        it?.entries?.forEach {
                            it?.senses?.forEach {
                                it?.translations?.forEach {
                                    traduzidoSb.append("${it?.text}, ")
                                }
                            }
                        }
                    }
                }
                // Enviando as tradução ao Handler da thread de UI para serem mostrados na tela
                mainActivity.tradutoHandler.obtainMessage(
                    RESPOSTA_TRADUCAO,
                    traduzidoSb.toString().substringBeforeLast(',')
                ).sendToTarget()
            } catch (jse: JSONException) {
                mainActivity.mainLl.snackbar("Erro na conversão JSON")
            }
        }
    }*/

    inner class LanguagesListener : Response.Listener<JSONObject> {
        override fun onResponse(response: JSONObject?) {
            try {
                // Cria um objeto Gson que consegue fazer reflexão de um Json para Data Class
                val gson: Gson = Gson()
                // Reflete a resposta (que é um Json) num objeto da classe Resposta
                val resposta: LanguagesResponse = gson.fromJson(response.toString(), LanguagesResponse::class.java)
                // StringBuffer para armazenar o resultado das traduções
                var languages: MutableList<String> = mutableListOf()
                // Parseando o objeto e adicionando as traduções ao StringBuffer, O(N^5)
                resposta.results?.forEach {
                    languages.add(it?.source!!) //Language?.id!!)
                }
                // Enviando as tradução ao Handler da thread de UI para serem mostrados na tela
                mainActivity.tradutoHandler.obtainMessage(
                    RESPOSTA_TRADUCAO,
                    languages
                ).sendToTarget()
            } catch (jse: JSONException) {
                mainActivity.mainLl.snackbar("Erro na conversão JSON")
            }
        }
    }


    inner class ErroListener : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            mainActivity.mainLl.snackbar("Erro na requisição: ${error.toString()}")
        }
    }


}