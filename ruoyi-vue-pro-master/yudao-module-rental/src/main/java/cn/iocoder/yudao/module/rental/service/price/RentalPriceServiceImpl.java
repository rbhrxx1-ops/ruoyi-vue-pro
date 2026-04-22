package cn.iocoder.yudao.module.rental.service.price;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCarRentRuleDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalPickoffRuleDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalCarRentRuleMapper;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalPickoffRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 租车价格中心 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalPriceServiceImpl implements RentalPriceService {

    @Resource
    private RentalCarRentRuleMapper carRentRuleMapper;

    @Resource
    private RentalPickoffRuleMapper pickoffRuleMapper;

    // ========== 车辆租金费率 ==========

    @Override
    public Long createCarRentRule(RentalCarRentRuleDO rule) {
        carRentRuleMapper.insert(rule);
        return rule.getId();
    }

    @Override
    public void updateCarRentRule(RentalCarRentRuleDO rule) {
        carRentRuleMapper.updateById(rule);
    }

    @Override
    public void deleteCarRentRule(Long id) {
        carRentRuleMapper.deleteById(id);
    }

    @Override
    public RentalCarRentRuleDO getCarRentRule(Long id) {
        return carRentRuleMapper.selectById(id);
    }

    @Override
    public List<RentalCarRentRuleDO> getCarRentRuleList() {
        return carRentRuleMapper.selectList();
    }

    @Override
    public List<RentalCarRentRuleDO> getCarRentRuleListByStore(Long storeId) {
        return carRentRuleMapper.selectListByStore(storeId);
    }

    @Override
    public void enableCarRentRule(Long id) {
        RentalCarRentRuleDO rule = new RentalCarRentRuleDO();
        rule.setId(id);
        rule.setStatus(1);
        carRentRuleMapper.updateById(rule);
    }

    @Override
    public void disableCarRentRule(Long id) {
        RentalCarRentRuleDO rule = new RentalCarRentRuleDO();
        rule.setId(id);
        rule.setStatus(0);
        carRentRuleMapper.updateById(rule);
    }

    // ========== 异地取还费率 ==========

    @Override
    public Long createPickoffRule(RentalPickoffRuleDO rule) {
        pickoffRuleMapper.insert(rule);
        return rule.getId();
    }

    @Override
    public void updatePickoffRule(RentalPickoffRuleDO rule) {
        pickoffRuleMapper.updateById(rule);
    }

    @Override
    public void deletePickoffRule(Long id) {
        pickoffRuleMapper.deleteById(id);
    }

    @Override
    public RentalPickoffRuleDO getPickoffRule(Long id) {
        return pickoffRuleMapper.selectById(id);
    }

    @Override
    public List<RentalPickoffRuleDO> getPickoffRuleList() {
        return pickoffRuleMapper.selectList();
    }

    @Override
    public List<RentalPickoffRuleDO> getPickoffRuleListByStores(Long pickupStoreId, Long returnStoreId) {
        return pickoffRuleMapper.selectListByPickupAndReturn(pickupStoreId, returnStoreId);
    }

    @Override
    public void enablePickoffRule(Long id) {
        RentalPickoffRuleDO rule = new RentalPickoffRuleDO();
        rule.setId(id);
        rule.setStatus(1);
        pickoffRuleMapper.updateById(rule);
    }

    @Override
    public void disablePickoffRule(Long id) {
        RentalPickoffRuleDO rule = new RentalPickoffRuleDO();
        rule.setId(id);
        rule.setStatus(0);
        pickoffRuleMapper.updateById(rule);
    }

    @Override
    public void batchEnablePickoffRule(List<Long> ids) {
        ids.forEach(this::enablePickoffRule);
    }

    @Override
    public void batchDisablePickoffRule(List<Long> ids) {
        ids.forEach(this::disablePickoffRule);
    }

}
