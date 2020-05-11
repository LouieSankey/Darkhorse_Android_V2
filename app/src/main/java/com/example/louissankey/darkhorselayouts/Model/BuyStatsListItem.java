package com.example.louissankey.darkhorselayouts.Model;

/**
 * Created by louissankey on 5/1/16.
 */
public class BuyStatsListItem {
    private String statLabel;
    private String playerAvg;
    private String oppAvg;
    private double price;
    private int plusAmount = 0;


    public String getStatLabel() {
        return statLabel;
    }

    public void setStatLabel(String statLabel) {
        this.statLabel = statLabel;
    }

    public String getPlayerAvg() {
        return playerAvg;
    }

    public void setPlayerAvg(String playerAvg) {
        this.playerAvg = playerAvg;
    }

    public String getOppAvg() {
        return oppAvg;
    }

    public void setOppAvg(String oppAvg) {
        this.oppAvg = oppAvg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPlusAmount() {
        return plusAmount;
    }

    public void setPlusAmount(int plusAmount) {
        this.plusAmount = plusAmount;
    }



}
