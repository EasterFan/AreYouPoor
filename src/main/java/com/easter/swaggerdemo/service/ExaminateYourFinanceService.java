package com.easter.swaggerdemo.service;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;

@Service
public class ExaminateYourFinanceService {
    public String ShortTermDebt(Integer shortTermLiability, Integer liquidAsset) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) shortTermLiability  / (float) liquidAsset));

        return percent <= 0.5 ? "优秀的短期偿债能力！" : "比较危险⚠️";
    }

    public String longTermDebt(Integer debt, Integer totalAsset) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) debt / (float) totalAsset));

        return percent >= 2 ? "优秀的短期偿债能力！" : "比较危险⚠️";
    }

    public String monthlyDebt(Integer monthlyDebt, Integer monthSalary) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) monthlyDebt / (float) monthSalary));

        return percent <= 0.3 ? "优秀的每月偿债能力！" :  "比较危险⚠️";
    }

    public String debtRate(Float debtRate) {
        return debtRate <= 8 ? "比较优秀的借款利率" : "危险⚠️";
    }
}
