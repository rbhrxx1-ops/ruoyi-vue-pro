package cn.iocoder.yudao.module.rental.service.insurance;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInsuranceDO;

import java.util.List;

/**
 * 保险商品 Service 接口
 */
public interface RentalInsuranceService {

    Long createInsurance(RentalInsuranceDO insurance);

    void updateInsurance(RentalInsuranceDO insurance);

    void deleteInsurance(Long id);

    RentalInsuranceDO getInsurance(Long id);

    List<RentalInsuranceDO> getInsuranceList();

    List<RentalInsuranceDO> getInsuranceListByFranchisee(Long franchiseeId);

    void enableInsurance(Long id);

    void disableInsurance(Long id);

}
