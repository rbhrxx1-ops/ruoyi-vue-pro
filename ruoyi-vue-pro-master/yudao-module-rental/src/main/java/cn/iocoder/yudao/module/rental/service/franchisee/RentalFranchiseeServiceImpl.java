package cn.iocoder.yudao.module.rental.service.franchisee;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo.*;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalFranchiseeDO;
import cn.iocoder.yudao.module.rental.dal.mysql.RentalFranchiseeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import static cn.iocoder.yudao.module.rental.enums.RentalErrorCodeConstants.FRANCHISEE_NOT_EXISTS;

@Service
@Validated
@Slf4j
public class RentalFranchiseeServiceImpl implements RentalFranchiseeService {

    @Resource
    private RentalFranchiseeMapper rentalFranchiseeMapper;

    @Override
    public Long createFranchisee(RentalFranchiseeSaveReqVO createReqVO) {
        RentalFranchiseeDO franchisee = toDO(createReqVO);
        if (franchisee.getStatus() == null) franchisee.setStatus(1);
        rentalFranchiseeMapper.insert(franchisee);
        return franchisee.getId();
    }

    @Override
    public void updateFranchisee(RentalFranchiseeSaveReqVO updateReqVO) {
        validateExists(updateReqVO.getId());
        rentalFranchiseeMapper.updateById(toDO(updateReqVO));
    }

    @Override
    public void deleteFranchisee(Long id) {
        validateExists(id);
        rentalFranchiseeMapper.deleteById(id);
    }

    @Override
    public RentalFranchiseeRespVO getFranchisee(Long id) {
        return toRespVO(validateExists(id));
    }

    @Override
    public PageResult<RentalFranchiseeRespVO> getFranchiseePage(RentalFranchiseePageReqVO pageReqVO) {
        return rentalFranchiseeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RentalFranchiseeRespVO> getFranchiseeList() {
        return rentalFranchiseeMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<RentalFranchiseeDO>()
                        .eq(RentalFranchiseeDO::getStatus, 1)
                        .orderByAsc(RentalFranchiseeDO::getId)
        ).stream().map(this::toRespVO).collect(Collectors.toList());
    }

    private RentalFranchiseeDO validateExists(Long id) {
        RentalFranchiseeDO f = rentalFranchiseeMapper.selectById(id);
        if (f == null) throw ServiceExceptionUtil.exception(FRANCHISEE_NOT_EXISTS);
        return f;
    }

    private RentalFranchiseeDO toDO(RentalFranchiseeSaveReqVO vo) {
        RentalFranchiseeDO f = new RentalFranchiseeDO();
        f.setId(vo.getId());
        f.setName(vo.getName());
        f.setContactName(vo.getContactName());
        f.setContactPhone(vo.getContactPhone());
        f.setStatus(vo.getStatus());
        f.setRemark(vo.getRemark());
        return f;
    }

    private RentalFranchiseeRespVO toRespVO(RentalFranchiseeDO f) {
        RentalFranchiseeRespVO vo = new RentalFranchiseeRespVO();
        vo.setId(f.getId());
        vo.setName(f.getName());
        vo.setContactName(f.getContactName());
        vo.setContactPhone(f.getContactPhone());
        vo.setStatus(f.getStatus());
        vo.setRemark(f.getRemark());
        vo.setCreateTime(f.getCreateTime());
        return vo;
    }
}
