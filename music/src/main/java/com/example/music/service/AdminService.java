package com.example.music.service;

import com.example.music.domain.Admin;

//管理员service接口
public interface AdminService {
    //验证密码是否正确
    public int verifyPassword(String username,String password);
    //修改密码
    public boolean updatePassword(Admin admin);
}
