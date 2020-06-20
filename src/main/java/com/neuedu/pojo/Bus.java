package com.neuedu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Bus implements Serializable {
    //id自动生成
    @TableId(type= IdType.AUTO)
    private Integer id;
    @TableField(exist = false)
    private Integer pageNo=1;
    @TableField(exist = false)
    private Integer pageSize=10;
    /*0则不分页，1则分页*/
    @TableField(exist = false)
    private Integer withPage=1;
}
