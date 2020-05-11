package com.example.louissankey.darkhorselayouts.Model;

import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louissankey on 5/8/16.
 */
public class ContestItem implements Parcelable{
    private String gameType;
    private String accepting;
    private String nbaGamesAmnt;
    private long draftEnds;
    private long buyingEnds;
    private String positionsPaid;
    private String entryAmnt;


    public ContestItem(){

    }


    protected ContestItem(Parcel in) {
        gameType = in.readString();
        accepting = in.readString();
        nbaGamesAmnt = in.readString();
        draftEnds = in.readLong();
        positionsPaid = in.readString();
        entryAmnt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameType);
        dest.writeString(accepting);
        dest.writeString(nbaGamesAmnt);
        dest.writeLong(draftEnds);
        dest.writeString(positionsPaid);
        dest.writeString(entryAmnt);
    }

    public static final Creator<ContestItem> CREATOR = new Creator<ContestItem>() {
        @Override
        public ContestItem createFromParcel(Parcel in) {
            return new ContestItem(in);
        }

        @Override
        public ContestItem[] newArray(int size) {
            return new ContestItem[size];
        }
    };


    public void setDraftCountdown(long countdownDuration) {
        setDraftEnds(countdownDuration);

        new CountDownTimer(countdownDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                setDraftEnds(millisUntilFinished);


            }
            @Override
            public void onFinish() {

            }

        }.start();

    }
    public long getDraftEnds() {
        return draftEnds;
    }

    public void setDraftEnds(long draftEnds) {
        this.draftEnds = draftEnds;
    }

    public long getBuyingEnds() {
        return buyingEnds;
    }

    public void setBuyingEnds(long buyingEnds) {
        this.buyingEnds = buyingEnds;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getAccepting() {
        return accepting;
    }

    public void setAccepting(String accepting) {
        this.accepting = accepting;
    }

    public String getNbaGamesAmnt() {
        return nbaGamesAmnt;
    }

    public void setNbaGamesAmnt(String nbaGamesAmnt) {
        this.nbaGamesAmnt = nbaGamesAmnt;
    }

    public String getPositionsPaid() {
        return positionsPaid;
    }

    public void setPositionsPaid(String positionsPaid) {
        this.positionsPaid = positionsPaid;
    }

    public String getEntryAmnt() {
        return entryAmnt;
    }

    public void setEntryAmnt(String entryAmnt) {
        this.entryAmnt = entryAmnt;
    }


    @Override
    public int describeContents() {
        return 0;
    }


}
