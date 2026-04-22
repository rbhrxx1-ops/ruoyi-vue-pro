package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 保险商品 DO
 *
 * @author Rental Module
 */
@TableName("rental_insurance")
@KeySequence("rental_insurance_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalInsuranceDO extends BaseDO {

    /** 主键ID */
    @TableId
    private Long id;

    /** 保险名称 */
    private String insuranceName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 加盟商名称 */
    private String franchiseeName;

    /**
     * 保险类型
     * 枚举：基本险 / 综合险 / 全额险
     */
    private String insuranceType;

    /**
     * 适用渠道（JSON数组）
     * 例如：["Klook","offline","zuzuche","QEEQ","DISCOVERCARS","Gogoout","carmore","Economy Bookings"]
     */
    private String applicableChannels;

    /**
     * 包含保险项目（JSON数组）
     * 例如：["CDW-车辆碰撞险","TPL-第三者责任险","TP-盗窃险"]
     */
    private String insuranceItems;

    /**
     * 状态
     * 0-禁用 1-启用
     */
    private Integer status;

    /** 最后更新时间 */
    private LocalDateTime lastUpdateTime;

}
