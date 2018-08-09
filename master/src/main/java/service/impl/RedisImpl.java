package service.impl;

import com.alibaba.fastjson.JSON;
import constants.ExceptionConst;
import entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import service.RedisFoctory;

import javax.annotation.Resource;
import java.util.List;

@Service("redisImpl")
public class RedisImpl implements RedisFoctory {
    private Logger log = LoggerFactory.getLogger(RedisImpl.class);

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public Long getDbSize() throws Exception {
        try {
            Long dbSize = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    return redisConnection.dbSize();
                }
            });

            return dbSize;
        } catch (Exception e) {
            throw new Exception(String.format(ExceptionConst.MSG_ERROR_DB, "get redis db size", e.getMessage()));
        }
    }

    @Override
    public void flushDb() throws Exception {
        try {
            redisTemplate.execute(new RedisCallback<Void>() {
                @Override
                public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.flushDb();
                    return null;
                }
            });
        } catch (Exception e) {
            throw new Exception(String.format(ExceptionConst.MSG_ERROR_DB, "clear redis db", e.getMessage()));
        }
    }

    @Override
    public void batchInsert(List<TaskEntity> tasks) throws Exception {
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        try {
            redisTemplate.executePipelined(new RedisCallback<List<TaskEntity>>() {
                @Override
                public List<TaskEntity> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    for (int i = 0, length = tasks.size(); i < length; i++) {
                        // 拼接task_name + 时间戳作为任务记录的key
                        byte[] rawKey = serializer.serialize(new StringBuffer(tasks.get(i).getTaskName())
                                .append("-").append(System.currentTimeMillis()).toString());
                        redisConnection.setEx(rawKey, 86400L, serializer.serialize(JSON.toJSONString(tasks.get(i))));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            throw new Exception(String.format(ExceptionConst.MSG_ERROR_DB, "generate redis task", e.getMessage()));
        }
    }

    @Override
    public void cyclicInsert(List<TaskEntity> tasks) throws Exception {
        try {
            for (int i = 0, length = tasks.size(); i < length; i++) {
                redisTemplate.opsForValue().set(new StringBuffer(tasks.get(i).getTaskName()).append("-")
                        .append(System.currentTimeMillis()).toString(), JSON.toJSONString(tasks.get(i)));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            throw new Exception(String.format(ExceptionConst.MSG_ERROR_DB, "generate redis task", e.getMessage()));
        }
    }
}
