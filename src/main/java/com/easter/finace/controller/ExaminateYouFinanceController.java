package com.easter.finace.controller;

import com.easter.finace.dto.*;
import com.easter.finace.service.ExaminateYourFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
        return examinateYourFinanceService.calculateEmergencyAbilityDebtAndSaveInfo(liquidAsset, dailyNecessaryExpenses, tokenId);
    }

    @ApiOperation(value = "检测储蓄能力")
    @GetMapping("/saving-ability")
    public ExamResponse examineSavingAbility(SaveAbilityRequest saveAbilityRequest) throws ChangeSetPersister.NotFoundException {
        examinateYourFinanceService.verifyIdentity(saveAbilityRequest.getTokenId());
        examinateYourFinanceService.checkMoney(saveAbilityRequest.getMonthlySaving(), saveAbilityRequest.getMonthlySalary());

        return examinateYourFinanceService.calculateSavingAbilityAndSaveInfo(saveAbilityRequest);
    }

    @ApiOperation(value = "检测资产生息能力")
    @GetMapping("/interest-bearing-assets-ability")
    public ExamResponse examineAssetsGrowthAbility(AssetsGrowthRequest assetsGrowthRequest) throws ChangeSetPersister.NotFoundException {
        examinateYourFinanceService.verifyIdentity(assetsGrowthRequest.getTokenId());
        examinateYourFinanceService.checkMoney(assetsGrowthRequest.getTotalAsset(), assetsGrowthRequest.getInvestAsset());

        return examinateYourFinanceService.calculateAssetsGrowthAbility(assetsGrowthRequest);
    }

    @ApiOperation(value = "获取我的体检报告")
    @GetMapping("/reporter")
    public ReporterResponse examineAssetsGrowthAbility(@RequestParam String tokenId) throws Exception {
        examinateYourFinanceService.verifyIdentity(tokenId);
        return examinateYourFinanceService.getMyReporter(tokenId);
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
}
