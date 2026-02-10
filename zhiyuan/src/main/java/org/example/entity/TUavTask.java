package org.example.entity;

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
 * 无人机配送任务表
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_uav_task")
@ApiModel(value="TUavTask对象", description="无人机配送任务表")
public class TUavTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单ID(一个订单最多一个任务)")
    private Long orderId;

    @ApiModelProperty(value = "无人机ID")
    private Long uavId;

    @ApiModelProperty(value = "任务状态(WAITING/FLYING/ARRIVED/FAILED)")
    private String taskStatus;

    @ApiModelProperty(value = "起飞时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "完成时间")
    private LocalDateTime endTime;


}
