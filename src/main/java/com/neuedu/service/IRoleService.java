package com.neuedu.service;

import com.neuedu.pojo.Result;
import com.neuedu.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
public interface IRoleService extends IService<Role> {

    Result all();
}
