package com.easter.finace.controller;

import com.easter.finace.dto.DebtAbilityRequest;
import com.easter.finace.dto.ExamResponse;
import com.easter.finace.dto.SaveAbilityRequest;
import com.easter.finace.service.ExaminateYourFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.NumberFormat;

@Api("财务体检API")
@RestController
@RequestMapping("/examine")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminateYouFinanceController {

    private final ExaminateYourFinanceService examinateYourFinanceService;

    @ApiOperation(value = "检测应急能力")
    @GetMapping("/emergency-ability")
    public ExamResponse examineEmergencyAbility(@RequestParam @Min(0) @Max(10000000) Integer liquidAsset,
                                                @RequestParam @Min(0) @Max(10000000) Integer dailyNecessaryExpenses,
                                                @RequestParam String tokenId) throws ChangeSetPersister.NotFoundException {
        examinateYourFinanceService.verifyIdentity(tokenId);
        examinateYourFinanceService.checkMoney(liquidAsset, dailyNecessaryExpenses);
        return examinateYourFinanceService.CalculateEmergencyAbilityDebtAndSaveInfo(liquidAsset, dailyNecessaryExpenses, tokenId);
    }

    @ApiOperation(value = "检测偿债能力")
    @PostMapping("/examineDebtAbility")
    public String examineDebtAbility(@RequestBody DebtAbilityRequest debtAbilityRequest) {
        examinateYourFinanceService.verifyIdentity(debtAbilityRequest.getTokenId());
        examinateYourFinanceService.checkMoney(debtAbilityRequest.getLiquidAsset(), debtAbilityRequest.getShortTermLiability());

        String result = "";

//        switch (debtAbilityRequest.getDebtType()) {
//            case "shortTerm":
//                result = examinateYourFinanceService.ShortTermDebt(liquidAsset, shortTermLiability);
//                return result;
//            case "longTermDebt":
//                result = examinateYourFinanceService.longTermDebt(liquidAsset, shortTermLiability);
//                return result;
//            case "monthlyDebt":
//                result = examinateYourFinanceService.monthlyDebt(liquidAsset, shortTermLiability);
//                return result;
//            case "debtRate":
//                result = examinateYourFinanceService.debtRate(debtRate);
//        }
        return result;
    }

    @ApiOperation(value = "检测储蓄能力")
    @PostMapping("/examineSavingAbility")
    public ExamResponse examineSavingAbility2(@RequestBody SaveAbilityRequest saveAbilityRequest) throws ChangeSetPersister.NotFoundException {
        examinateYourFinanceService.verifyIdentity(saveAbilityRequest.getTokenId());
        examinateYourFinanceService.checkMoney(saveAbilityRequest.getMonthlySaving(), saveAbilityRequest.getMonthlySalary());


        return examinateYourFinanceService.CalculateSavingAbilityAndSaveInfo(saveAbilityRequest);
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

        return result;
    }
}
