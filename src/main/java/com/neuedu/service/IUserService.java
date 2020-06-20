package com.neuedu.service;

import com.neuedu.pojo.Result;
import com.neuedu.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface IUserService extends IService<User> {
    Result login(User user);

    Result checkExist(User user);

    Result register(User user) throws IOException;

    Result changeTag(Integer tagNum) throws IOException;

    Result changePassword(String pass, String changedPass);

    Result allAdminRegReq();

    Result verifyAdmin(Integer id, Integer res);

    Result changeStatus(Integer id, Integer status);
}
