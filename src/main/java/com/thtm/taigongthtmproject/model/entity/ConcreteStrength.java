package com.thtm.taigongthtmproject.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.model.entity
 * @Class ConcreteStrength
 * @Date 2021/9/30 11:30
 */
@Data
@TableName("concrete_strength")
public class ConcreteStrength implements Serializable {

    @TableId(value = "id")
    private int id;

    @TableField(value = "R7")
    private double R7;

    @TableField(value = "R28")
    private double R28;

    @TableField(value = "R28_real")
    private double R28Real;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
