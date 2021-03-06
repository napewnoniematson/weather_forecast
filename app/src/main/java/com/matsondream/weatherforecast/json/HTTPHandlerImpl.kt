package com.matsondream.exchangerates

import android.util.Log
import com.matsondream.weatherforecast.json.HTTPHandler
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Matson on 05.12.2017.
 */
class HTTPHandlerImpl : HTTPHandler {

    @Throws(MalformedURLException::class, IOException::class)
    override fun makeServiceCall(reqUrl: String): String? {
        var response: String? = null
        var url = URL(reqUrl)
        var connection  = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        var stream: InputStream = BufferedInputStream(connection.inputStream)
        response = convertStreamToString(stream)
        return response
    }

    @Throws(IOException::class)
    private fun convertStreamToString(stream: InputStream): String {
        var reader = BufferedReader(InputStreamReader(stream))
        var sb = StringBuilder()
        var line: String?
        line = reader.readLine()
        while (line != null) {
            sb.append(line).append('\n')
            line = reader.readLine()
        }
        stream.close()
        return  sb.toString()
    }
}