package com.share.data.api.trending.price.entity;

public class TrendingPriceEntity {
    private String      sTime;
    private double      dKospi;
    private double      dExchangeRateDollar;
    private double      dCorporateLoan;
    private double      dHouseholdLoan;

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public double getdKospi() {
        return dKospi;
    }

    public void setdKospi(double dKospi) {
        this.dKospi = dKospi;
    }

    public double getdExchangeRateDollar() {
        return dExchangeRateDollar;
    }

    public void setdExchangeRateDollar(double dExchangeRateDollar) {
        this.dExchangeRateDollar = dExchangeRateDollar;
    }

    public double getdCorporateLoan() {
        return dCorporateLoan;
    }

    public void setdCorporateLoan(double dCorporateLoan) {
        this.dCorporateLoan = dCorporateLoan;
    }

    public double getdHouseholdLoan() {
        return dHouseholdLoan;
    }

    public void setdHouseholdLoan(double dHouseholdLoan) {
        this.dHouseholdLoan = dHouseholdLoan;
    }
}
