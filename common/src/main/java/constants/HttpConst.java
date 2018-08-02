package constants;

public class HttpConst {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String REQUEST_METHOD_GET = "get";
    public static final String REQUEST_METHOD_POST = "post";

    public static final String PROTOCOL_HTTP_HEAD = "http";
    public static final String PROTOCOL_HTTPS_HEAD = "https";

    public static final int PROTOCOL_HTTP_PORT = 80;
    public static final int PROTOCOL_HTTPS_PORT = 443;

    public static final int CONNECT_TIMEOUT = 5000;

    public static final String HEADER_USERAGENT_KEY = "User-Agent";
    public static final String HEADER_USERAGENT_VALUE =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0";

    public static final String HEADER_ACCEPT_KEY = "Accept";
    public static final String HEADER_ACCEPT_VALUE = "*/*";

    public static final String HEADER_ACCEPT_LANGUAGE_KEY = "Accept-Language";
    public static final String HEADER_ACCEPT_LANGUAGE_VALUE =
            "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2";

    public static final String HEADER_CONNECTION_KEY = "Connection";
    public static final String HEADER_CONNECTION_VALUE = "keep-alive";

    public static final String HEADER_COOKIE_KEY = "Cookie";
    public static final String HEADER_CONTENTTYPE_KEY = "Content-Type";
    public static final String HEADER_CONTENTTYPE_JSON = "application/json";

}
