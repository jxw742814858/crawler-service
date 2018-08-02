package kit;

import constants.HttpConst;
import entity.HttpEntity;
import entity.ProxyEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HttpKit {
    private static Logger log = LoggerFactory.getLogger(HttpKit.class);

    public static HttpEntity get(String url) {
        return baseRequest(url, null, null, null, HttpConst.REQUEST_METHOD_GET);
    }

    public static HttpEntity get(String url, ProxyEntity proxy) {
        return baseRequest(url, null, proxy, null, HttpConst.REQUEST_METHOD_GET);
    }

    public static HttpEntity get(String url, ProxyEntity proxy, Map<String, String> header) {
        return baseRequest(url, null, proxy, header, HttpConst.REQUEST_METHOD_GET);
    }

    public static HttpEntity post(String url, String dataStr, Map<String, String> header) {
        return baseRequest(url, dataStr, null, header, HttpConst.REQUEST_METHOD_POST);
    }

    public static HttpEntity post(String url, String dataStr, Map<String, String> header, ProxyEntity proxy) {
        return baseRequest(url, dataStr, proxy, header, HttpConst.REQUEST_METHOD_POST);
    }

    private static HttpEntity baseRequest(String url, String dataStr, ProxyEntity proxy, Map<String, String> header,
                                          String requestType) {
        if (url == null || url.trim().length() == 0) {
            return null;
        }

        RequestConfig requestConfig = null;
        HttpClientContext httpClientContext = null;
        CloseableHttpResponse httpResponse = null;
        HttpEntity entity = null;

        // 判断url, 得到域名、端口、协议类型
        String domain = null, protocol = null;
        int port = 0;
        if (url.contains(HttpConst.PROTOCOL_HTTPS_HEAD)) {
            domain = url.replace(HttpConst.PROTOCOL_HTTPS_HEAD + "://", "");
            port = HttpConst.PROTOCOL_HTTPS_PORT;
            protocol = HttpConst.PROTOCOL_HTTPS_HEAD;
        } else {
            domain = url.replace(HttpConst.PROTOCOL_HTTP_HEAD + "://", "");
            port = HttpConst.PROTOCOL_HTTP_PORT;
            protocol = HttpConst.PROTOCOL_HTTP_HEAD;
        }
        if (domain.contains("/")) {
            domain = domain.substring(0, domain.indexOf("/"));
        }

        HttpHost targetHost = new HttpHost(domain, port, protocol);
        CredentialsProvider credsProvider = null;

        // 使用代理请求
        if (proxy != null) {
            try {
                // 创建认证
                credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()),
                        new UsernamePasswordCredentials(proxy.getAgentAccount(), proxy.getAgentPassword()));

                HttpHost httpHost = new HttpHost(proxy.getHost(), proxy.getPort());
                requestConfig = RequestConfig.custom()
                        .setConnectTimeout(HttpConst.CONNECT_TIMEOUT)   // 设置连接超时时间
                        .setConnectionRequestTimeout(HttpConst.CONNECT_TIMEOUT) // 设置请求超时时间
                        .setSocketTimeout(HttpConst.CONNECT_TIMEOUT)
                        .setRedirectsEnabled(true)  // 默认允许自动重定向
                        .setProxy(httpHost)
                        .build();
            } catch (Exception e) {
                log.error("", e);
            }
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(HttpConst.CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(HttpConst.CONNECT_TIMEOUT)
                    .setSocketTimeout(HttpConst.CONNECT_TIMEOUT)
                    .setRedirectsEnabled(true)
                    .build();
        }

        try (CloseableHttpClient client = proxy == null ? HttpClients.createDefault()
                : HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build()) {
            switch (requestType) {
                // 主要作用于采集时请求
                case HttpConst.REQUEST_METHOD_GET:
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setConfig(requestConfig);

                    // 设置默认header
                    httpGet.setHeader(HttpConst.HEADER_ACCEPT_KEY, HttpConst.HEADER_ACCEPT_VALUE);
                    httpGet.setHeader(HttpConst.HEADER_ACCEPT_LANGUAGE_KEY, HttpConst.HEADER_ACCEPT_LANGUAGE_VALUE);
                    httpGet.setHeader(HttpConst.HEADER_CONNECTION_KEY, HttpConst.HEADER_CONNECTION_VALUE);
                    httpGet.setHeader(HttpConst.HEADER_USERAGENT_KEY, HttpConst.HEADER_USERAGENT_VALUE);

                    // 添加传入的header值
                    if (header != null) {
                        for (Map.Entry<String, String> entry : header.entrySet()) {
                            httpGet.setHeader(entry.getKey(), entry.getValue());
                        }
                    }

                    httpResponse = proxy == null ? client.execute(httpGet) : client.execute(targetHost, httpGet);
                    break;

                // 主要作用于提交数据时请求
                case HttpConst.REQUEST_METHOD_POST:
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setConfig(requestConfig);

                    if (header != null) {
                        for (Map.Entry<String, String> entry : header.entrySet()) {
                            httpPost.setHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    // 设置值字符串
                    httpPost.setEntity(new StringEntity(dataStr, ContentType.create(HttpConst.HEADER_CONTENTTYPE_JSON,
                            HttpConst.DEFAULT_CHARSET)));
                    httpResponse = proxy == null ? client.execute(httpPost)
                            : client.execute(targetHost, httpPost);
                    break;

                default:
                    break;
            }

            String html = EntityUtils.toString(httpResponse.getEntity());
            Integer respCode = httpResponse.getStatusLine().getStatusCode();
            entity = new HttpEntity(html, respCode);
            return entity;
        } catch (IOException e) {
            log.error("", e);
        } finally {
            if (httpResponse != null) {
                httpResponse = null;
            }
            if (requestConfig != null) {
                requestConfig = null;
            }
            if (httpClientContext != null) {
                httpClientContext = null;
            }
        }
        return null;
    }
}
