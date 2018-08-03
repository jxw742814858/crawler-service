package httptest;

import entity.HttpEntity;
import entity.ProxyEntity;
import kit.HttpKit;
import org.junit.Test;

public class test {

    @Test
    public void requestTest() {
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        ProxyEntity proxy = new ProxyEntity("192.168.155.155", 25, "yproxyq", "zproxyx");
        try {
            HttpEntity entity = HttpKit.get("https://plus.google.com/110924633889503463658", proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
