package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 异地取还费率规则 DO
 * 对应 FleetEdge：租车价格中心 > 异地取还费率
 *
 * @author Rental Module
 */
@TableName("rental_pickoff_rule")
@KeySequence("rental_pickoff_rule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalPickoffRuleDO extends BaseDO {

    @TableId
    private Long id;

    /** 规则名称 */
    private String ruleName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 加盟商名称 */
    private String franchiseeName;

    /** 取车门店ID */
    private Long pickupStoreId;

    /** 取车门店名称 */
    private String pickupStoreName;

    /** 还车门店ID */
    private Long returnStoreId;

    /** 还车门店名称 */
    private String returnStoreName;

    /** 车型组 */
    private String vehicleGroup;

    /** SIPP Code */
    private String sippCode;

    /**
     * 支付方式
     * 枚举：prepaid-预付 / postpaid-到店付
     */
    private String paymentType;

    /** 异地取还费用 */
    private BigDecimal fee;

    /** 币种 */
    private String currency;

    /**
     * 规则状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
