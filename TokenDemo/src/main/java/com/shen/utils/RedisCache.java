package com.shen.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Shen
 * @date 2022/12/18 9:38
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 过期时间
     * @param timeUnit 时间颗粒度（天、小时、分钟、秒等）
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间，不写默认时间单位为秒
     *
     * @param key 键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout){
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间，手动设置时间单位
     *
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间粒度
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, TimeUnit timeUnit){
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    /**
     * 获取缓存的基本对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key){
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存的键值
     * @return true=删除成功；false=删除失败
     */
    public boolean deleteObject(final String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection){
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存键值
     * @param dataList 对应的集合数据
     * @return 集合中数据的个数
     */
    public <T> long setCacheList(final String key, final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获取缓存的List对象
     *
     * @param key 缓存的键值
     * @return 对应的数据
     */
    public <T> List<T> getCacheList(final String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key
     * @param dataset
     * @return
     * @param <T>
     */
    public <T>BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataset){
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        for (T t : dataset) {
            setOperations.add(t);
        }
        return setOperations;
    }

    /**
     * 获取缓存的Set
     *
     * @param key
     * @return
     * @param <T>
     */
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @param <T>
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap){
        if(dataMap != null){
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     * @param <T>
     */
    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向缓存的Hash中存入数据
     *
     * @param key
     * @param hKey
     * @param value
     * @param <T>
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value){
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取缓存的Hash中的数据
     *
     * @param key
     * @param hKey
     * @return
     * @param <T>
     */
    public <T> T getCacheMapValue(final String key, final String hKey){
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, hKey);
    }

    /**
     * 删除缓存的Hash中的数据
     *
     * @param key
     * @param hKey
     */
    public void delCacheMapValue(final String key, final String hKey){
        redisTemplate.opsForHash().delete(key, hKey);
    }

    /**
     * 获取缓存的Hash中的多个数据
     *
     * @param key
     * @param hKeys
     * @return
     * @param <T>
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys){
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获取缓存的基本对象列表（缓存的键值集合）
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }
}
