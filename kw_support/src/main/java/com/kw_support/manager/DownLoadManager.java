package com.kw_support.manager;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/8/30.
 */
public class DownLoadManager {

    private static DownLoadManager mInstance;

    private OkHttpClient mOkHttpClient;

    private DownLoadManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static DownLoadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownLoadManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步基于post文件上传：上传单个文件�
     */
    public Response upload(String url, String fileKey, File file) throws IOException {
        return upload(url, new String[]{fileKey}, new File[]{file}, null);
    }

    /**
     * 同步单文件上传
     */
    public Response post(String url, String fileKey, File file, Param... params) throws IOException {
        return upload(url, new String[]{fileKey}, new File[]{file}, params);
    }

    /**
     * 同步基于post的文件上传：上传多个文件以及携带key-value对��
     */
    public Response upload(String url, String[] fileKeys, File[] files, Param[] paramses) throws IOException {
        Request request = buildMultipartFormRequest(url, fileKeys, files, paramses);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传:主方法����
     */
    public void postAsyn(String url, String[] fileKeys, File[] files, Param[] params, ResultCallback callback) {
        Request request = buildMultipartFormRequest(url, fileKeys, files, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传:单文件不带参数上传
     */
    public void postAsyn(String url, String fileKey, File file, ResultCallback callback) throws IOException {
        postAsyn(url, new String[]{fileKey}, new File[]{file}, null, callback);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     */
    public void postAsyn(String url, String fileKey, File file, Param[] params, ResultCallback callback) {
        postAsyn(url, new String[]{fileKey}, new File[]{file}, params, callback);
    }

    private Request buildMultipartFormRequest(String url, String[] fileKeys, File[] files, Param[] paramses) {
        paramses = validateParam(paramses);
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Param param : paramses) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""), RequestBody.create(null, param.value));
        }

        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMineType(fileName)), file);
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""), fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private String guessMineType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (TextUtils.isEmpty(contentTypeFor)) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private Param[] validateParam(Param[] paramses) {
        return paramses == null ? new Param[0] : paramses;
    }

    private void deliveryResult(final ResultCallback callback, Request request) {
        if (callback == null) ;
        final ResultCallback resCallBack = callback;
        if (callback != null) {
            callback.onStart();
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                if (callback == null) {
                    return;
                }
                callback.onFailed(request, e);
            }

            @Override
            public void onResponse(final Response response) {
                if (callback == null) {
                    return;
                }
                callback.onSucceed(response);
            }
        });
    }


    /**
     * 文件下载类�
     *
     * @param url
     * @param destFilePath 目标文件存放地址
     * @param callback     callback仍在在非UI线程当中，所以需要转换成UI线程�
     */

    public void downLoad(final String url, final String destFilePath, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        final Call call = mOkHttpClient.newCall(request);

        if (callback != null) {
            callback.onStart();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (callback == null) {
                    return;
                }

                callback.onFailed(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[1024];
                int len = 0;
                BufferedOutputStream bos = null;

                try {
                    is = response.body().byteStream();

                    File dir = new File(destFilePath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File file = new File(dir, getFileName(url));
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    while ((len = is.read(buf)) != -1) {
                        bos.write(buf, 0, len);
                    }
                    bos.flush();

                    if (callback == null) {
                        return;
                    }

                    callback.onSucceed(destFilePath);
                } catch (Exception e) {
                    if (callback == null) {
                        return;
                    }

                    callback.onFailed(response.request(), e);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (bos != null) {
                            bos.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public abstract class ResultCallback {
        public void onStart() {
        }

        public void onProgress(int progress) {
        }

        public abstract void onSucceed(Object obj);

        public abstract void onFailed(Request request, Exception e);
    }
}

