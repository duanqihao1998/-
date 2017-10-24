package okhttp3utilsstudy.text.com.okhttp3utils.okhttputils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 1. 类的用途    对OkHttp3二次封装   单例模式
 * 2. @author 王毅博
 * 3. @date 2017/9/9 13:13
 */

public class OkHttp3Utils {

    private static OkHttpClient okHttpClient = null;

    public OkHttp3Utils() {
    }

    //通过单例模式获取实例
    public static OkHttpClient getInstance() {

        if (okHttpClient == null) {
            //同步代码
            synchronized (OkHttp3Utils.class) {
                if (okHttpClient == null) {
                    //缓存路径
                    File sdcache = new File(Environment.getExternalStorageDirectory(), "wybcache");
                    int cacheSize = 10 * 1024 * 1024;
                    //okhttp拦截器
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.i("msg", message.toString());
                        }
                    });
                    //拦截器日志分类
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES)
                            .addInterceptor(httpLoggingInterceptor)
                            .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize)).build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * get请求方式
     *
     * @param url      请求地址
     * @param callback 回调接口
     */
    public static void doGet(String url, Callback callback) {
        //获取请求对象的实例
        OkHttpClient okHttpClient = OkHttp3Utils.getInstance();
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(callback);
    }

    public static void doGetHeader(String url,Map<String,String> headers, Callback callback) {
        //获取请求对象的实例
        OkHttpClient okHttpClient = OkHttp3Utils.getInstance();
        //创建Request
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        for (String header:headers.keySet())
        {
            builder.addHeader(header,headers.get(header));
        }
        Request request=builder.build();
        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(callback);
    }
    /**
     * post请求方式
     *
     * @param url      请求地址
     * @param params   请求体
     * @param callback 接口回调
     */
    public static void doPost(String url, Map<String, String> params, Callback callback) {
        //获取请求对象的实例
        OkHttpClient okHttpClient = OkHttp3Utils.getInstance();
        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合给请求体添加值
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        //获取call
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(callback);
    }
    public static void doPostHeader(String url,Map<String,String> headers, Map<String, String> params, Callback callback) {
        //获取请求对象的实例
        OkHttpClient okHttpClient = OkHttp3Utils.getInstance();
        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合给请求体添加值
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //创建Request
        //创建Request
        Request.Builder builder1 = new Request.Builder();
        builder1.url(url);
        for (String header:headers.keySet())
        {
            builder1.addHeader(header,headers.get(header));
        }
        Request request = builder1.post(builder.build()).build();
        //获取call
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(callback);
    }
    /**
     * 上传文件
     *
     * @param url      接口地址
     * @param file     文件
     * @param fileName 文件名
     */
    public static void loadFile(String url, File file, String fileName,Callback callback) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传json字符
     *
     * @param url        接口地址
     * @param jsonParams Json串
     * @param callback   接口回调
     */
    public static void doPostJson(String url, String jsonParams, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 下载
     *
     * @param context
     * @param url     下载地址
     * @param saveDir 保存的位置
     */
    public static void downFile(final Activity context, final String url, final String saveDir) {

        //创建Request
        Request request = new Request.Builder().url(url).build();
        //创建Call
        Call call = getInstance().newCall(request);
        //同步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("xxx", e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    //apk保存路径
                    final String fileDir = isExistDir(saveDir);
                    //文件
                    File file = new File(fileDir, getNameFromUrl(url));
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "下载成功:" + fileDir + "," + getNameFromUrl(url), Toast.LENGTH_SHORT).show();
                        }
                    });
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }

                    fos.flush();
                    //apk下载完成后 调用系统的安装方法
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    context.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            Log.e("savePath", savePath);
            return savePath;
        }
        return null;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
