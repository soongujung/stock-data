package com.api.trending.price.entity;

public class TrendingPriceEntity {
    private String      sTime;
    private Double      dKospi;
    private Double      dExchangeRateDollar;
    private Double      dCorporateLoan;
    private Double      dHouseholdLoan;

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public Double getdKospi() {
        return dKospi;
    }

    public void setdKospi(Double dKospi) {
        this.dKospi = dKospi;
    }

    public Double getdExchangeRateDollar() {
        return dExchangeRateDollar;
    }

    public void setdExchangeRateDollar(Double dExchangeRateDollar) {
        this.dExchangeRateDollar = dExchangeRateDollar;
    }

    public Double getdCorporateLoan() {
        return dCorporateLoan;
    }

    public void setdCorporateLoan(Double dCorporateLoan) {
        this.dCorporateLoan = dCorporateLoan;
    }

    public Double getdHouseholdLoan() {
        return dHouseholdLoan;
    }

    public void setdHouseholdLoan(Double dHouseholdLoan) {
        this.dHouseholdLoan = dHouseholdLoan;
    }
}
