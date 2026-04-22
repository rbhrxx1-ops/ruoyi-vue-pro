package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车型库存 DO
 * 对应 FleetEdge：租车库存管理 > 车型库存管理
 *
 * @author Rental Module
 */
@TableName("rental_inventory")
@KeySequence("rental_inventory_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalInventoryDO extends BaseDO {

    /** 主键ID */
    @TableId
    private Long id;

    /** 门店ID */
    private Long storeId;

    /** 门店名称 */
    private String storeName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 车型组（如：ECAR、CCAR、ICAR、SCAR、FCAR 等） */
    private String vehicleGroup;

    /** SIPP Code（如：ECMR、CCMR、ICMR 等） */
    private String sippCode;

    /** 车型名称（如：Toyota Roomy、Toyota Sienta 等） */
    private String vehicleName;

    /** 总库存数量 */
    private Integer totalStock;

    /** 可用库存数量 */
    private Integer availableStock;

    /** 已预订数量 */
    private Integer reservedStock;

    /** 维修中数量 */
    private Integer maintenanceStock;

}
