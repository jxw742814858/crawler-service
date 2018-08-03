package thread.impl;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class RedisImpl {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void generateTasks(String dataStr) {
        JSONArray taskDetails = null;
        if (taskDetails == null) {
            return;
        }


    }
}
