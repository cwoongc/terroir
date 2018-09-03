package com.elevenst.terroir.product.registration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 1003811 on 2017. 11. 16..
 */
public class Test {

    public static void main(String[] args) {

        long selPrc = 100;
        long preSelPrc = 19;

        double increaseRt = ((double)preSelPrc/(double)selPrc) * 100;


        if(increaseRt < 20 || increaseRt > 150){
            System.out.println("판매가는 최대 80% 인하까지 수정하실 수 있습니다.");
        }else{
            System.out.println("통과");
        }

    }
}
