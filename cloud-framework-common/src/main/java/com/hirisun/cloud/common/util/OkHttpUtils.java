package com.hirisun.cloud.common.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);
    private static final MediaType MT_JSON = MediaType.parse("application/json");

    /**
     * 默认OkHttpClient配置
     */
    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static Response get(String url, Map<String, String> param) throws IOException {
        return get(okHttpClient, url, param);
    }

    public static Response get(OkHttpClient client, String url, Map<String, String> param) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().get().url(urlBuilder.build()).build();
        return client.newCall(request).execute();
    }

    public static Response post(String url, Map<String, String> param) throws IOException {
        return post(okHttpClient, url, param);
    }

    public static Response post(OkHttpClient client, String url, Map<String, String> param) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().post(builder.build()).url(url).build();
        return client.newCall(request).execute();
    }

    public static Response postJson(String url, String json) throws IOException {
        return postJson(okHttpClient, url, json);
    }

    public static Response postJson(OkHttpClient client, String url, String json) throws IOException {
        logger.error("postJson,RequestBody: [{}]", json);
        RequestBody requestBody = RequestBody.create(MT_JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return client.newCall(request).execute();
    }
    public static Response putFile(OkHttpClient client, MediaType mediaType, String url, String localPath) throws IOException {
        logger.error("putFile,RequestBody: [{}]", localPath);
        File file = new File(localPath);
        RequestBody requestBody = RequestBody.create(mediaType, file);
        Request request = new Request.Builder().url(url).put(requestBody).build();
        Response response = client.newCall(request).execute();
        return response;
    }
    /**
     * put请求
     * @param client
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static Response putJson(OkHttpClient client, String url, String json) throws IOException {
        logger.error("putJson,RequestBody: [{}]", json);
        RequestBody requestBody = RequestBody.create(MT_JSON, json);
        Request request = new Request.Builder().url(url).put(requestBody).build();
        return client.newCall(request).execute();
    }

    /**
     * delete请求
     * @param client
     * @param url
     * @return
     * @throws IOException
     */
    public static Response delete(OkHttpClient client, String url) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        Request request = new Request.Builder().delete().url(urlBuilder.build()).build();
        return client.newCall(request).execute();
    }

}
