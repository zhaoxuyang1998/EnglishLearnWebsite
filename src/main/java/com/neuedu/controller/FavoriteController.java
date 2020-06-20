package com.neuedu.controller;


import com.neuedu.pojo.Result;
import com.neuedu.service.IFavoriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Resource
    IFavoriteService favoriteService;

    @GetMapping("/myAllFavorite")
    public Result myFavorites(Integer typeNum,Integer pageNum,Integer pageSize){
        return favoriteService.myFavorites(typeNum,pageNum,pageSize);
    }
}
