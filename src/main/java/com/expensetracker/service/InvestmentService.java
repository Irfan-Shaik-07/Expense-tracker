package com.expensetracker.service;

import com.expensetracker.model.InvestmentOption;
import java.util.ArrayList;
import java.util.List;

public class InvestmentService {

    public List<InvestmentOption> getAllInvestmentOptions() {
        List<InvestmentOption> list = new ArrayList<>();

        list.add(new InvestmentOption(
            "Mutual Funds (Equity)", "Equity",
            "Professionally managed funds investing in stock market equities for high long-term growth.",
            "Moderate to High", "5+ Years", "High (T+2 Days)",
            "LTCG taxed at 10% above ₹1 Lakh; STCG taxed at 15%",
            "12% - 15% CAGR (Historical)", "fa-chart-line"
        ));

        list.add(new InvestmentOption(
            "Index Funds", "Passive Equity",
            "Low-cost passive funds tracking benchmarks like Nifty 50 or S&P 500.",
            "Moderate", "5+ Years", "High",
            "Same as Equity Mutual Funds (10% LTCG)",
            "11% - 14% CAGR (Historical)", "fa-cubes"
        ));

        list.add(new InvestmentOption(
            "Direct Stocks", "Equity",
            "Direct ownership in publicly listed companies for dividends and capital appreciation.",
            "High to Very High", "3+ Years", "Very High",
            "10% LTCG after ₹1L; 15% STCG; Dividends taxed per slab",
            "12% - 20%+ (Highly variable)", "fa-arrow-trend-up"
        ));

        list.add(new InvestmentOption(
            "SIP (Systematic Investment Plan)", "Strategy",
            "Disciplined monthly automated investment into mutual funds to average out volatility.",
            "Depends on Fund", "3-5+ Years", "High",
            "Taxed based on underlying fund asset class",
            "12% - 15% CAGR (Historical)", "fa-clock-rotate-left"
        ));

        list.add(new InvestmentOption(
            "Gold / Sovereign Gold Bonds (SGB)", "Commodity",
            "Hedge against inflation and currency devaluation. SGB offers 2.5% annual interest.",
            "Low to Moderate", "5-8 Years", "Moderate",
            "SGB maturity capital gains are 100% Tax-Free; 2.5% interest taxed as per income slab",
            "8% - 10% CAGR (Historical)", "fa-coins"
        ));

        list.add(new InvestmentOption(
            "Public Provident Fund (PPF)", "Government Debt",
            "Government-backed long-term tax-saving scheme under EEE (Exempt-Exempt-Exempt) category.",
            "Zero / Safe", "15 Years", "Low (Partial withdrawal after 7 yrs)",
            "100% Tax-Free interest and maturity proceeds under Sec 80C",
            "7.1% p.a. Guaranteed Govt Rate", "fa-piggy-bank"
        ));

        list.add(new InvestmentOption(
            "National Pension System (NPS)", "Retirement",
            "Voluntary retirement savings scheme with equity and corporate debt allocation.",
            "Low to Moderate", "Until Age 60", "Low / Lock-in",
            "Additional deduction up to ₹50,000 under Sec 80CCD(1B); 60% lump sum tax-free at 60",
            "9% - 12% CAGR (Historical)", "fa-shield-heart"
        ));

        list.add(new InvestmentOption(
            "Fixed Deposits (FD)", "Bank Debt",
            "Guaranteed fixed interest returns offered by scheduled banks and NBFCs.",
            "Very Low", "7 Days to 10 Years", "High (Premature withdrawal available)",
            "Interest income added to slab rate; TDS applies if interest exceeds limit",
            "6.5% - 7.5% p.a.", "fa-vault"
        ));

        list.add(new InvestmentOption(
            "Exchange Traded Funds (ETFs)", "Market Securities",
            "Basket of securities traded live on stock exchanges throughout the trading day.",
            "Moderate to High", "3+ Years", "Very High",
            "Taxed same as equity shares (10% LTCG / 15% STCG)",
            "11% - 14% CAGR (Historical)", "fa-layer-group"
        ));

        list.add(new InvestmentOption(
            "Corporate & Govt Bonds", "Fixed Income",
            "Debt instruments issued by governments or corporates offering regular coupon interest.",
            "Low to Moderate", "1 to 10 Years", "Moderate",
            "Interest taxed at slab rate; STCG/LTCG rules apply upon sale",
            "7% - 9.5% p.a.", "fa-file-contract"
        ));

        list.add(new InvestmentOption(
            "Real Estate Investment Trusts (REITs)", "Real Estate",
            "Invest in commercial real estate properties generating rental income without physical ownership.",
            "Moderate", "3-5 Years", "High (Traded on exchange)",
            "Dividends and interest distributions have specific tax-exempt conditions",
            "8% - 12% total return (Yield + Appreciation)", "fa-building-columns"
        ));

        list.add(new InvestmentOption(
            "Physical Real Estate", "Property",
            "Direct purchase of residential or commercial property for rental yields and capital gains.",
            "Moderate to High", "10+ Years", "Low",
            "20% LTCG after 2 years with indexation benefit; Rental income taxed after 30% deduction",
            "8% - 12% CAGR (Varies by location)", "fa-house-chimney"
        ));

        return list;
    }
}
