package com.expensetracker.model;

import java.io.Serializable;

public class InvestmentOption implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String category;
    private String description;
    private String riskLevel; // Low, Moderate, High, Very High
    private String investmentHorizon; // Short Term, Medium Term, Long Term
    private String liquidity; // High, Moderate, Low, Locked-in
    private String taxInformation;
    private String historicalReturns; // Historical return range estimate
    private String iconClass;

    public InvestmentOption() {}

    public InvestmentOption(String name, String category, String description, String riskLevel,
                            String investmentHorizon, String liquidity, String taxInformation,
                            String historicalReturns, String iconClass) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.riskLevel = riskLevel;
        this.investmentHorizon = investmentHorizon;
        this.liquidity = liquidity;
        this.taxInformation = taxInformation;
        this.historicalReturns = historicalReturns;
        this.iconClass = iconClass;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getInvestmentHorizon() { return investmentHorizon; }
    public void setInvestmentHorizon(String investmentHorizon) { this.investmentHorizon = investmentHorizon; }

    public String getLiquidity() { return liquidity; }
    public void setLiquidity(String liquidity) { this.liquidity = liquidity; }

    public String getTaxInformation() { return taxInformation; }
    public void setTaxInformation(String taxInformation) { this.taxInformation = taxInformation; }

    public String getHistoricalReturns() { return historicalReturns; }
    public void setHistoricalReturns(String historicalReturns) { this.historicalReturns = historicalReturns; }

    public String getIconClass() { return iconClass; }
    public void setIconClass(String iconClass) { this.iconClass = iconClass; }
}
