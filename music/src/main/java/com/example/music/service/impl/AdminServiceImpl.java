package com.example.music.service.impl;

import com.example.music.dao.AdminMapper;
import com.example.music.domain.Admin;
import com.example.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    /**
     * 验证密码是否正确
     * @param username
     * @param password
     * @return
     */
    @Override
    public int verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username, password);
    }

    /**
     * @param admin
     * @return
     */
    @Override
    public boolean updatePassword(Admin admin) {
        return adminMapper.updatePassword(admin)>0;
    }
}
