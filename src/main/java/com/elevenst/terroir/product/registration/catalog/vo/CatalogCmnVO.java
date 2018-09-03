package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogCmnVO implements Serializable {
    private static final long serialVersionUID = 5427533243416242931L;
    private int start = 1;
    private int end = 11;
    private int page = 0;
    private int limit = 10;
    private long totalCount = 0;
    private long createNo = -1;
    private long updateNo = -1;
    private long dispCtgrNo = -1;

    private String searchType = null;
    private String searchStr = null;
    private String mode = null;
    private String createDt = null;
    private String updateDt = null;
    private String createId = null;
    private String updateId = null;
    private String createNm = null;
    private String updateNm = null;
    private String searchMode = null;
    private String sort = null;
    private String sortColumn = null;
    private String sortDirection = null;
    private String bidbSearchYn = null;
}
