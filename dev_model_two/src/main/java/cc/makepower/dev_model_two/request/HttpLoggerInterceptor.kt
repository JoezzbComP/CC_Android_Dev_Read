package makpower.cc.civillock.retrofit

import android.util.Log
import okhttp3.*
import okio.Buffer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
author : atex
 *     e-mail : xxx@xx
 *     time   : 2018/11/29
 *     desc   :
 *     version: 1.0
 */
class HttpLoggerInterceptor : Interceptor {
    enum class Level{
        /**
        //         * No logs.
        //         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         * <p>
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         * <p>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * <p>
         * Hi?
         * --> END GET
         * <p>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <p>
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    private val UTF8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        var level = Level.BODY
//
         request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        var logBody = level == Level.BODY;
        var logHeaders = logBody || level == Level.HEADERS;

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        var requestStartMessage = "--> " + request.method() + ' ' + requestPath(request.url()) + ' ' + protocol(protocol)
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        Log.d("HttpLoggerInterceptor",requestStartMessage)
        if (logHeaders) {
            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                Log.d("HttpLoggerInterceptor",headers.name(i) + ": " + headers.value(i))
                i++
            }

            var endMessage = "--> END " + request.method()
            if (logBody && hasRequestBody) {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)

                val charset = UTF8
                val contentType = requestBody.contentType()
                contentType?.charset(UTF8)

//                logger.log("")
//                logger.log(buffer.readString(charset))
                Log.d("HttpLoggerInterceptor","")
                Log.d("HttpLoggerInterceptor",buffer.readString(charset))
                endMessage += " (" + requestBody.contentLength() + "-byte body)"
            }
//            logger.log(endMessage)
            Log.d("HttpLoggerInterceptor",endMessage)
        }

        val startNs = System.nanoTime()
        val response = chain!!.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)


        val responseBody = response.body()
        if (responseBody != null) {
            Log.d("HttpLoggerInterceptor","<-- " + protocol(response.protocol()) + ' '.toString() + response.code() + ' '.toString()
                    + response.message() + " (" + tookMs + "ms"
                    + (if (!logHeaders) ", " + responseBody.contentLength() + "-byte body" else "") + ')'.toString())
//            logger.log("<-- " + protocol(response.protocol()) + ' '.toString() + response.code() + ' '.toString()
//                    + response.message() + " (" + tookMs + "ms"
//                    + (if (!logHeaders) ", " + responseBody.contentLength() + "-byte body" else "") + ')'.toString())
        }

        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                Log.d("HttpLoggerInterceptor",headers.name(i) + ": " + headers.value(i))
//                logger.log(headers.name(i) + ": " + headers.value(i))
                i++
            }

            var endMessage = "<-- END HTTP"
            if (logBody) {
                val source = responseBody!!.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()

                var charset = UTF8
                val contentType = responseBody!!.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (responseBody.contentLength()>0||responseBody.contentLength()<0) {
                    Log.d("HttpLoggerInterceptor","")
                    Log.d("HttpLoggerInterceptor",buffer.clone().readString(charset))
//                    logger.log()
                    //                    logger.log(AES_Encryption.Decrypt(buffer.clone().readString(charset),AES_Encryption.secretKey));
                }

                endMessage += " (" + buffer.size() + "-byte body)"
            }
            Log.d("HttpLoggerInterceptor",endMessage)
        }
        return response
    }

    private fun protocol(protocol: Protocol): String {
        return if (protocol == Protocol.HTTP_1_0) "HTTP/1.0" else "HTTP/1.1"
    }

    private fun requestPath(url: HttpUrl): String {
        val path = url.encodedPath()
        val query = url.encodedQuery()
        return if (query != null) path + '?'.toString() + query else path
    }

}