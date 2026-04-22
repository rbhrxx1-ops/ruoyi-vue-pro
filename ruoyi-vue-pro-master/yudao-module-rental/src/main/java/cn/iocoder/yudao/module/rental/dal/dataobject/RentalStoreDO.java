package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;

/**
 * 租车门店 DO
 */
@TableName("rental_store")
@KeySequence("rental_store_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalStoreDO extends BaseDO {

    @TableId
    private Long id;
    /** 门店名称 */
    private String name;
    /** 加盟商编号（null 为直营） */
    private Long franchiseeId;
    /** 城市编号 */
    private Integer cityId;
    /** 详细地址 */
    private String address;
    /** 经度 */
    private BigDecimal longitude;
    /** 纬度 */
    private BigDecimal latitude;
    /** 联系电话 */
    private String contactPhone;
    /** 营业时间 */
    private String businessHours;
    /** 状态（0-停业, 1-营业） */
    private Integer status;
}
