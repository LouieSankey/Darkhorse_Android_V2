package com.example.louissankey.darkhorselayouts.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by louissankey on 5/1/16.
 */
public class Player implements Parcelable{

    private boolean isDraftedOpponent;
    private int displayType;
    private double mCapDouble;

    private String username;
    private String oppVersus;
    private String statusLabel;
    private String playerName;
    private String mCapString;
    private String price;

    private String ptsAvg;
    private String rebAvg;
    private String astAvg;
    private String blkAvg;
    private String _3PtAvg;
    private String TOAvg;
    private String stlAvg;






    public Player(Parcel in) {
        oppVersus = in.readString();
        statusLabel = in.readString();
        username = in.readString();
        playerName = in.readString();
        mCapString = in.readString();
        price = in.readString();
        ptsAvg = in.readString();
        rebAvg = in.readString();
        astAvg = in.readString();
        blkAvg = in.readString();
        _3PtAvg = in.readString();
        TOAvg = in.readString();
        stlAvg = in.readString();
        displayType = in.readInt();
        isDraftedOpponent = in.readByte() != 0;
        mCapDouble = in.readDouble();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(oppVersus);
        dest.writeString(statusLabel);
        dest.writeString(username);
        dest.writeString(playerName);
        dest.writeString(mCapString);
        dest.writeString(price);
        dest.writeString(ptsAvg);
        dest.writeString(rebAvg);
        dest.writeString(astAvg);
        dest.writeString(blkAvg);
        dest.writeString(_3PtAvg);
        dest.writeString(TOAvg);
        dest.writeString(stlAvg);
        dest.writeInt(displayType);
        dest.writeByte((byte) (isDraftedOpponent ? 1 : 0));
        dest.writeDouble(mCapDouble);
    }

    public Player() {

    }

    public boolean isDraftedOpponent() {
        return isDraftedOpponent;
    }

    public void setDraftedOpponent(boolean draftedOpponent) {
        isDraftedOpponent = draftedOpponent;
    }

    public double getCapDouble() {
        return mCapDouble;
    }

    public void setCapDouble(double capDouble) {
        mCapDouble = capDouble;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getOppVersus() {
        return oppVersus;
    }

    public void setOppVersus(String oppVersus) {
        this.oppVersus = oppVersus;
    }

    public String getCapString() {
        return mCapString;
    }

    public void setCapString(String capString) {
        this.mCapString = capString;
    }

    public String getStlAvg() {
        return stlAvg;
    }

    public void setStlAvg(String stlAvg) {
        this.stlAvg = round(Double.parseDouble(stlAvg));
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {


        double value = Double.parseDouble(price);
        value = Math.round(value * 10000d);
        value = Math.ceil(value / 5d) * 5;

        double fundsPerMatch = 10000 - value;
        DecimalFormat formatter = new DecimalFormat("$#,###");
        setCapString(formatter.format(fundsPerMatch));
        setCapDouble(fundsPerMatch);
        this.price = formatter.format(value);
    }

    public String getPtsAvg() {
        return ptsAvg;
    }

    public void setPtsAvg(String ptsAvg) {
        this.ptsAvg = round(Double.parseDouble(ptsAvg));
    }

    public String getRebAvg() {
        return rebAvg;
    }

    public void setRebAvg(String rebAvg) {
        this.rebAvg = round(Double.parseDouble(rebAvg));
    }

    public String getAstAvg() {
        return astAvg;
    }

    public void setAstAvg(String astAvg) {
        this.astAvg = round(Double.parseDouble(astAvg));
    }

    public String getBlkAvg() {
        return blkAvg;
    }

    public void setBlkAvg(String blkAvg) {
        this.blkAvg = round(Double.parseDouble(blkAvg));
    }

    public String get_3PtAvg() {
        return _3PtAvg;
    }

    public void set_3PtAvg(String _3PtAvg) {
        this._3PtAvg = round(Double.parseDouble(_3PtAvg));
    }

    public String getTOAvg() {
        return TOAvg;
    }

    public void setTOAvg(String TOAvg) {
        this.TOAvg = round(Double.parseDouble(TOAvg));
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public Boolean getDraftedOpponent() {
        return isDraftedOpponent;
    }

    public void setDraftedOpponent(Boolean drafted) {
        isDraftedOpponent = drafted;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String round(double avg) {
        BigDecimal bigDecimal = new BigDecimal(avg);
        BigDecimal roundedAvg = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
        return roundedAvg.toString();
    }

    //used for creating an organic upper bound for the normalized price of available players


    private String pV = "0";
    private String rV = "0";
    private String aV = "0";
    private String bV = "0";
    private String _3V = "0";
    private String sV = "0";
    private String toV = "0";

    public String getpV() {

        return pV;
    }
    public void setpV(String pV) {

        this.pV = pV;
    }public String getrV() {

        return rV;
    }public void setrV(String rV) {

        this.rV = rV;
    }public String getaV() {
        return aV;
    }public void setaV(String aV) {
        this.aV = aV;
    }public String getbV() {
        return bV;
    }public void setbV(String bV) {
        this.bV = bV;
    }public String get_3V() {
        return _3V;
    }public void set_3V(String _3V) {
        this._3V = _3V;
    }public String getsV() {
        return sV;
    }public void setsV(String sV) {
        this.sV = sV;
    }public String getToV() {
        return toV;
    }public void setToV(String toV) {
        this.toV = toV;


    }
}

