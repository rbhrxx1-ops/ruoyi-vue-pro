package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 租车车辆 DO
 */
@TableName("rental_vehicle")
@KeySequence("rental_vehicle_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalVehicleDO extends BaseDO {

    @TableId
    private Long id;
    /** 车牌号 */
    private String plateNo;
    /** 车架号 */
    private String vin;
    /** 车型编号 */
    private Long modelId;
    /** 所属门店编号 */
    private Long storeId;
    /**
     * 车辆状态
     * 10-空闲, 20-已预订, 30-租赁中, 40-维修/保养, 50-停运
     */
    private Integer status;
    /** 颜色 */
    private String color;
    /** 里程数(km) */
    private Integer mileage;
    /** 油量/电量百分比 */
    private Integer fuelLevel;
    /** 备注 */
    private String remark;
}
