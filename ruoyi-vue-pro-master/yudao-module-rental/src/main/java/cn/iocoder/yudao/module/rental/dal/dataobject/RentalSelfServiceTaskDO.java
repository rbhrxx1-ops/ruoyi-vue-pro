package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 自助取还任务 DO
 * 对应 FleetEdge：自助取还 > 自助取还任务
 *
 * @author Rental Module
 */
@TableName("rental_self_service_task")
@KeySequence("rental_self_service_task_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalSelfServiceTaskDO extends BaseDO {

    @TableId
    private Long id;

    /** 任务编号 */
    private String taskNo;

    /** 关联订单ID */
    private Long orderId;

    /** 关联订单号 */
    private String orderNo;

    /** 设备ID */
    private Long deviceId;

    /** 设备编号 */
    private String deviceNo;

    /** 门店ID */
    private Long storeId;

    /** 门店名称 */
    private String storeName;

    /** 车牌号 */
    private String licensePlate;

    /** 客户姓名 */
    private String customerName;

    /** 客户联系电话 */
    private String customerPhone;

    /**
     * 任务类型
     * 枚举：pickup-自助取车 / return-自助还车
     */
    private String taskType;

    /**
     * 任务状态
     * 枚举：pending-待执行 / in_progress-执行中 / completed-已完成 / failed-失败 / cancelled-已取消
     */
    private String taskStatus;

    /** 计划执行时间 */
    private LocalDateTime scheduledTime;

    /** 实际执行时间 */
    private LocalDateTime actualTime;

    /** 备注 */
    private String remark;

}
