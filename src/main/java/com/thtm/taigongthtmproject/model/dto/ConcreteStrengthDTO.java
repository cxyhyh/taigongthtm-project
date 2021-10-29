package com.thtm.taigongthtmproject.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.model.dto
 * @Class ConcreteStrengthDTO
 * @Date 2021/9/30 11:30
 */
@Data
public class ConcreteStrengthDTO {

    private int id;

    private double R27;

    private double R28;

    private Date createTime;
}
