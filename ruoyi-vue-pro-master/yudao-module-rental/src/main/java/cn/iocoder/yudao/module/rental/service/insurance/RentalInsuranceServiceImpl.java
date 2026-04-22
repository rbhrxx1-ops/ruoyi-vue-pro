package cn.iocoder.yudao.module.rental.service.insurance;

import cn.iocoder.yudao.module.rental.dal.dataobject.RentalInsuranceDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalInsuranceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 保险商品 Service 实现类
 */
@Service
@Validated
@Slf4j
public class RentalInsuranceServiceImpl implements RentalInsuranceService {

    @Resource
    private RentalInsuranceMapper insuranceMapper;

    @Override
    public Long createInsurance(RentalInsuranceDO insurance) {
        insuranceMapper.insert(insurance);
        return insurance.getId();
    }

    @Override
    public void updateInsurance(RentalInsuranceDO insurance) {
        insuranceMapper.updateById(insurance);
    }

    @Override
    public void deleteInsurance(Long id) {
        insuranceMapper.deleteById(id);
    }

    @Override
    public RentalInsuranceDO getInsurance(Long id) {
        return insuranceMapper.selectById(id);
    }

    @Override
    public List<RentalInsuranceDO> getInsuranceList() {
        return insuranceMapper.selectList();
    }

    @Override
    public List<RentalInsuranceDO> getInsuranceListByFranchisee(Long franchiseeId) {
        return insuranceMapper.selectListByFranchisee(franchiseeId);
    }

    @Override
    public void enableInsurance(Long id) {
        RentalInsuranceDO insurance = new RentalInsuranceDO();
        insurance.setId(id);
        insurance.setStatus(1);
        insuranceMapper.updateById(insurance);
    }

    @Override
    public void disableInsurance(Long id) {
        RentalInsuranceDO insurance = new RentalInsuranceDO();
        insurance.setId(id);
        insurance.setStatus(0);
        insuranceMapper.updateById(insurance);
    }

}
