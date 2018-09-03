package com.elevenst.terroir.product.registration.image.service;

import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.vo.ProductImageVO;
import org.springframework.stereotype.Component;

@Component
public class ProductImageSetter {

    public PdPrdImage setExtNm(ImageKind imageKind, PdPrdImage pdPrdImage, String extNm){
        if(imageKind == null || pdPrdImage == null){
            return null;
        }
        if(imageKind == ImageKind.B){
            pdPrdImage.setDtlBasicExtNm(extNm);

        } else if(imageKind == ImageKind.A1){
            pdPrdImage.setAdd1ExtNm(extNm);

        } else if(imageKind == ImageKind.A2){
            pdPrdImage.setAdd2ExtNm(extNm);

        } else if(imageKind == ImageKind.A3){
            pdPrdImage.setAdd3ExtNm(extNm);

        } else if(imageKind == ImageKind.L300){
            pdPrdImage.setBasicExtNm(extNm);

        } else if(imageKind == ImageKind.L80){
            pdPrdImage.setL80ExtNm(extNm);

        } else if(imageKind == ImageKind.L170){
            pdPrdImage.setL170ExtNm(extNm);

        } else if(imageKind == ImageKind.D){
            pdPrdImage.setCardVwImgUrl(extNm);

        } else if(imageKind == ImageKind.U1){
            pdPrdImage.setPtnrExusImgUrl(extNm);

        } else if(imageKind == ImageKind.S){
            pdPrdImage.setStyleExtNm(extNm);

        } else if(imageKind == ImageKind.WD){
            pdPrdImage.setWirelssDtlExtNm(extNm);

        }

        return pdPrdImage;
    }

    public String getExtNm(ImageKind imageKind, PdPrdImage pdPrdImage){
        if(imageKind == null || pdPrdImage == null){
            return null;
        }
        if(imageKind == ImageKind.B){
            return pdPrdImage.getDtlBasicExtNm();

        } else if(imageKind == ImageKind.A1){
            return pdPrdImage.getAdd1ExtNm();

        } else if(imageKind == ImageKind.A2){
            return pdPrdImage.getAdd2ExtNm();

        } else if(imageKind == ImageKind.A3){
            return pdPrdImage.getAdd3ExtNm();

        } else if(imageKind == ImageKind.L300){
            return pdPrdImage.getBasicExtNm();

        } else if(imageKind == ImageKind.L80){
            return pdPrdImage.getL80ExtNm();

        } else if(imageKind == ImageKind.L170){
            return pdPrdImage.getL170ExtNm();

        } else if(imageKind == ImageKind.D){
            return pdPrdImage.getCardVwImgUrl();

        } else if(imageKind == ImageKind.U1){
            return pdPrdImage.getPtnrExusImgUrl();

        } else if(imageKind == ImageKind.S){
            return pdPrdImage.getStyleExtNm();

        } else if(imageKind == ImageKind.WD){
            return pdPrdImage.getWirelssDtlExtNm();

        }
        return null;
    }


    public PdPrdImage setZoominImgYn(ImageKind imageKind, PdPrdImage pdPrdImage, String zoominImgYn){
        if(imageKind == null || pdPrdImage == null){
            return null;
        }
        if(imageKind == ImageKind.B){
            pdPrdImage.setBasicZoominImgYn(zoominImgYn);

        } else if(imageKind == ImageKind.A1){
            pdPrdImage.setAdd1ZoominImgYn(zoominImgYn);

        } else if(imageKind == ImageKind.A2){
            pdPrdImage.setAdd2ZoominImgYn(zoominImgYn);

        } else if(imageKind == ImageKind.A3){
            pdPrdImage.setAdd3ZoominImgYn(zoominImgYn);

        }
        return pdPrdImage;
    }

    public String getZoominImgYn(ImageKind imageKind, PdPrdImage pdPrdImage){
        if(imageKind == null || pdPrdImage == null){
            return null;
        }
        if(imageKind == ImageKind.B){
            return pdPrdImage.getBasicZoominImgYn();

        } else if(imageKind == ImageKind.A1){
            return pdPrdImage.getAdd1ZoominImgYn();

        } else if(imageKind == ImageKind.A2){
            return pdPrdImage.getAdd2ZoominImgYn();

        } else if(imageKind == ImageKind.A3){
            return pdPrdImage.getAdd3ZoominImgYn();

        }
        return null;
    }


    public ProductImageVO setImgChgYn(ImageKind imageKind, ProductImageVO productImageVO, String imgChgYn){
        if(imageKind == null || productImageVO == null){
            return null;
        }
        if(imageKind == ImageKind.B){
            productImageVO.setDtlBaseImgChgYn(imgChgYn);

        } else if(imageKind == ImageKind.A1){
            productImageVO.setAdd1ImgChgYn(imgChgYn);

        } else if(imageKind == ImageKind.A2){
            productImageVO.setAdd2ImgChgYn(imgChgYn);

        } else if(imageKind == ImageKind.A3){
            productImageVO.setAdd3ImgChgYn(imgChgYn);

        } else if(imageKind == ImageKind.D){
            productImageVO.setDtlBaseImgChgYn(imgChgYn);

        }
        return productImageVO;
    }

}
