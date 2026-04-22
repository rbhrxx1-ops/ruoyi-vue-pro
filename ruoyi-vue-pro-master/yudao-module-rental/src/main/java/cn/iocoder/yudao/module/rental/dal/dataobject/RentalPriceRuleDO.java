package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

/**
 * 租车价格规则 DO
 */
@TableName("rental_price_rule")
@KeySequence("rental_price_rule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalPriceRuleDO extends BaseDO {

    @TableId
    private Long id;
    /** 车型编号 */
    private Long modelId;
    /** 门店编号 */
    private Long storeId;
    /**
     * 规则类型
     * 1-基础日租金, 2-节假日附加费, 3-周末附加费, 4-异地取还费
     */
    private Integer ruleType;
    /** 价格（分） */
    private Integer price;
    /** 生效开始日期 */
    private LocalDate startDate;
    /** 生效结束日期 */
    private LocalDate endDate;
    /** 状态（0-停用, 1-启用） */
    private Integer status;
}
