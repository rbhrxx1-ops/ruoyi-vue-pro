package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 取消政策 DO
 * 对应 FleetEdge：租车政策管理 > 取消政策
 *
 * @author Rental Module
 */
@TableName("rental_cancel_policy")
@KeySequence("rental_cancel_policy_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalCancelPolicyDO extends BaseDO {

    @TableId
    private Long id;

    /** 规则名称 */
    private String ruleName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 加盟商名称 */
    private String franchiseeName;

    /** 适用门店ID（null=所有门店） */
    private Long storeId;

    /** 适用门店名称（null时显示"所有"） */
    private String storeName;

    /**
     * 是否允许取消
     * true-允许 false-不允许
     */
    private Boolean cancelAllowed;

    /**
     * N小时内下单允许退单（0=不限制，-1=不允许）
     * 对应字段：N小时内下单允许退单
     */
    private Integer cancelWithinHours;

    /**
     * 规则状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
