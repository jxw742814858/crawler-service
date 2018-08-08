package service.impl;

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
    RedisTemplate<Integer, TaskEntity> redisTemplate;

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
    public void batchInsert(List<TaskEntity> rules) throws Exception {
        try {
            redisTemplate.executePipelined(new RedisCallback<List<TaskEntity>>() {
                @Override
                public List<TaskEntity> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    for (int i = 0, length = rules.size(); i < length; i++) {
                        RedisSerializer<String> keySerializer = redisTemplate.getStringSerializer();
                        RedisSerializer<TaskEntity> ruleSerializer = (RedisSerializer) redisTemplate.getDefaultSerializer();
                        byte[] rawKey = keySerializer.serialize(String.valueOf(System.currentTimeMillis()));
                        redisConnection.setEx(rawKey, 86400L, ruleSerializer.serialize(rules.get(i)));
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            throw new Exception(String.format(ExceptionConst.MSG_ERROR_DB, "generate redis task", e.getMessage()));
        }
    }
}
