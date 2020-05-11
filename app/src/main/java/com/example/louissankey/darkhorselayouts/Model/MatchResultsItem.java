package com.example.louissankey.darkhorselayouts.Model;

/**
 * Created by louissankey on 5/14/16.
 */
public class MatchResultsItem {
    String statCategory;
    String playerName;
    String opponentName;
    String playerBox;
    String playerBuy;
    String playerTotal;
    String opponentBox;
    String opponentBuy;
    String opponentTotal;

    String playerWin;
    String opponentWin;


    public String getPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(String playerWin) {
        this.playerWin = playerWin;
    }

    public String getOpponentWin() {
        return opponentWin;
    }

    public void setOpponentWin(String opponentWin) {
        this.opponentWin = opponentWin;
    }

    public String getStatCategory() {
        return statCategory;
    }

    public void setStatCategory(String statCategory) {
        this.statCategory = statCategory;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getPlayerBox() {
        return playerBox;
    }

    public void setPlayerBox(String playerBox) {
        this.playerBox = playerBox;
    }

    public String getPlayerBuy() {
        return playerBuy;
    }

    public void setPlayerBuy(String playerBuy) {
        if (playerBuy.equals("+ 0") || playerBuy.equals("- 0")) {

            this.playerBuy = "    ";
        }else{
            this.playerBuy = playerBuy;
        }

    }

    public String getPlayerTotal() {
        return playerTotal;
    }

    public void setPlayerTotal(String playerTotal) {
        this.playerTotal = playerTotal;
    }

    public String getOpponentBox() {
        return opponentBox;
    }

    public void setOpponentBox(String opponentBox) {
        this.opponentBox = opponentBox;
    }

    public String getOpponentBuy() {
        return opponentBuy;
    }

    public void setOpponentBuy(String opponentBuy) {
        this.opponentBuy = opponentBuy;
    }

    public String getOpponentTotal() {
        return opponentTotal;
    }

    public void setOpponentTotal(String opponentTotal) {
        this.opponentTotal = opponentTotal;
    }
}
