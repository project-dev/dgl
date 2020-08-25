package jp.krohigewagma.dgl.net

import java.net.URL
import java.net.HttpURLConnection

class HttpUnit{

    var url : String = ""

    constructor(url : String){
        this.url = url
    }

    fun doPost(){
        doSend("POST")
    }

    fun doGost(){
        doSend("GET")
    }

    private fun doSend(method : String){
        var uri = URL(this.url)
        var con = uri.openConnection() as HttpURLConnection
        try{
            con.requestMethod = method
            if(con.responseCode == HttpURLConnection.HTTP_OK){
                // OK
            }
        }finally{
            con.disconnect()
        }

    }
}