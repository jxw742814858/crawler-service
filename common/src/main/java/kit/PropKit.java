package kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropKit {
    private static Logger log = LoggerFactory.getLogger(PropKit.class);

    /**
     * 获取properties 配置
     * @param fileName
     * @return
     */
    public static Properties getProp(String fileName) {
        Properties prop = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(PropKit.class.getResourceAsStream(fileName),
                    "UTF-8");
            prop.load(inputStreamReader);
            return prop;
        } catch (IOException e) {
            log.error("", e);
        }

        return null;
    }
}
