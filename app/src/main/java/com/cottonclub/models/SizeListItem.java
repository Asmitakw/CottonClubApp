package com.cottonclub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SizeListItem implements Parcelable {
   private String S;
   private String M;
   private String L;
   private String XL;
   private String XXL;
   private String XXXL;
   private String size1;
   private String size2;
   private String size3;
   private String size4;
   private String size5;
   private String size6;
   private String size8;
   private String size10;
   private String size12;
   private String size14;
   private String size16;
   private String size18;
   private String size20;
   private String size22;
   private String size24;
   private String size26;
   private String size28;
   private String size30;
   private String size32;
   private String size34;
   private String size36;
   private String NB;
   private String size0b3;
   private String size3b6;
   private String size6b9;
   private String size9b12;

   private String designType;

    public SizeListItem(Parcel in) {
        S = in.readString();
        M = in.readString();
        L = in.readString();
        XL = in.readString();
        XXL = in.readString();
        XXXL = in.readString();
        size1 = in.readString();
        size2 = in.readString();
        size3 = in.readString();
        size4 = in.readString();
        size5 = in.readString();
        size6 = in.readString();
        size8 = in.readString();
        size10 = in.readString();
        size12 = in.readString();
        size14 = in.readString();
        size16 = in.readString();
        size18 = in.readString();
        size20 = in.readString();
        size22 = in.readString();
        size24 = in.readString();
        size26 = in.readString();
        size28 = in.readString();
        size30 = in.readString();
        size32 = in.readString();
        size34 = in.readString();
        size36 = in.readString();
        NB = in.readString();
        size0b3 = in.readString();
        size3b6 = in.readString();
        size6b9 = in.readString();
        size9b12 = in.readString();
        designType = in.readString();
    }

    public static final Creator<SizeListItem> CREATOR = new Creator<SizeListItem>() {
        @Override
        public SizeListItem createFromParcel(Parcel in) {
            return new SizeListItem(in);
        }

        @Override
        public SizeListItem[] newArray(int size) {
            return new SizeListItem[size];
        }
    };

    public SizeListItem() {

    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    public String getXL() {
        return XL;
    }

    public void setXL(String XL) {
        this.XL = XL;
    }

    public String getXXL() {
        return XXL;
    }

    public void setXXL(String XXL) {
        this.XXL = XXL;
    }

    public String getXXXL() {
        return XXXL;
    }

    public void setXXXL(String XXXL) {
        this.XXXL = XXXL;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public String getSize2() {
        return size2;
    }

    public void setSize2(String size2) {
        this.size2 = size2;
    }

    public String getSize3() {
        return size3;
    }

    public void setSize3(String size3) {
        this.size3 = size3;
    }

    public String getSize4() {
        return size4;
    }

    public void setSize4(String size4) {
        this.size4 = size4;
    }

    public String getSize5() {
        return size5;
    }

    public void setSize5(String size5) {
        this.size5 = size5;
    }

    public String getSize6() {
        return size6;
    }

    public void setSize6(String size6) {
        this.size6 = size6;
    }

    public String getSize8() {
        return size8;
    }

    public void setSize8(String size8) {
        this.size8 = size8;
    }

    public String getSize10() {
        return size10;
    }

    public void setSize10(String size10) {
        this.size10 = size10;
    }

    public String getSize12() {
        return size12;
    }

    public void setSize12(String size12) {
        this.size12 = size12;
    }

    public String getSize14() {
        return size14;
    }

    public void setSize14(String size14) {
        this.size14 = size14;
    }

    public String getSize16() {
        return size16;
    }

    public void setSize16(String size16) {
        this.size16 = size16;
    }

    public String getSize20() {
        return size20;
    }

    public void setSize20(String size20) {
        this.size20 = size20;
    }

    public String getSize22() {
        return size22;
    }

    public void setSize22(String size22) {
        this.size22 = size22;
    }

    public String getSize24() {
        return size24;
    }

    public void setSize24(String size24) {
        this.size24 = size24;
    }

    public String getSize26() {
        return size26;
    }

    public void setSize26(String size26) {
        this.size26 = size26;
    }

    public String getSize28() {
        return size28;
    }

    public void setSize28(String size28) {
        this.size28 = size28;
    }

    public String getSize30() {
        return size30;
    }

    public void setSize30(String size30) {
        this.size30 = size30;
    }

    public String getSize32() {
        return size32;
    }

    public void setSize32(String size32) {
        this.size32 = size32;
    }

    public String getSize34() {
        return size34;
    }

    public void setSize34(String size34) {
        this.size34 = size34;
    }

    public String getSize36() {
        return size36;
    }

    public void setSize36(String size36) {
        this.size36 = size36;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public String getNB() {
        return NB;
    }

    public void setNB(String NB) {
        this.NB = NB;
    }

    public String getSize0b3() {
        return size0b3;
    }

    public void setSize0b3(String size0b3) {
        this.size0b3 = size0b3;
    }

    public String getSize3b6() {
        return size3b6;
    }

    public void setSize3b6(String size3b6) {
        this.size3b6 = size3b6;
    }

    public String getSize6b9() {
        return size6b9;
    }

    public void setSize6b9(String size6b9) {
        this.size6b9 = size6b9;
    }

    public String getSize9b12() {
        return size9b12;
    }

    public void setSize9b12(String size9b12) {
        this.size9b12 = size9b12;
    }

    public String getSize18() {
        return size18;
    }

    public void setSize18(String size18) {
        this.size18 = size18;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(S);
        parcel.writeString(M);
        parcel.writeString(L);
        parcel.writeString(XL);
        parcel.writeString(XXL);
        parcel.writeString(XXXL);
        parcel.writeString(size1);
        parcel.writeString(size2);
        parcel.writeString(size3);
        parcel.writeString(size4);
        parcel.writeString(size5);
        parcel.writeString(size6);
        parcel.writeString(size8);
        parcel.writeString(size10);
        parcel.writeString(size12);
        parcel.writeString(size14);
        parcel.writeString(size16);
        parcel.writeString(size18);
        parcel.writeString(size20);
        parcel.writeString(size22);
        parcel.writeString(size24);
        parcel.writeString(size26);
        parcel.writeString(size28);
        parcel.writeString(size30);
        parcel.writeString(size32);
        parcel.writeString(size34);
        parcel.writeString(size36);
        parcel.writeString(NB);
        parcel.writeString(size0b3);
        parcel.writeString(size3b6);
        parcel.writeString(size6b9);
        parcel.writeString(size9b12);
        parcel.writeString(designType);
    }
}
