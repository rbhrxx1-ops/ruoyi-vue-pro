package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 附加设备 DO
 * 对应 FleetEdge：租车商品管理 > 附加设备管理
 *
 * @author Rental Module
 */
@TableName("rental_device")
@KeySequence("rental_device_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalDeviceDO extends BaseDO {

    /** 主键ID */
    @TableId
    private Long id;

    /** 附加设备名称（如：ETC卡、防滑链、儿童座椅、雪地胎） */
    private String deviceName;

    /** 设备描述 */
    private String deviceDesc;

    /** 加盟商ID */
    private Long franchiseeId;

    /**
     * 计价方式
     * 枚举：per_use-按次计价 / per_day-按天计价 / per_rental-按租期计价
     */
    private String pricingType;

    /** 单价 */
    private BigDecimal unitPrice;

    /**
     * 币种
     * 枚举：JPY / USD / CNY / EUR 等
     */
    private String currency;

    /**
     * 状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
