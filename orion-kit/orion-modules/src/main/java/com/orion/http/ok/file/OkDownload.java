package com.orion.http.ok.file;

import com.orion.http.ok.OkRequest;
import com.orion.http.ok.OkResponse;
import com.orion.utils.io.Files1;
import com.orion.utils.io.Streams;
import okhttp3.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Mock 下载文件
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/6/25 0:15
 */
public class OkDownload {

    private OkRequest request;

    private OkResponse response;

    public OkDownload(String url) {
        this.request = new OkRequest(url).client(null);
    }

    public OkDownload(String url, OkHttpClient client) {
        this.request = new OkRequest(url).client(client);
    }

    public OkDownload(OkRequest request) {
        this.request = request.client(null);
    }

    public OkDownload(OkRequest request, OkHttpClient client) {
        this.request = request.client(client);
    }

    public OkDownload client(OkHttpClient client) {
        this.request.client(client);
        return this;
    }

    public OkDownload download(String file) throws IOException {
        Files1.touch(file);
        this.download(Files1.openOutputStream(file), true);
        return this;
    }

    public OkDownload download(File file) throws IOException {
        Files1.touch(file);
        this.download(Files1.openOutputStream(file), true);
        return this;
    }

    public OkDownload download(OutputStream out) throws IOException {
        this.download(out, false);
        return this;
    }

    public OkDownload download(OutputStream out, boolean autoClose) throws IOException {
        this.response = this.request.await();
        out.write(this.response.getBody());
        if (autoClose) {
            Streams.close(out);
        }
        return this;
    }

    public OkRequest getRequest() {
        return request;
    }

    public OkResponse getResponse() {
        return response;
    }

}