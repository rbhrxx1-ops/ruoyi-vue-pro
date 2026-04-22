package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 车辆租金费率规则 DO
 * 对应 FleetEdge：租车价格中心 > 车辆租金费率
 *
 * @author Rental Module
 */
@TableName("rental_car_rent_rule")
@KeySequence("rental_car_rent_rule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalCarRentRuleDO extends BaseDO {

    @TableId
    private Long id;

    /** 规则名称（如：[成田]-成田10-4） */
    private String ruleName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 加盟商名称 */
    private String franchiseeName;

    /** 取车门店ID */
    private Long pickupStoreId;

    /** 取车门店名称 */
    private String pickupStoreName;

    /** 车型组 */
    private String vehicleGroup;

    /** SIPP Code */
    private String sippCode;

    /** 日租金（基础价格） */
    private BigDecimal dailyRate;

    /** 币种 */
    private String currency;

    /** 有效期开始日期 */
    private LocalDate validFrom;

    /** 有效期结束日期 */
    private LocalDate validTo;

    /**
     * 规则状态
     * 0-禁用 1-启用
     */
    private Integer status;

    /** 费率详情JSON（含分时段费率、周末费率等） */
    private String rateDetail;

}
