package com.easter.finace.service;

import com.easter.configuration.Constants;
import com.easter.finace.dto.AssetsGrowthRequest;
import com.easter.finace.dto.ExamResponse;
import com.easter.finace.dto.ReporterResponse;
import com.easter.finace.dto.SaveAbilityRequest;
import com.easter.finace.entity.Finance;
import com.easter.finace.entity.FinanceRepository;
import com.easter.user.entity.Token;
import com.easter.user.entity.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminateYourFinanceService {
    private final FinanceRepository financeRepository;
    private final TokenRepository tokenRepository;


    public ExamResponse calculateEmergencyAbilityDebtAndSaveInfo(Integer liquidAsset, Integer dailyNecessaryExpenses, String tokenId) throws ChangeSetPersister.NotFoundException {
        float percent = getPercent(liquidAsset, dailyNecessaryExpenses);
        String comment = "";

        if (percent <= 30) {
            comment = Constants.VERY_GOOD_EMERGENCY_STATUS;
        } else if (percent > 30 && percent < 80) {
            comment = Constants.GOOD_EMERGENCY_STATUS;
        } else if (percent > 80) {
            comment = Constants.DANGEROUS_EMERGENCY_STATUS;
        }

        Finance finance = financeRepository.findByUserId(getUserIdByTokenId(tokenId)).orElseGet(Finance::new);
        finance.setUserId(getUserIdByTokenId(tokenId));
        finance.setEmergencyAbility(String.valueOf(percent));

        financeRepository.save(finance);

        return ExamResponse.builder()
                .comment(comment)
                .percent(String.valueOf(100 - percent))
                .build();
    }

    public ExamResponse calculateSavingAbilityAndSaveInfo(SaveAbilityRequest saveAbilityRequest) throws ChangeSetPersister.NotFoundException {
        String comment = "";
        float percent = getPercent(saveAbilityRequest.getMonthlySalary(), saveAbilityRequest.getMonthlySaving());

        if (percent >= 8 && percent <= 30) {
            comment = Constants.VERY_GOOD_SAVING_ABILITY_STATUS;
        } else if (percent < 8) {
            comment = Constants.A_LITTLE_DANGEROUS_SAVING_ABILITY_STATUS;
        } else {
            comment = Constants.OVER_GOOD_ENOUGH_SAVING_ABILITY_STATUS;
        }

        Finance finance = financeRepository.findByUserId(getUserIdByTokenId(saveAbilityRequest.getTokenId())).orElseGet(Finance::new);
        finance.setUserId(getUserIdByTokenId(saveAbilityRequest.getTokenId()));
        finance.setSavingAbility(String.valueOf(percent));
        financeRepository.save(finance);

        return ExamResponse.builder()
                .percent(String.valueOf(100 - percent))
                .comment(comment)
                .build();
    }

    public ExamResponse calculateAssetsGrowthAbility(AssetsGrowthRequest assetsGrowthRequest) throws ChangeSetPersister.NotFoundException {
        String comment = "";
        float percent = getPercent(assetsGrowthRequest.getTotalAsset(), assetsGrowthRequest.getInvestAsset());

        if (percent >= 50) {
            comment = Constants.DANGEROUS_ASSETS_GROWTH_ABILITY;
        } else {
            comment = Constants.GOOD_ASSETS_GROWTH_ABILITY;
        }

        Finance finance = financeRepository.findByUserId(getUserIdByTokenId(assetsGrowthRequest.getTokenId())).orElseGet(Finance::new);
        finance.setUserId(getUserIdByTokenId(assetsGrowthRequest.getTokenId()));
        finance.setAssetsGrowthAbility(String.valueOf(percent));

        financeRepository.save(finance);

        return ExamResponse.builder()
                .percent(String.valueOf(100 - percent))
                .comment(comment)
                .build();
    }

    private float getPercent(Integer liquidAsset, Integer dailyNecessaryExpenses) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return Float.parseFloat(numberFormat.format((float) dailyNecessaryExpenses / (float) liquidAsset * 100));
    }

    private String getUserIdByTokenId(String tokenId) throws ChangeSetPersister.NotFoundException {
        Token token = tokenRepository.findById(tokenId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return token.getUserId();
    }

    public String ShortTermDebt(Integer shortTermLiability, Integer liquidAsset) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        float percent = Float.parseFloat(numberFormat.format((float) shortTermLiability / (float) liquidAsset));

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

        return percent <= 0.3 ? "优秀的每月偿债能力！" : "比较危险⚠️";
    }

    public String debtRate(Float debtRate) {
        return debtRate <= 8 ? "比较优秀的借款利率" : "危险⚠️";
    }

    public void verifyIdentity(String tokenId) {
        try {
            tokenRepository.findById(tokenId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkMoney(Integer bigMoney, Integer smallMoney) {
        if (bigMoney - smallMoney < 0 || bigMoney < 0 || smallMoney < 0) {
            try {
                throw new Exception("请输入合法金钱数！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ReporterResponse getMyReporter(String tokenId) throws Exception {
        Finance finance = financeRepository.findByUserId(getUserIdByTokenId(tokenId)).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (Stream.of(finance.getEmergencyAbility(), finance.getAssetsGrowthAbility(), finance.getSavingAbility()).anyMatch(Objects::isNull))
            throw new Exception("信息不全，无法生成报告！");

        Finance newFinance = calculateFinanceStatus(finance);

        return ReporterResponse.builder()
                .emergencyAbility(100 - Float.parseFloat(newFinance.getEmergencyAbility()))
                .savingAbility(100 - Float.parseFloat(newFinance.getSavingAbility()))
                .assetGrowthAbility(100 - Float.parseFloat(newFinance.getAssetsGrowthAbility()))
                .totalAbility(newFinance.getTotalAbility())
                .comment(newFinance.getComment())
                .build();
    }

    private Finance calculateFinanceStatus(Finance finance) {
        String comment = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        double totalAbility = Double.parseDouble(numberFormat.format((Float.parseFloat(finance.getEmergencyAbility()) * 0.5
                + Float.parseFloat(finance.getSavingAbility()) * 0.3
                + Float.parseFloat(finance.getAssetsGrowthAbility()) * 0.2) / 3));

        if (totalAbility <= 10) {
            comment = Constants.DANGEROUS_TOTAL_ABILITY;
        } else if (totalAbility <= 1){
            comment = Constants.GOOD_TOTAL_ABILITY;
        }else {
            comment = Constants.NOT_BAD_TOTAL_ABILITY;
        }

        finance.setTotalAbility(String.valueOf(totalAbility));
        finance.setComment(comment);


        return financeRepository.save(finance);
    }
}
