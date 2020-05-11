package com.example.louissankey.darkhorselayouts.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louissankey on 6/11/16.
 */
public class StatPrices implements Parcelable{
    private static double pts;
    private static double reb;
    private static double ast;
    private static double stl;
    private static double blk;
    private static double to;
    private static double _3pt;


    protected StatPrices(Parcel in) {
        pts = in.readDouble();
        reb = in.readDouble();
        ast = in.readDouble();
        stl = in.readDouble();
        blk = in.readDouble();
        to = in.readDouble();
        _3pt = in.readDouble();
    }
    public StatPrices(){

    }

    public static final Creator<StatPrices> CREATOR = new Creator<StatPrices>() {
        @Override
        public StatPrices createFromParcel(Parcel in) {
            return new StatPrices(in);
        }

        @Override
        public StatPrices[] newArray(int size) {
            return new StatPrices[size];
        }
    };

    public double getPts() {

        return pts;
    }

    public void setPts(double pts) {
        pts = Math.ceil(pts / 5d) * 5;
        this.pts = pts;
    }

    public double getReb() {
        return reb;
    }

    public void setReb(double reb) {
        reb = Math.ceil(reb / 5d) * 5;
        this.reb = reb;
    }

    public double getAst() {
        return ast;
    }

    public void setAst(double ast) {
        ast = Math.ceil(ast / 5d) * 5;
        this.ast = ast;
    }

    public double getStl() {
        return stl;
    }

    public void setStl(double stl) {
        stl = Math.ceil(stl / 5d) * 5;
        this.stl = stl;
    }

    public double getBlk() {
        return blk;
    }

    public void setBlk(double blk) {
        blk = Math.ceil(blk / 5d) * 5;
        this.blk = blk;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        to = Math.ceil(to / 5d) * 5;
        this.to = to;
    }

    public double get_3pt() {
        return _3pt;
    }

    public void set_3pt(double _3pt) {
        _3pt = Math.ceil(_3pt / 5d) * 5;
        this._3pt = _3pt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(pts);
        dest.writeDouble(reb);
        dest.writeDouble(ast);
        dest.writeDouble(stl);
        dest.writeDouble(blk);
        dest.writeDouble(to);
        dest.writeDouble(_3pt);
    }
}
