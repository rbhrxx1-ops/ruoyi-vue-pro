package cn.iocoder.yudao.module.rental.service.price;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCarRentRuleDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalPickoffRuleDO;

import java.util.List;

/**
 * 租车价格中心 Service 接口
 * 包含：车辆租金费率 + 异地取还费率
 */
public interface RentalPriceService {

    // ========== 车辆租金费率 ==========

    Long createCarRentRule(RentalCarRentRuleDO rule);

    void updateCarRentRule(RentalCarRentRuleDO rule);

    void deleteCarRentRule(Long id);

    RentalCarRentRuleDO getCarRentRule(Long id);

    List<RentalCarRentRuleDO> getCarRentRuleList();

    List<RentalCarRentRuleDO> getCarRentRuleListByStore(Long storeId);

    void enableCarRentRule(Long id);

    void disableCarRentRule(Long id);

    // ========== 异地取还费率 ==========

    Long createPickoffRule(RentalPickoffRuleDO rule);

    void updatePickoffRule(RentalPickoffRuleDO rule);

    void deletePickoffRule(Long id);

    RentalPickoffRuleDO getPickoffRule(Long id);

    List<RentalPickoffRuleDO> getPickoffRuleList();

    List<RentalPickoffRuleDO> getPickoffRuleListByStores(Long pickupStoreId, Long returnStoreId);

    void enablePickoffRule(Long id);

    void disablePickoffRule(Long id);

    void batchEnablePickoffRule(List<Long> ids);

    void batchDisablePickoffRule(List<Long> ids);

}
