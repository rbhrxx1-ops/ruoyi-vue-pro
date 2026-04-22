package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 租车订单 DO
 */
@TableName("rental_order")
@KeySequence("rental_order_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalOrderDO extends BaseDO {

    /** 订单编号 */
    @TableId
    private Long id;
    /** 订单号 */
    private String orderNo;
    /** 用户编号 */
    private Long userId;
    /** 所属门店编号 */
    private Long storeId;
    /** 车辆编号 */
    private Long vehicleId;
    /**
     * 订单状态
     * 10-待支付, 20-已支付/待取车, 30-用车中,
     * 40-已还车/待结算, 50-已完成, 60-已取消
     */
    private Integer status;
    /** 订单总金额（分） */
    private Integer totalAmount;
    /** 实际支付金额（分） */
    private Integer payAmount;
    /** 预计取车时间 */
    private LocalDateTime startTime;
    /** 预计还车时间 */
    private LocalDateTime endTime;
    /** 实际取车时间 */
    private LocalDateTime actualStartTime;
    /** 实际还车时间 */
    private LocalDateTime actualEndTime;
    /** 取车门店编号 */
    private Long pickupStoreId;
    /** 还车门店编号 */
    private Long returnStoreId;
    /** 渠道来源 */
    private String channelSource;
    /** 备注 */
    private String remark;
}
