package com.tkx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 同步方式使用redis
 * @Author tkx
 * @Date 2024 12 15 21 05
 **/
@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setKey(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }


    public Object getKey(String key){
        return redisTemplate.opsForValue().get(key);
    }


    public void setList(String key, List<String> values){

        for (int i = 0; i < values.size(); i++) {
            redisTemplate.opsForList().rightPush(key,values.get(i));
        }
    }


    public List<Object> getList(String key){
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        return range;
    }

    public void updateList(String key,Integer index ,Object value){
        try {
            redisTemplate.opsForList().set(key,index,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
