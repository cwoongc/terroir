package com.elevenst.terroir.product.registration.pln_prd.controller;

import com.elevenst.terroir.product.registration.pln_prd.exception.PlanProductControllerException;
import com.elevenst.terroir.product.registration.pln_prd.message.PlanProductControllerExceptionMessage;
import com.elevenst.terroir.product.registration.pln_prd.service.PlanProductServiceImpl;
import com.elevenst.terroir.product.registration.pln_prd.validate.PlanProductControllerValidate;
import com.elevenst.terroir.product.registration.pln_prd.vo.PlanProductVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/pln-prd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(
        description = "예약상품 API"
)
public class PlanProductController {

    @Autowired
    private PlanProductServiceImpl planProductService;

    @Autowired
    private PlanProductControllerValidate planProductControllerValidate;

    /**
     * 예약상품 조회 API
     * @param prdNo 상품번호
     * @return 예약상품, status code 200 or 404
     * @throws PlanProductControllerException
     */
    @ApiOperation(
            value = "예약상품 조회 API",
            notes = "상품번호(PK)로 예약상품 조회",
            response = PlanProductVO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=404, message = "해당 예약상품을 찾을 수 없습니다."),
            @ApiResponse(code=200, message = "예약상품 조회 성공")
    })
    @GetMapping(value = "/{prdNo}")
    public ResponseEntity<PlanProductVO> getPlanProduct(
            @PathVariable("prdNo") @ApiParam(value = "상품번호", required = true) long prdNo
    ) {

        PlanProductVO planProductVO;

        try {

            planProductVO = planProductService.getPlanProduct(prdNo);

        } catch (Exception ex) {
            PlanProductControllerException threx = new PlanProductControllerException(PlanProductControllerExceptionMessage.PLN_PRD_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        ResponseEntity<PlanProductVO> re;
        if(planProductVO == null)
            re = new ResponseEntity<PlanProductVO>(HttpStatus.NOT_FOUND);
        else
            re = new ResponseEntity<PlanProductVO>(planProductVO, HttpStatus.OK);

        return re;
    }



    /**
     * 예약상품 등록 API
     * @param planProductVO 예약상품
     * @return http status code 200
     * @throws PlanProductControllerException
     */
    @PostMapping("")
    public ResponseEntity registerPlanProduct(@RequestBody PlanProductVO planProductVO) throws PlanProductControllerException {

        try {

            planProductService.insertPlanProduct(planProductVO);

        } catch (Exception ex) {
            PlanProductControllerException threx = new PlanProductControllerException(PlanProductControllerExceptionMessage.PLN_PRD_REGISTER_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }


    /**
     * 예약상품 수정 API
     * @param planProductVO
     * @return http status code 204
     * @throws PlanProductControllerException
     */
    @PutMapping("")
    public ResponseEntity updatePlanProduct(@RequestBody PlanProductVO planProductVO) throws PlanProductControllerException {
        try {

            planProductService.updatePlanProduct(planProductVO);

        } catch (Exception ex) {
            PlanProductControllerException threx = new PlanProductControllerException(PlanProductControllerExceptionMessage.PLN_PRD_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 예약상품 삭제 API
     * @param prdNo
     * @return http status code 204
     * @throws PlanProductControllerException
     */
    @DeleteMapping("/{prdNo}")
    public ResponseEntity deletePlanProduct(@PathVariable("prdNo") long prdNo) throws PlanProductControllerException {
        try {

            planProductService.deletePlanProduct(prdNo);

        } catch (Exception ex) {
            PlanProductControllerException threx = new PlanProductControllerException(PlanProductControllerExceptionMessage.PLN_PRD_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
