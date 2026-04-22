package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自助取还服务区域 DO
 * 对应 FleetEdge：自助取还 > 服务区域管理
 *
 * @author Rental Module
 */
@TableName("rental_self_service_region")
@KeySequence("rental_self_service_region_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalSelfServiceRegionDO extends BaseDO {

    @TableId
    private Long id;

    /** 区域名称 */
    private String regionName;

    /** 区域编码 */
    private String regionCode;

    /** 所属门店ID */
    private Long storeId;

    /** 所属门店名称 */
    private String storeName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 区域描述 */
    private String regionDesc;

    /** 区域地址 */
    private String address;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 服务半径（米） */
    private Integer serviceRadius;

    /**
     * 状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
