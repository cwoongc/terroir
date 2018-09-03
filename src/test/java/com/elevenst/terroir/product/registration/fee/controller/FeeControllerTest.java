package com.elevenst.terroir.product.registration.fee.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class FeeControllerTest {

    @Autowired
    FeeController feeController;

    public MockHttpServletRequest setMockHttpServletRequest() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        //TMALL_AUTH value : vf2mania (2G6YrKmdT4n13di8eyy9ZEC67UVYynFjDci6uXStwqCi9kQTO2ojH6uIxneVNZMMOQm4n8V3n1CH%0ABRvXV44e9CNUq%2BAgZWSzbfNtNp8xMP6g4Vl32LBP0PIJzNFNkactLnm6x6yowuoqjYEv%2F%2F%2BM%2BuKE%0AtKsWN97p4DnXLom9zjtIYV4nhRAGEAEwTu8e2vm%2B4%2BQ2Fvvv8ndGP58C459CpztkWaCXt25LNZDO%0A61t0rD6ZNCRlTkGAS65UrvRjejkSR0QgKr3rM%2BcbUbSeFaI6Aunu8Rg0adlyqSx07rerpExrJj2B%0AX5J2KtAV7tyHJ8xp8IBqDRig3HuvTAaHvLmyzJPkLn7MI9y1dj1EVInFn2uq864gOl7uSWpDOlPE%0Ahts3dFNBxX3AMCuxRZ8kEYrCcbjgwI6im%2FsGUuTnsrY5PJL6bpjT%2FuJhg%2Faa%2FmIJ8EEZyz6luna9%0AtwAkmP%2FicMetjC%2BHxx%2F%2FSEZN15gyMl4R0uny%2Fq%2FK1%2BsUzdiUdwwRlf61Nsh7dUuAv5Z3p3apBnhl%0A7h0X%2BDJ5PBfHKwctFy%2FvX8m1%2FJ%2B8EQ2xChPJhbjl%2BXf19CmOKtvt5HomskQcKWPwYuEsLTN0aF1L%0A4nEVHw1z8FxJHadrG99rAkC03XviHrun5OthHQ2S0vgDUUCWLT4SFGUT5XqWd2BMe8ScTn91XeES%0Aubh%2B8ChjE6Uqqx21Y8CQhQvRej%2By2KYGymnmKjIu9QbD4O7s5cieozXaK4h55kv5pc7lbCw2SSvT%0A8XcywvxIKCIB%2F4OJrBGS4ZRPGnBq1x5arxfAvqTdNlTY72O2vbbve6qc92V9Z3NaN9YftEpH8u%2FO%0A956SNOmlu5ZoTmatEl6zL6rBkkx0cgVHggkEBK3MIZd0LJx8ueTTISoo1AuGsJrroEz9qz4IBEt7%0AHI94Mw%3D%3D)
        //TMALL_AUTH value : crewmate (AL%2FEIbZDCf2bQVpYkNiqrGbH0ExJh4GAt3FG%2B7JH6o4AU7UgyRY5RzBIuUYVM82xCcmsvsNp0GlL%0ApkdVjO60p68oLPYzyraEXv42BI4I0IEZuvXFPewgeKA5ZEQ5kNiCdV74UV9DBWzJjEC09PnSBDUp%0Ap%2BSpytT3LoGTPAWhzAPBUqq1O0kiBkCagkEvFvTZRutgNPklnxkprF%2BxivJe%2Bwq87%2BBuRPabdDjw%0AAw2sOwUDcMbxA1CZkEctC6%2FyH4pjVqrzaADmO%2Ft7TnAcPak9pIYTqQa6aqRSA02aD4n%2FjilYUy0y%0A%2B6JLbOt8KWZroNRlvE3JjFcs9fY7iF6LGgRyrluhPtBeX3kxueZ224plxC1BRJQHTXMIMKcKOcnl%0A1R7RPo2MEsjSMC3LcGYarH0Jr9HeoYaEAEjbo7INdsxLt%2FtvC8%2FJFb%2B9bFEJ%2BWMgMe9IFiw8%2FEre%0A9EuwU1ac%2FnhDzvuVMcT34I8E9F27aYy%2FgCUZsJH6K1uqIQ1sPOJylUscwd%2FQehuPr869xpIDezEj%0AnnymuSY6jRasTi1%2BMB6KbR4kiEFiIit0mAnfAkeaEGCV2pOtdcAgL%2BVbciH34Rjb4XDjWFHlmyDM%0AkuSAG%2Frqp46JltLuXe5rw3HNFI90Zt3nmeyE7DSA3dTLORsgYPkJkZGcZe78gNlNX%2FqZE8LpNrIL%0AvcgImUj%2FPeJNW8akQLXXRdhkW%2BVBzwoMbGXImfG25PPj6G2KtD35Nj40IUrlHZXNstOMBaZjUCd0%0AkP0sjeyvMwbeUUAhwe27FkPDtJhwflHQFpck9TThAvS4Nw9rjd6NRNojoWTbewiv5HAat1Nc2c7K%0A3Ax5FC5idtkyVvIqwWFM8ng%2FfPGev7T0ujBzQpPZdwkDmssnhYYJ%2FsbmeMoCxn2xTS%2FT4KQo%2BwaT%0AcZtnwjfKnh9ucy2NLee7m%2FVH3QHHThf4suk3gnsOCb%2BI9Q%2Fy)
        Cookie cookie = new Cookie("TMALL_AUTH", "AL%2FEIbZDCf2bQVpYkNiqrGbH0ExJh4GAt3FG%2B7JH6o4AU7UgyRY5RzBIuUYVM82xCcmsvsNp0GlL%0ApkdVjO60p68oLPYzyraEXv42BI4I0IEZuvXFPewgeKA5ZEQ5kNiCdV74UV9DBWzJjEC09PnSBDUp%0Ap%2BSpytT3LoGTPAWhzAPBUqq1O0kiBkCagkEvFvTZRutgNPklnxkprF%2BxivJe%2Bwq87%2BBuRPabdDjw%0AAw2sOwUDcMbxA1CZkEctC6%2FyH4pjVqrzaADmO%2Ft7TnAcPak9pIYTqQa6aqRSA02aD4n%2FjilYUy0y%0A%2B6JLbOt8KWZroNRlvE3JjFcs9fY7iF6LGgRyrluhPtBeX3kxueZ224plxC1BRJQHTXMIMKcKOcnl%0A1R7RPo2MEsjSMC3LcGYarH0Jr9HeoYaEAEjbo7INdsxLt%2FtvC8%2FJFb%2B9bFEJ%2BWMgMe9IFiw8%2FEre%0A9EuwU1ac%2FnhDzvuVMcT34I8E9F27aYy%2FgCUZsJH6K1uqIQ1sPOJylUscwd%2FQehuPr869xpIDezEj%0AnnymuSY6jRasTi1%2BMB6KbR4kiEFiIit0mAnfAkeaEGCV2pOtdcAgL%2BVbciH34Rjb4XDjWFHlmyDM%0AkuSAG%2Frqp46JltLuXe5rw3HNFI90Zt3nmeyE7DSA3dTLORsgYPkJkZGcZe78gNlNX%2FqZE8LpNrIL%0AvcgImUj%2FPeJNW8akQLXXRdhkW%2BVBzwoMbGXImfG25PPj6G2KtD35Nj40IUrlHZXNstOMBaZjUCd0%0AkP0sjeyvMwbeUUAhwe27FkPDtJhwflHQFpck9TThAvS4Nw9rjd6NRNojoWTbewiv5HAat1Nc2c7K%0A3Ax5FC5idtkyVvIqwWFM8ng%2FfPGev7T0ujBzQpPZdwkDmssnhYYJ%2FsbmeMoCxn2xTS%2FT4KQo%2BwaT%0AcZtnwjfKnh9ucy2NLee7m%2FVH3QHHThf4suk3gnsOCb%2BI9Q%2Fy");
        cookie.setDomain(".11st.co.kr");
        req.setCookies(cookie);
        return req;
    }

    public HttpServletResponse setMockHttpServletResponse() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        return response;
    }

    @Test
    public void getSaleFeeRate() throws Exception {
        HttpServletRequest request = setMockHttpServletRequest();
        HttpServletResponse response = setMockHttpServletResponse();
        String selMthdCd = "01";
        long dispCtgr2No = 1001550;
        long dispCtgr3No = 1010385;
        long prdNo = 0;
        long selPrc = 0;
        ResponseEntity<String> retStr = feeController.getSaleFeeRate(request, response, selMthdCd, dispCtgr2No, dispCtgr3No, prdNo, selPrc);

        System.out.println("getSaleFeeRate= " + retStr);
    }
}
