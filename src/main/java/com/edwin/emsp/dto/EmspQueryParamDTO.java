package com.edwin.emsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmspQueryParamDTO implements Serializable {

    private Date lasteUpdate;

    private Integer page;

    private Integer size;


}
