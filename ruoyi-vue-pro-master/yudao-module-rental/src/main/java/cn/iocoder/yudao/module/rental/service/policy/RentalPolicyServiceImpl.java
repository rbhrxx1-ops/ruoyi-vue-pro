package cn.iocoder.yudao.module.rental.service.policy;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCancelPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDepositPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalReservePolicyDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalCancelPolicyMapper;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalDepositPolicyMapper;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalReservePolicyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 租车政策管理 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalPolicyServiceImpl implements RentalPolicyService {

    @Resource
    private RentalReservePolicyMapper reservePolicyMapper;
    @Resource
    private RentalDepositPolicyMapper depositPolicyMapper;
    @Resource
    private RentalCancelPolicyMapper cancelPolicyMapper;

    // ========== 预定政策 ==========

    @Override
    public Long createReservePolicy(RentalReservePolicyDO policy) {
        reservePolicyMapper.insert(policy);
        return policy.getId();
    }

    @Override
    public void updateReservePolicy(RentalReservePolicyDO policy) {
        reservePolicyMapper.updateById(policy);
    }

    @Override
    public void deleteReservePolicy(Long id) {
        reservePolicyMapper.deleteById(id);
    }

    @Override
    public RentalReservePolicyDO getReservePolicy(Long id) {
        return reservePolicyMapper.selectById(id);
    }

    @Override
    public List<RentalReservePolicyDO> getReservePolicyList() {
        return reservePolicyMapper.selectList();
    }

    @Override
    public List<RentalReservePolicyDO> getReservePolicyListByFranchisee(Long franchiseeId) {
        return reservePolicyMapper.selectListByFranchisee(franchiseeId);
    }

    @Override
    public void enableReservePolicy(Long id) {
        RentalReservePolicyDO p = new RentalReservePolicyDO();
        p.setId(id); p.setStatus(1);
        reservePolicyMapper.updateById(p);
    }

    @Override
    public void disableReservePolicy(Long id) {
        RentalReservePolicyDO p = new RentalReservePolicyDO();
        p.setId(id); p.setStatus(0);
        reservePolicyMapper.updateById(p);
    }

    // ========== 取用车政策 - 押金政策 ==========

    @Override
    public Long createDepositPolicy(RentalDepositPolicyDO policy) {
        depositPolicyMapper.insert(policy);
        return policy.getId();
    }

    @Override
    public void updateDepositPolicy(RentalDepositPolicyDO policy) {
        depositPolicyMapper.updateById(policy);
    }

    @Override
    public void deleteDepositPolicy(Long id) {
        depositPolicyMapper.deleteById(id);
    }

    @Override
    public RentalDepositPolicyDO getDepositPolicy(Long id) {
        return depositPolicyMapper.selectById(id);
    }

    @Override
    public List<RentalDepositPolicyDO> getDepositPolicyList() {
        return depositPolicyMapper.selectList();
    }

    @Override
    public List<RentalDepositPolicyDO> getDepositPolicyListByFranchisee(Long franchiseeId) {
        return depositPolicyMapper.selectListByFranchisee(franchiseeId);
    }

    @Override
    public void enableDepositPolicy(Long id) {
        RentalDepositPolicyDO p = new RentalDepositPolicyDO();
        p.setId(id); p.setStatus(1);
        depositPolicyMapper.updateById(p);
    }

    @Override
    public void disableDepositPolicy(Long id) {
        RentalDepositPolicyDO p = new RentalDepositPolicyDO();
        p.setId(id); p.setStatus(0);
        depositPolicyMapper.updateById(p);
    }

    // ========== 取消政策 ==========

    @Override
    public Long createCancelPolicy(RentalCancelPolicyDO policy) {
        cancelPolicyMapper.insert(policy);
        return policy.getId();
    }

    @Override
    public void updateCancelPolicy(RentalCancelPolicyDO policy) {
        cancelPolicyMapper.updateById(policy);
    }

    @Override
    public void deleteCancelPolicy(Long id) {
        cancelPolicyMapper.deleteById(id);
    }

    @Override
    public RentalCancelPolicyDO getCancelPolicy(Long id) {
        return cancelPolicyMapper.selectById(id);
    }

    @Override
    public List<RentalCancelPolicyDO> getCancelPolicyList() {
        return cancelPolicyMapper.selectList();
    }

    @Override
    public List<RentalCancelPolicyDO> getCancelPolicyListByFranchisee(Long franchiseeId) {
        return cancelPolicyMapper.selectListByFranchisee(franchiseeId);
    }

    @Override
    public void enableCancelPolicy(Long id) {
        RentalCancelPolicyDO p = new RentalCancelPolicyDO();
        p.setId(id); p.setStatus(1);
        cancelPolicyMapper.updateById(p);
    }

    @Override
    public void disableCancelPolicy(Long id) {
        RentalCancelPolicyDO p = new RentalCancelPolicyDO();
        p.setId(id); p.setStatus(0);
        cancelPolicyMapper.updateById(p);
    }

}
