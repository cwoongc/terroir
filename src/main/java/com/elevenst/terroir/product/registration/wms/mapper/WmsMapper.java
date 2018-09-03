package com.elevenst.terroir.product.registration.wms.mapper;

import com.elevenst.terroir.product.registration.option.vo.BasisStockVO;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import com.elevenst.terroir.product.registration.wms.vo.EmpVO;
import com.elevenst.terroir.product.registration.wms.vo.WmChrgVO;
import com.elevenst.terroir.product.registration.wms.vo.WmCustomsResultVO;
import com.elevenst.terroir.product.registration.wms.vo.WmCustomsVO;
import com.elevenst.terroir.product.registration.wms.vo.WmInsrCstVO;
import com.elevenst.terroir.product.registration.wms.vo.WmWghtDlvCstVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WmsMapper {
    int insertPdPrdWmsInfoHist(PdPrdWmsInfo param);

    int updateEmployeeNo(PdPrdWmsInfo param);

    int insertBasisStockAttribute(PdPrdWmsInfo basisStockVO);

    List<EmpVO> getMdList(EmpVO empVO);
    List<WmWghtDlvCstVO> getSellerWmWghtDlvCst(WmWghtDlvCstVO wmWghtDlvCstVO);
    List<WmInsrCstVO> getSellerWmInsrCst(WmInsrCstVO wmInsrCstVO);
    WmCustomsResultVO getWmCustomsCondition(WmCustomsResultVO wmCustomsResultVO);
    WmChrgVO getWmCharge(WmChrgVO wmChrgVO);
    BasisStockVO getBasisStockAttribute(long prdNo);

    int getHScodeDeliveryCd(WmCustomsVO wmCustomsVO);
}
