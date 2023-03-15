package com.codingallen.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codingallen.sys.entity.User;
import com.codingallen.sys.mapper.UserMapper;
import com.codingallen.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CodingAllen
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> login(User user) {
        //サッチbyユーザnameとパスワード
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);

        //結果はnullじやない場合　tokenを生成する
        if(loginUser != null){
            //tokennを生成、UUID
            String key = "user:" + UUID.randomUUID();
            //redisに保存
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            //データを返す
            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }
    @Override
    public Map<String,Object> getUserInfo(String token){
        //tokenを通じてユーザ情報をゲット
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
           User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
           Map<String,Object> data = new HashMap<>();
           data.put("name",loginUser.getUsername());
           data.put("avatar",loginUser.getAvatar());
           //役割roles
            List<String> roleList =  this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
            return data;
        }
        return null;
    }
//logout
    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
