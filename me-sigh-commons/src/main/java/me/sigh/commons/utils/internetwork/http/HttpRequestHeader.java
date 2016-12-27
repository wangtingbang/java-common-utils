package me.sigh.commons.utils.internetwork.http;

/**
 * Created by wangtingbang on 2016/12/27.
 */
public class HttpRequestHeader {

  private String requestSeqNo;

  private String requestUrl;

  private String contentEncoding;

  private String contentType;

  private int timeout;


  public HttpRequestHeader() {
  }

  public HttpRequestHeader(String requestSeqNo, String requestUrl, String contentEncoding, String contentType, int timeout) {
    this.requestSeqNo = requestSeqNo;
    this.requestUrl = requestUrl;
    this.contentEncoding = contentEncoding;
    this.contentType = contentType;
    this.timeout = timeout;
  }

  public String getRequestSeqNo() {
    return requestSeqNo;
  }

  public void setRequestSeqNo(String requestSeqNo) {
    this.requestSeqNo = requestSeqNo;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  public String getContentEncoding() {
    return contentEncoding;
  }

  public void setContentEncoding(String contentEncoding) {
    this.contentEncoding = contentEncoding;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
