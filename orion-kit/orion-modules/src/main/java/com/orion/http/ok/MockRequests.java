package com.orion.http.ok;

import com.orion.http.common.HttpContent;
import com.orion.http.common.HttpMethod;
import com.orion.http.ok.file.MockAsyncDownload;
import com.orion.http.ok.file.MockDownload;
import com.orion.http.ok.file.MockUpload;
import com.orion.http.ok.ws.MockWebSocketClient;
import com.orion.http.ok.ws.MockWebSocketServer;
import okhttp3.OkHttpClient;

import java.net.InetAddress;
import java.util.Map;

/**
 * Mock 调用工具
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/4/7 20:17
 */
public class MockRequests {

    /**
     * get
     *
     * @param url url
     * @return response
     */
    public static MockResponse get(String url) {
        return new MockRequest(url).await();
    }

    /**
     * get
     *
     * @param url    url
     * @param params params
     * @return response
     */
    public static MockResponse get(String url, Map<String, String> params) {
        return new MockRequest(url).queryParams(params).await();
    }

    /**
     * post
     *
     * @param url url
     * @return response
     */
    public static MockResponse post(String url) {
        return new MockRequest(url).method(HttpMethod.POST).await();
    }

    /**
     * post application/json
     *
     * @param url  url
     * @param body body
     * @return response
     */
    public static MockResponse post(String url, byte[] body) {
        return new MockRequest(url).method(HttpMethod.POST).contentType(HttpContent.APPLICATION_JSON).body(body).await();
    }

    /**
     * post application/json
     *
     * @param url  url
     * @param body body
     * @return response
     */
    public static MockResponse post(String url, String body) {
        return new MockRequest(url).method(HttpMethod.POST).contentType(HttpContent.APPLICATION_JSON).body(body).await();
    }

    /**
     * post
     *
     * @param url         url
     * @param contentType contentType
     * @param body        body
     * @return response
     */
    public static MockResponse post(String url, String contentType, byte[] body) {
        return new MockRequest(url).method(HttpMethod.POST).contentType(contentType).body(body).await();
    }

    /**
     * post
     *
     * @param url         url
     * @param contentType contentType
     * @param body        body
     * @return response
     */
    public static MockResponse post(String url, String contentType, String body) {
        return new MockRequest(url).method(HttpMethod.POST).contentType(contentType).body(body).await();
    }

    /**
     * post x-www-form-urlencoded
     *
     * @param url       url
     * @param formParts formParts
     * @return response
     */
    public static MockResponse post(String url, Map<String, String> formParts) {
        return new MockRequest(url).method(HttpMethod.POST).formParts(formParts).await();
    }

    /**
     * 获取get请求
     *
     * @return get
     */
    public static MockRequest get() {
        return new MockRequest();
    }

    /**
     * 获取post请求
     *
     * @return post
     */
    public static MockRequest post() {
        return new MockRequest().method(HttpMethod.POST);
    }

    /**
     * 异步下载文件
     *
     * @param url url
     * @return ignore
     */
    public static MockAsyncDownload downloadAsync(String url) {
        return new MockAsyncDownload(url);
    }

    /**
     * 异步下载文件
     *
     * @param url    url
     * @param client client
     * @return ignore
     */
    public static MockAsyncDownload downloadAsync(String url, OkHttpClient client) {
        return new MockAsyncDownload(url, client);
    }

    /**
     * 同步下载文件
     *
     * @param url url
     * @return ignore
     */
    public static MockDownload download(String url) {
        return new MockDownload(url);
    }

    /**
     * 同步下载文件
     *
     * @param url    url
     * @param client client
     * @return ignore
     */
    public static MockDownload download(String url, OkHttpClient client) {
        return new MockDownload(url, client);
    }

    /**
     * 上传文件
     *
     * @param url url
     * @return ignore
     */
    public static MockUpload upload(String url) {
        return new MockUpload(url);
    }

    /**
     * 上传文件
     *
     * @param url    url
     * @param client client
     * @return ignore
     */
    public static MockUpload upload(String url, OkHttpClient client) {
        return new MockUpload(url, client);
    }

    /**
     * 获取webSocket client
     *
     * @param url url
     * @return client
     */
    public static MockWebSocketClient getWebSocketClient(String url) {
        return new MockWebSocketClient(url);
    }

    /**
     * 获取webSocket server
     *
     * @param port port
     * @return server
     */
    public static MockWebSocketServer getWebSocketServer(int port) {
        return new MockWebSocketServer(port);
    }

    /**
     * 获取webSocket server
     *
     * @param address address
     * @param port    port
     * @return server
     */
    public static MockWebSocketServer getWebSocketServer(InetAddress address, int port) {
        return new MockWebSocketServer(address, port);
    }

}
