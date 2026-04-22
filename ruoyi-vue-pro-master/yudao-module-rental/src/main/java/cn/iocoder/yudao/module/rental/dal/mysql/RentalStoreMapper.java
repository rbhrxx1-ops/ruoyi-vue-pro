package cn.iocoder.yudao.module.rental.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.rental.controller.admin.store.vo.RentalStorePageReqVO;
import cn.iocoder.yudao.module.rental.controller.admin.store.vo.RentalStoreRespVO;
import cn.iocoder.yudao.module.rental.dal.dataobject.RentalStoreDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentalStoreMapper extends BaseMapperX<RentalStoreDO> {

    default PageResult<RentalStoreRespVO> selectPage(RentalStorePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RentalStoreDO>()
                .likeIfPresent(RentalStoreDO::getName, reqVO.getName())
                .eqIfPresent(RentalStoreDO::getFranchiseeId, reqVO.getFranchiseeId())
                .eqIfPresent(RentalStoreDO::getCityId, reqVO.getCityId())
                .eqIfPresent(RentalStoreDO::getStatus, reqVO.getStatus())
                .orderByDesc(RentalStoreDO::getId))
                .convert(s -> {
                    RentalStoreRespVO vo = new RentalStoreRespVO();
                    vo.setId(s.getId());
                    vo.setName(s.getName());
                    vo.setFranchiseeId(s.getFranchiseeId());
                    vo.setCityId(s.getCityId());
                    vo.setAddress(s.getAddress());
                    vo.setLongitude(s.getLongitude());
                    vo.setLatitude(s.getLatitude());
                    vo.setContactPhone(s.getContactPhone());
                    vo.setBusinessHours(s.getBusinessHours());
                    vo.setStatus(s.getStatus());
                    vo.setCreateTime(s.getCreateTime());
                    return vo;
                });
    }
}
