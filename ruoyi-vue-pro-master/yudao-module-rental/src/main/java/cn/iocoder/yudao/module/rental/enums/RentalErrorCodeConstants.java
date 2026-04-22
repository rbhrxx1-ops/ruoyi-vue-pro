package cn.iocoder.yudao.module.rental.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 租车模块错误码常量
 * 错误码区间：1-030-000-000 ~ 1-030-999-999
 */
public interface RentalErrorCodeConstants {

    // ========== 订单相关 ==========
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1_030_001_000, "租车订单不存在");
    ErrorCode ORDER_STATUS_ERROR = new ErrorCode(1_030_001_001, "订单状态不正确");

    // ========== 车辆相关 ==========
    ErrorCode VEHICLE_NOT_EXISTS = new ErrorCode(1_030_002_000, "车辆不存在");
    ErrorCode VEHICLE_NOT_AVAILABLE = new ErrorCode(1_030_002_001, "车辆不可用");

    // ========== 库存相关 ==========
    ErrorCode INVENTORY_NOT_ENOUGH = new ErrorCode(1_030_003_000, "库存不足");

    // ========== 价格相关 ==========
    ErrorCode PRICE_RULE_NOT_EXISTS = new ErrorCode(1_030_004_000, "价格规则不存在");

    // ========== 商品相关 ==========
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1_030_005_000, "租车商品不存在");

    // ========== 政策相关 ==========
    ErrorCode POLICY_NOT_EXISTS = new ErrorCode(1_030_006_000, "租车政策不存在");

    // ========== 门店相关 ==========
    ErrorCode STORE_NOT_EXISTS = new ErrorCode(1_030_007_000, "门店不存在");

    // ========== 加盟商相关 ==========
    ErrorCode FRANCHISEE_NOT_EXISTS = new ErrorCode(1_030_008_000, "加盟商不存在");

    // ========== 自助设备相关 ==========
    ErrorCode SELF_DEVICE_NOT_EXISTS = new ErrorCode(1_030_009_000, "自助取还设备不存在");
}
