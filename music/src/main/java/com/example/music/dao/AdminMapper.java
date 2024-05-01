package com.example.music.dao;

import com.example.music.domain.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    //验证密码是否正确
    public int verifyPassword(String username,String password);
    //修改密码
    public int updatePassword(Admin admin);
}
