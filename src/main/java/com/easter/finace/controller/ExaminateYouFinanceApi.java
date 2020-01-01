package com.easter.finace.controller;

import com.easter.finace.service.ExaminateYourFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;

@Api("财务体检API")
@RestController
@RequestMapping("/examine")
public class ExaminateYouFinanceApi {

    @Autowired
    ExaminateYourFinanceService examinateYourFinanceService;

    @ApiOperation(value = "检测应急能力")
    @PostMapping("/examineEmergencyAbility")
    public String examineEmergencyAbility(Integer liquidAsset, Integer dailyNecessaryExpenses) {
        String result = "";

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) liquidAsset / (float) dailyNecessaryExpenses));

        if (percent >= 3 && percent <= 6) {
            result = "优秀的应急能力！";
        } else if (percent < 3) {
            result = "危险⚠️";
        } else {
            result = "备用金过多，可考虑适当储蓄或投资！";
        }

        return result;
    }

    @ApiOperation(value = "检测偿债能力")
    @PostMapping("/examineDebtAbility/{debtType}")
    public String examineDebtAbility(@PathVariable String debtType, Integer liquidAsset, Integer shortTermLiability, Float debtRate ) {
        String result = "";

        switch (debtType) {
            case "shortTerm":
                result = examinateYourFinanceService.ShortTermDebt(liquidAsset, shortTermLiability);
                return result;
            case "longTermDebt":
                result = examinateYourFinanceService.longTermDebt(liquidAsset,shortTermLiability);
                return result;
            case "monthlyDebt":
                result = examinateYourFinanceService.monthlyDebt(liquidAsset,shortTermLiability);
                return result;
            case "debtRate":
                result = examinateYourFinanceService.debtRate(debtRate);
        }
        return result;
    }

    @ApiOperation(value = "检测储蓄能力")
    @PostMapping("/examineSavingAbility")
    public String examineSavingAbility2(Integer monthlySaving, Integer monthlySalary) {
        String result = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) monthlySalary / (float) monthlySaving));

        if (percent >= 1.5 && percent <= 3) {
            result = "合理的储蓄，继续保持！";
        } else if (percent < 1.5) {
            result = "危险!需要适量增加储蓄比例⚠️";
        } else {
            result = "储蓄比例偏高，可考虑适当消费或投资！";
        }

        return result;
    }

    @ApiOperation(value = "检测资产生息能力")
    @PostMapping("/examineInterestBearingAssetsAbility")
    public String examineSavingAbility(Integer investAsset, Integer totalAsset) {
        String result = "";

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) investAsset / (float) totalAsset));

        if (percent >= 0.4 && percent <= 0.6) {
            result = "合理的资产生息比例，继续保持！";
        } else if (percent < 0.4) {
            result = "投资比例偏低";
        } else {
            result = "投资比例偏高";
        }

        return result ;
    }
}
