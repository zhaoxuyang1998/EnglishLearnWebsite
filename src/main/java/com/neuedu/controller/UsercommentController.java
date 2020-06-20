package com.neuedu.controller;


import com.neuedu.pojo.Result;
import com.neuedu.pojo.Usercomment;
import com.neuedu.service.IUsercommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/usercomment")
public class UsercommentController {

    @Resource
    IUsercommentService usercommentService;

    @GetMapping("/addComment")
    public Result addComment(Usercomment usercomment,Integer typeNum) throws IOException {
        return usercommentService.addComment(usercomment,typeNum);
    }
}
