package com.example.music.service;

import com.example.music.domain.Consumer;

import java.util.List;

public interface ConsumerService {
    //增加
    public boolean insert(Consumer consumer);
    //修改
    public boolean update(Consumer consumer);
    //删除
    public boolean delete(Integer id);
    //查询整个对象
    public Consumer selectByPrimaryKey(Integer id);
    //查询所有用户
    public List<Consumer> allConsumer();
    //修改密码
    public boolean verifyPassword(String username, String password);
    //根据用户名查询
    public Consumer getByUsername(String username);
}
