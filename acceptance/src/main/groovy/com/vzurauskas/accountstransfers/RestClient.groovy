package com.vzurauskas.accountstransfers

import org.apache.http.Header
import org.apache.http.HttpResponse
import org.apache.http.client.HttpRequestRetryHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicHeaderElement
import org.apache.http.protocol.HttpContext

import static com.vzurauskas.accountstransfers.Json.readJson
import static com.vzurauskas.accountstransfers.Json.toJson

@Singleton
class RestClient {
    static CloseableHttpClient client;
    static {
        client = HttpClients.custom().setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount > 10) {
                    return false;
                }
                if (exception instanceof org.apache.http.NoHttpResponseException) {
                    return true;
                }
                return false;
            }
        }).build()
    }

    static post(String url, Map body) {
        return post(url, body, [:])
    }

    static post(String url, Map body, Map headersToAdd) {
        def headers = headersToAdd.collect {it -> new BasicHeaderElement(it.key, it.value, null) } as Header[]
        def req = new HttpPost(url).tap {
            setHeaders(headers)
            setEntity(new StringEntity(toJson(body), ContentType.APPLICATION_JSON))
        }
        def response = client.execute(req)
        return RestResponse.readAndClose(response)
    }

    static get(String url) {
        def req = new HttpGet(url)
        def response = client.execute(req)
        return RestResponse.readAndClose(response)
    }

    static class RestResponse {
        int status
        Map body

        static readAndClose(HttpResponse res) {
            return new RestResponse(
                status: res.statusLine.statusCode,
                body: res.entity ? readJson(res.entity.content) : [:]
            )
        }
    }
}
