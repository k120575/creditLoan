package com.example.creditloan;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootApplication
public class CreditLoanApplication {

    public static void main(String[] args) {


//        System.out.println("Hello world!");

        // 年利率
        BigDecimal yearInterestRate = new BigDecimal("0.0345");
        // 月利率
        BigDecimal monthlyInterestRate = yearInterestRate.divide(new BigDecimal(12), 5, RoundingMode.HALF_UP);
        // 期數
        Integer period = 84;
        // 貸款總額
        Integer loan = 700000;

        // 每月平均潘還率 = { [ (1 + 月利率 ) ^ 期數 ] x 月利率 } / { [ ( 1 + 月利率 ) ^ 期數 ] - 1 }
        BigDecimal avgPaymentRate = ((monthlyInterestRate.add(BigDecimal.ONE)).pow(period)).multiply(monthlyInterestRate)
                .divide(((monthlyInterestRate.add(BigDecimal.ONE)).pow(period)).subtract(BigDecimal.ONE), 5, BigDecimal.ROUND_HALF_UP);
        System.out.println("每月平均潘還率 = " + avgPaymentRate);
        // 每月還款金額 = 貸款總金額 x 每月平均攤還率
        Integer monthlyRepayment = avgPaymentRate.multiply(new BigDecimal(loan)).intValue();
        System.out.println("每月還款金額 = " + monthlyRepayment);

        for (int i = 0; i < period; i++){
            // 每月應付利息 = 貸款本金餘額 x 月利率
            Integer monthlyInterest = monthlyInterestRate.multiply(new BigDecimal(loan)).intValue();
            // 每月應付本金 = 每月還款金額 - 每月應付利息
            Integer monthlyPrincipal = monthlyRepayment - monthlyInterest;
            loan -= monthlyPrincipal;
            System.out.println("第" + (i + 1) + "期應付本金 = " + monthlyPrincipal + "，應付利息 = " + monthlyInterest + "，剩餘貸款 = " + loan);
        }
    }

}
