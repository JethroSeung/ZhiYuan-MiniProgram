package org.example.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 无人机表
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_uav")
@ApiModel(value="TUav对象", description="无人机表")
public class TUav implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "无人机ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "无人机编号(UAV-01)")
    private String uavCode;

    @ApiModelProperty(value = "无人机型号")
    private String model;

    @ApiModelProperty(value = "最大载重(kg)")
    private BigDecimal maxLoad;

    @ApiModelProperty(value = "电量(0-100)")
    private Integer battery;

    @ApiModelProperty(value = "状态(ONLINE/OFFLINE/FLYING/CHARGING)")
    private String status;

    @ApiModelProperty(value = "当前纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "当前经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
