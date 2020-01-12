package com.easter.finace.service;

import com.easter.configuration.Constants;
import com.easter.finace.dto.ExamResponse;
import com.easter.finace.entity.Finance;
import com.easter.finace.entity.FinanceRepository;
import com.easter.user.entity.Token;
import com.easter.user.entity.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;

@Service
public class ExaminateYourFinanceService {
    @Autowired
    FinanceRepository financeRepository;

    @Autowired
    TokenRepository tokenRepository;


    public ExamResponse CalculateEmergencyAbilityDebtAndSaveInfo(Integer liquidAsset, Integer dailyNecessaryExpenses, String tokenId) throws ChangeSetPersister.NotFoundException {
        float percent = getPercent(liquidAsset, dailyNecessaryExpenses);
        Finance finance = new Finance();
        ExamResponse examResponse = new ExamResponse();
        String comment = "";

        if (percent > 0 && percent <= 30) {
            comment = Constants.VERY_GOOD_EMERGENCY_STATUS;
        } else if (percent > 30 && percent < 80) {
            comment = Constants.GOOD_EMERGENCY_STATUS;
        } else if (percent > 80) {
            comment = Constants.DANGEROUS_EMERGENCY_STATUS;
        }


        finance.setComment(comment);
        finance.setEmergencyAbility(String.valueOf(percent));
        finance.setUserId(getUserIdByTokenId(tokenId));

        examResponse.setComment(comment);
        examResponse.setPercent(String.valueOf(percent));

        financeRepository.save(finance);
        return examResponse;
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
        if (bigMoney - smallMoney < 0) {
            try {
                throw new Exception("请输入合法金钱数！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
