package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 取用车政策 - 押金政策 DO
 * 对应 FleetEdge：租车政策管理 > 取用车政策 > 押金政策 Tab
 * 优先级：指定具体车型 > 仅指定车型组 > 不指定车型
 *
 * @author Rental Module
 */
@TableName("rental_deposit_policy")
@KeySequence("rental_deposit_policy_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalDepositPolicyDO extends BaseDO {

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

    /** 适用门店名称 */
    private String storeName;

    /** 车型组（null=所有车型组） */
    private String vehicleGroup;

    /** SIPP Code（null=所有） */
    private String sippCode;

    /**
     * 是否免押金
     * true-免押金 false-需要押金
     */
    private Boolean depositFree;

    /**
     * 押金收取方式
     * 枚举：credit_card-信用卡预授权 / cash-现金 / online-线上支付
     */
    private String depositMethod;

    /** 押金金额（免押金时为0） */
    private BigDecimal depositAmount;

    /** 押金币种 */
    private String currency;

    /**
     * 超里程限制（km/天，0=不限）
     */
    private Integer mileageLimit;

    /** 超里程费用（每公里） */
    private BigDecimal extraMileageFee;

    /**
     * 规则状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
