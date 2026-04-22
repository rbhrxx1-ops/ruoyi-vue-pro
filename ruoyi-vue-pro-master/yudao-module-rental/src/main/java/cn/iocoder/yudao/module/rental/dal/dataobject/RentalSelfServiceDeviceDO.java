package cn.iocoder.yudao.module.rental.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自助取还设备（Qube）DO
 * 对应 FleetEdge：自助取还 > 设备管理
 *
 * @author Rental Module
 */
@TableName("rental_self_service_device")
@KeySequence("rental_self_service_device_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RentalSelfServiceDeviceDO extends BaseDO {

    @TableId
    private Long id;

    /** 设备编号 */
    private String deviceNo;

    /** 设备名称 */
    private String deviceName;

    /** 所属门店ID */
    private Long storeId;

    /** 所属门店名称 */
    private String storeName;

    /** 加盟商ID */
    private Long franchiseeId;

    /** 设备型号 */
    private String deviceModel;

    /** 设备序列号 */
    private String serialNo;

    /** 安装位置描述 */
    private String locationDesc;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /**
     * 设备状态
     * 枚举：online-在线 / offline-离线 / maintenance-维护中
     */
    private String deviceStatus;

    /**
     * 启用状态
     * 0-禁用 1-启用
     */
    private Integer status;

}
