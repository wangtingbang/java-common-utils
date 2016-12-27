package me.sigh.commons.utils.internetwork.http;


//import com.cmbchina.commons.util.KeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangtingbang on 16/3/18.
 */
public class HttpRequestUtil {

  private static String DEFAULT_CONTENT_TYPE = "application/json";

  private static String DEFAULT_CONTENT_ENCODING = "UTF-8";

  private static int DEFAULT_CONTENT_TIMEOUT = 15000;

  private static Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);

  public static String sendJsonPostRequest(HttpRequestHeader header, String body) {

    HttpClient httpClient = new DefaultHttpClient();
    try {
      HttpPost post = new HttpPost();
      String url = header.getRequestUrl();
      String encoding = StringUtils.isEmpty(header.getContentEncoding())
              ?DEFAULT_CONTENT_ENCODING:header.getContentEncoding();
      String contentType = StringUtils.isEmpty(header.getContentType())
              ?DEFAULT_CONTENT_TYPE:header.getContentType();

      log.info("create connection to:{}", url);
      post.setURI(URI.create(url));

      StringEntity entity = new StringEntity(body, encoding);

      entity.setContentType(contentType);

      post.setEntity(entity);

      RequestLine requestLine = post.getRequestLine();

      log.info("request line:{}", requestLine.toString());
      log.info("request content:{}", EntityUtils.toString(post.getEntity(),DEFAULT_CONTENT_ENCODING));

      HttpResponse response = httpClient.execute(post);

      if (response == null) {
        System.out.println("error: response is null");
        log.error("error: response is null");
        return null;
      }
      StatusLine statusLine = response.getStatusLine();
      if (statusLine == null) {
        System.out.println("error: response.statusLine is null");
        log.error("error: response.statusLine is null");
        return null;
      }
      int statusCode = statusLine.getStatusCode();
      log.info("response.statusLine.statusCode:" + statusCode);
      log.info("response line:{}", response);
      String result = null;
      if (Response.Status.OK.getStatusCode() == statusCode) {
        result = EntityUtils.toString(response.getEntity(), header.getContentEncoding());// TODO
      }
      log.info("response content:{}", result);
      return result;
    } catch (Exception e) {
      log.error("error:{}", e);
    } finally {
      log.info("httpClient-->closeExpiredConnections");
      httpClient.getConnectionManager().closeExpiredConnections();// TODO connection pool
    }
    return null;
  }

  public static String sendParameterPostRequest(HttpRequestHeader header,
      Map<String, String> params) {

    HttpClient httpClient = new DefaultHttpClient();
    try {
      HttpPost post = new HttpPost();
      String url = header.getRequestUrl();
      log.info("create connection to:{}", url);
      post.setURI(URI.create(url));

      List<NameValuePair> nvps = new ArrayList<NameValuePair>();

      Set<String> keySet = params.keySet();
      for (String key : keySet) {
        nvps.add(new BasicNameValuePair(key, params.get(key)));
      }

      post.setEntity(new UrlEncodedFormEntity(nvps, StringUtils.isEmpty(header.getContentEncoding())
          ? DEFAULT_CONTENT_ENCODING : header.getContentEncoding()));


      RequestLine requestLine = post.getRequestLine();

      log.info("request line:{}", requestLine.toString());
      log.info("request content:{}", EntityUtils.toString(post.getEntity()));

      HttpResponse response = httpClient.execute(post);

      if (response == null) {
        System.out.println("error: response is null");
        log.error("error: response is null");
        return null;
      }
      StatusLine statusLine = response.getStatusLine();
      if (statusLine == null) {
        System.out.println("error: response.statusLine is null");
        log.error("error: response.statusLine is null");
        return null;
      }
      int statusCode = statusLine.getStatusCode();
      log.info("response.statusLine.statusCode:" + statusCode);
      log.info("response line:{}", response);
      String result = null;
      if (Response.Status.OK.getStatusCode() == statusCode) {
        result = EntityUtils.toString(response.getEntity(), header.getContentEncoding());// TODO
      }
      log.info("response content:{}", result);
      return result;
    } catch (Exception e) {

    }

    // response
    /**
     * "id": "123123123", // + "productState": "1", // + "productNo": "2016032398001", // +
     * "productName": "手机活动平台测试商品1", // + "ticketPrice": 98, // + 市场价 "totalStock": 98, // +
     * "productPicUrl": "11", // + "salesPrice2": 0, // + 销售价 "validityPerBegin": 1458662400000, //
     * - "validityPerEnd": 1458748800000, // - "labelId": "07", // + productType "status": "1", // +
     */
    return null;
  }

  public static String sendNameValuePairPostRequest(HttpRequestHeader header,
    Map<String, String> param, String proIp, int proPort) {
    log.info("此方法真没用代理，由于代码历史原因和线上补丁形式的发版，才了有这个蛋疼的method，后面肯定要干掉");
    return sendNameValuePairPostRequest(header, param);
  }
  public static String sendNameValuePairPostRequest(HttpRequestHeader header,
      Map<String, String> param) {
    HttpClient httpClient = new DefaultHttpClient();

    if (header.getTimeout() == 0) {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          DEFAULT_CONTENT_TIMEOUT);
    }
    try {
      HttpPost post = new HttpPost();
      String url = header.getRequestUrl();
      log.info("create connection to:{}", url);
      post.setURI(URI.create(url));

      // BasicHttpEntity entity = new BasicHttpEntity();

      // post.setEntity(entity);

      List<NameValuePair> nvps = new ArrayList<NameValuePair>();

      Set<String> keySet = param.keySet();
      for (String key : keySet) {
        nvps.add(new BasicNameValuePair(key, param.get(key)));
      }


      post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

      RequestLine requestLine = post.getRequestLine();

      String seqNo = StringUtils.isEmpty(header.getRequestSeqNo())
        ? "I"+System.currentTimeMillis()
        :header.getRequestSeqNo();
      log.info("{}-->> 请求头:{}",seqNo, requestLine.toString());
      log.info("{}-->> 请求报文:{}",seqNo, EntityUtils.toString(post.getEntity()));

      HttpResponse response = httpClient.execute(post);

      if (response == null) {
        System.out.println("error: response is null");
        log.error("error: response is null");
        return null;
      }
      StatusLine statusLine = response.getStatusLine();
      if (statusLine == null) {
        System.out.println("error: response.statusLine is null");
        log.error("error: response.statusLine is null");
        return null;
      }
      int statusCode = statusLine.getStatusCode();
      log.info("{}--<< 响应码:{}", seqNo,statusCode);
      log.info("{}--<< 响应:{}", seqNo, response);
      String result = null;
      if (Response.Status.OK.getStatusCode() == statusCode) {
        result = EntityUtils.toString(response.getEntity(), header.getContentEncoding());// TODO
      }
      log.info("response content:{}", result);
      return result;
    } catch (Exception e) {
      log.error("error:{}", e);
    } finally {
      log.info("httpClient-->closeExpiredConnections");
      httpClient.getConnectionManager().closeExpiredConnections();// TODO connection pool
    }
    return null;
  }

  public static String sendStringPostRequest(HttpRequestHeader header,
    String param, String proxyIp, int proxyPort) {
    HttpClient httpClient = new DefaultHttpClient();
    if (!StringUtils.isEmpty(proxyIp)){
      HttpHost proxy = new HttpHost(proxyIp, proxyPort);
      httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }
    if (header.getTimeout() == 0) {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
        DEFAULT_CONTENT_TIMEOUT);
    }
    try {
      HttpPost post = new HttpPost();
      String url = header.getRequestUrl();
      log.info("create connection to:{}", url);
      post.setURI(URI.create(url));

      StringEntity entity = new StringEntity(param, "UTF-8");
//      entity.setContentType("text/plain");
//      post.setEntity(new StringEntity(param));
      post.setEntity(entity);

      RequestLine requestLine = post.getRequestLine();

      String seqNo = StringUtils.isEmpty(header.getRequestSeqNo())
    	? "I"+System.currentTimeMillis()
        :header.getRequestSeqNo();
      log.info("{}-->> 请求头:{}",seqNo, requestLine.toString());
      log.info("{}-->> 请求报文:{}",seqNo, EntityUtils.toString(post.getEntity()));

      HttpResponse response = httpClient.execute(post);

      if (response == null) {
        System.out.println("error: response is null");
        log.error("error: response is null");
        return null;
      }
      StatusLine statusLine = response.getStatusLine();
      if (statusLine == null) {
        System.out.println("error: response.statusLine is null");
        log.error("error: response.statusLine is null");
        return null;
      }
      int statusCode = statusLine.getStatusCode();
      log.info("{}--<< 响应码:{}", seqNo,statusCode);
      log.info("{}--<< 响应:{}", seqNo, response);
      String result = null;
      if (Response.Status.OK.getStatusCode() == statusCode) {
        result = EntityUtils.toString(response.getEntity(), header.getContentEncoding());// TODO
      }
      log.info("response content:{}", result);
      return result;
    } catch (Exception e) {
      log.error("error:{}", e);
    } finally {
      log.info("httpClient-->closeExpiredConnections");
      httpClient.getConnectionManager().closeExpiredConnections();// TODO connection pool
    }
    return null;
  }
}
