package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo.RentalFranchiseePageReqVO;
import cn.iocoder.yudao.module.rental.controller.admin.franchisee.vo.RentalFranchiseeRespVO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalFranchiseeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentalFranchiseeMapper extends BaseMapperX<RentalFranchiseeDO> {

    default PageResult<RentalFranchiseeRespVO> selectPage(RentalFranchiseePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RentalFranchiseeDO>()
                .likeIfPresent(RentalFranchiseeDO::getName, reqVO.getName())
                .likeIfPresent(RentalFranchiseeDO::getContactName, reqVO.getContactName())
                .eqIfPresent(RentalFranchiseeDO::getStatus, reqVO.getStatus())
                .orderByDesc(RentalFranchiseeDO::getId))
                .convert(f -> {
                    RentalFranchiseeRespVO vo = new RentalFranchiseeRespVO();
                    vo.setId(f.getId());
                    vo.setName(f.getName());
                    vo.setContactName(f.getContactName());
                    vo.setContactPhone(f.getContactPhone());
                    vo.setStatus(f.getStatus());
                    vo.setRemark(f.getRemark());
                    vo.setCreateTime(f.getCreateTime());
                    return vo;
                });
    }
}
