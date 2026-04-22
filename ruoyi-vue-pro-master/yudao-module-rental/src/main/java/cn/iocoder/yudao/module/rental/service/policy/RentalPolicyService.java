package cn.iocoder.yudao.module.rental.service.policy;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalCancelPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalDepositPolicyDO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalReservePolicyDO;

import java.util.List;

/**
 * 租车政策管理 Service 接口
 * 包含：预定政策 + 取用车政策（押金政策）+ 取消政策
 */
public interface RentalPolicyService {

    // ========== 预定政策 ==========

    Long createReservePolicy(RentalReservePolicyDO policy);

    void updateReservePolicy(RentalReservePolicyDO policy);

    void deleteReservePolicy(Long id);

    RentalReservePolicyDO getReservePolicy(Long id);

    List<RentalReservePolicyDO> getReservePolicyList();

    List<RentalReservePolicyDO> getReservePolicyListByFranchisee(Long franchiseeId);

    void enableReservePolicy(Long id);

    void disableReservePolicy(Long id);

    // ========== 取用车政策 - 押金政策 ==========

    Long createDepositPolicy(RentalDepositPolicyDO policy);

    void updateDepositPolicy(RentalDepositPolicyDO policy);

    void deleteDepositPolicy(Long id);

    RentalDepositPolicyDO getDepositPolicy(Long id);

    List<RentalDepositPolicyDO> getDepositPolicyList();

    List<RentalDepositPolicyDO> getDepositPolicyListByFranchisee(Long franchiseeId);

    void enableDepositPolicy(Long id);

    void disableDepositPolicy(Long id);

    // ========== 取消政策 ==========

    Long createCancelPolicy(RentalCancelPolicyDO policy);

    void updateCancelPolicy(RentalCancelPolicyDO policy);

    void deleteCancelPolicy(Long id);

    RentalCancelPolicyDO getCancelPolicy(Long id);

    List<RentalCancelPolicyDO> getCancelPolicyList();

    List<RentalCancelPolicyDO> getCancelPolicyListByFranchisee(Long franchiseeId);

    void enableCancelPolicy(Long id);

    void disableCancelPolicy(Long id);

}
