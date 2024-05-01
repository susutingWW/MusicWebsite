package com.example.music.dao;

import com.example.music.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper {
    //增加
    public int insert(Consumer consumer);
    //修改
    public int update(Consumer consumer);
    //删除
    public int delete(Integer id);
    //查询整个对象
    public Consumer selectByPrimaryKey(Integer id);
    //查询所有用户
    public List<Consumer> allConsumer();
    //修改密码
    public int verifyPassword(String username, String password);
    //根据用户名查询
    public Consumer getByUsername(String username);
}
