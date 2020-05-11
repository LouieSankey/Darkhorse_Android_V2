package com.example.louissankey.darkhorselayouts.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louissankey on 5/24/16.
 */
public class StandingsListItem implements Parcelable{

    private String playerName;
    private String username;
    private boolean isDraftedOpponent;
    private int displayType;
    private String fantasyPoints;

    protected StandingsListItem(Parcel in) {
        playerName = in.readString();
        username = in.readString();
        isDraftedOpponent = in.readByte() != 0;
        displayType = in.readInt();
        fantasyPoints = in.readString();
    }

    public StandingsListItem(){

    }

    public static final Creator<StandingsListItem> CREATOR = new Creator<StandingsListItem>() {
        @Override
        public StandingsListItem createFromParcel(Parcel in) {
            return new StandingsListItem(in);
        }

        @Override
        public StandingsListItem[] newArray(int size) {
            return new StandingsListItem[size];
        }
    };

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public boolean isDraftedOpponent() {return isDraftedOpponent;}

    public void setDraftedOpponent(boolean draftedOpponent) {isDraftedOpponent = draftedOpponent;}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerName);
        dest.writeString(username);
        dest.writeByte((byte) (isDraftedOpponent ? 1 : 0));
        dest.writeInt(displayType);
        dest.writeString(fantasyPoints);
    }

    public String getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(String fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }
}
