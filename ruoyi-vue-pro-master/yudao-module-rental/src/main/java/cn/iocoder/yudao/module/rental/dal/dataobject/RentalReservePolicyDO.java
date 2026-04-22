package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 预定政策 DO
 * 对应 FleetEdge：租车政策管理 > 预定政策
 * Tab1: 预定时间限制
 * Tab2: 驾龄及年龄限制
 *
 * @author Rental Module
 */
@TableName("rental_reserve_policy")
@KeySequence("rental_reserve_policy_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalReservePolicyDO extends BaseDO {

    @TableId
    private Long id;

    /** 规则编号 */
    private String ruleNo;

    /** 规则名称 */
    private String ruleName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 加盟商名称 */
    private String franchiseeName;

    /** 适用门店ID（null=所有门店） */
    private Long storeId;

    /** 适用门店名称 */
    private String storeName;

    /** 最早预定期数值 */
    private Integer earliestBookingValue;

    /** 最早预定期单位（hour/day/month） */
    private String earliestBookingUnit;

    /** 最晚预定期数值 */
    private Integer latestBookingValue;

    /** 最晚预定期单位（hour/day/month） */
    private String latestBookingUnit;

    /** 最小租期数值 */
    private Integer minRentalValue;

    /** 最小租期单位（hour/day） */
    private String minRentalUnit;

    /** 最小驾龄（年） */
    private Integer minDrivingYears;

    /** 最小年龄 */
    private Integer minAge;

    /** 最大年龄 */
    private Integer maxAge;

    /**
     * 规则状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
