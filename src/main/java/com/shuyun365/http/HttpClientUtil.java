package com.shuyun365.http;

/**
 * Created by sw on 2018/3/9
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    static {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%-6r [%p] %c - %m%n")));
    }

    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * 发送post请求
     *
     * @param url   请求路径
     * @param param 请求json数据
     * @return
     */
    public static String doPost(String url, JSONObject param) {
        HttpPost httpPost = null;
        String result = null;
        try {
            HttpClient client = SSLClient.createSSLClientDefault();
            httpPost = new HttpPost(url);
            if (param != null) {
                StringEntity se = new StringEntity(param.toString(), "utf-8");
//                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset="));
                httpPost.setEntity(se); //post方法中，加入json数据
                httpPost.setHeader("Content-Type", "application/json");
            }

            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("返回结果:\n" + result);
        return result;
    }

    /**
     * 发送post请求
     *
     * @param url       请求路径
     * @param jsonparam 请求json数据字符串
     * @return
     */
    public static String taikangPost(String url, String jsonparam) {
        HttpPost httpPost = null;
        String result = null;
        try {
            HttpClient client = SSLClient.createSSLClientDefault();
            httpPost = new HttpPost(url);
            logger.info("请求路径:\n" + url);
            logger.info("请求参数:\n" + jsonparam);
            if (jsonparam != null) {
                jsonparam = Base64Util.encode(jsonparam);
                StringEntity se = new StringEntity(jsonparam, "GBK");
                httpPost.setEntity(se); //post方法中，加入json数据
                httpPost.setHeader("Content-Type", "application/text");
            }

            HttpResponse response = client.execute(httpPost);

            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "GBK");
                    logger.info("返回结果原始字符串:\n" + result);
                    result = Base64Util.decode(result);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.info("返回结果:\n" + result);
        return result;
    }


    /**
     * 发送post请求
     *
     * @param url   请求路径
     * @param param 请求参数数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String doPostMap(String url, Map<String, String> map) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = SSLClient.createSSLClientDefault();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.info("返回结果" + result);
        return result;
    }

    /**
     * 发送get请求
     *
     * @param url   请求路径
     * @param param 请求json数据
     * @return
     */
    public static String doGet(String url) {
        String result = null;
        try {
            CloseableHttpClient client = SSLClient.createSSLClientDefault();
            //用get方法发送http请求
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = (CloseableHttpResponse) client.execute(get);
            try {
                //response实体
                HttpEntity entity = httpResponse.getEntity();

                if (null != entity) {
                    /** //写到本地文件
                    InputStream input = entity.getContent();
                    OutputStream output = new FileOutputStream("D:/dat.json");
                    int len =0;
                    byte[] buff = new byte[1024];
                    while (-1!=(len= input.read(buff))){
                        output.write(buff,0,len);
                    }**/
                   result = EntityUtils.toString(entity, "utf-8");
                }
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("返回结果" + result);
        return result;
    }


    public static void main(String[] args) {

        String result = HttpClientUtil.doGet("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=7WB1UUKGC1EG46BL");

    }


}