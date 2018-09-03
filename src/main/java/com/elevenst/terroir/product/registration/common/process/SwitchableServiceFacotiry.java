package com.elevenst.terroir.product.registration.common.process;

import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.common.process.bsndeal.*;
import com.elevenst.terroir.product.registration.common.process.channel.BackOfficeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.channel.ChannelService;
import com.elevenst.terroir.product.registration.common.process.channel.SellerApiServiceImpl;
import com.elevenst.terroir.product.registration.common.process.channel.SellerToolServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.BpTravelTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.BranchMartTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.CheckOutProductTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.CheckOutReservationTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.DigitalTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.LPStoreVisitTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.LPVisitingPickupDlvTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.LPVisitingSvcTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.NormalTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.PinNoZeroWonTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.PinNo11stSendTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.PointTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.RentalTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.SkiTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.SocialIntangibleTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.SocialTangibleTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.TicketTypeServiceImpl;
import com.elevenst.terroir.product.registration.common.process.prdtyp.ProductTypeService;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;

public class SwitchableServiceFacotiry {

    public static ChannelService createChannelService(String channel){
        ChannelService channelService = null;
        switch (channel){
            case "SO":
                channelService = new SellerToolServiceImpl();
                break;
            case "BO":
                channelService = new BackOfficeServiceImpl();
                break;
            case "API":
                channelService = new SellerApiServiceImpl();
                break;
        }
        return channelService;
    }

    public static ProductTypeService createPrdocutTypeService(PrdTypCd prdTypCd) {
        ProductTypeService productTypeService = null;
        switch (prdTypCd) {
            case NORMAL:    //01
                productTypeService = new NormalTypeServiceImpl();
                break;
            case BRANCH_DELIVERY_MART:  //11
                productTypeService = new BranchMartTypeServiceImpl();
                break;
            case DIGITAL_CATEGORY:  //12
                productTypeService = new DigitalTypeServiceImpl();
                break;
            case BP_TRAVEL:  //13
                productTypeService = new BpTravelTypeServiceImpl();
                break;
            case POINT:  //14
                productTypeService = new PointTypeServiceImpl();
                break;
            case SKI:  //15
                productTypeService = new SkiTypeServiceImpl();
                break;
            case SOCIAL_INTANGIBLE:  //17
                productTypeService = new SocialIntangibleTypeServiceImpl();
                break;
            case SOCIAL_TANGIBLE:  //18
                productTypeService = new SocialTangibleTypeServiceImpl();
                break;
            case PIN_11ST_SEND: //19
                productTypeService = new PinNo11stSendTypeServiceImpl();
                break;
            case PIN_ZERO_COST: //20
                productTypeService = new PinNoZeroWonTypeServiceImpl();
                break;
            case BP_TICKET: //22
                productTypeService = new TicketTypeServiceImpl();
                break;
            case RENTAL:    //26
                productTypeService = new RentalTypeServiceImpl();
                break;
            case ONECLICK_CHECKOUT_PRODUCT:   //33
                productTypeService = new CheckOutProductTypeServiceImpl();
                break;
            case ONECLICK_CHECKOUT_RESERVATION:  //34
                productTypeService = new CheckOutReservationTypeServiceImpl();
                break;
            case STORE_VISIT:   //35
                productTypeService = new LPStoreVisitTypeServiceImpl();
                break;
            case VISITING_SERVICE:  //37
                productTypeService = new LPVisitingSvcTypeServiceImpl();
                break;
            case VISITING_PICKUP_DELIVERY:  //37
                productTypeService = new LPVisitingPickupDlvTypeServiceImpl();
                break;
            default:
                break;
        }

        return productTypeService;
    }

    public static BsnDealService createBsnDealService(BsnDealClf bsnDealClf){
        BsnDealService bsnDealService = null;
        switch (bsnDealClf){
            case COMMISSION:
                bsnDealService = new CommissionServiceImpl();
                break;
            case DIRECT_PURCHASE:
                bsnDealService = new DirectPurcharseServiceImpl();
                break;
            case SPECIFIC_PURCHASE:
                bsnDealService = new SpecificPurchaseServiceImpl();
                break;
            case TRUST_PURCHASE:
                bsnDealService = new TrustPurchaseServiceImpl();
                break;
        }

        return bsnDealService;
    }
}
