package com.neuedu.controller;


import com.neuedu.config.PassToken;
import com.neuedu.factory.ConstantFactory;
import com.neuedu.service.IDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Resource
    IDictService dictService;

    @GetMapping("allByKeyword")
    @PassToken
    public Object allByKeyword(String keyword) throws IOException {
        System.out.println(keyword);
        //return dictService.allByKeyword(keyword);
        return dictService.allByKeyword(keyword);
    }

}
