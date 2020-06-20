package com.neuedu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    /*
    * 返回的状态码 0不成功 1成功
    * */
    private Integer code;
    /*返回值*/
    private Object obj;
    /*返回文字消息*/
    private String txt;


}
