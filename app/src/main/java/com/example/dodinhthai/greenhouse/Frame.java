package com.example.dodinhthai.greenhouse;

public class Frame {
    private int mId;
    private String mTitle;
    private String mCreatedat;
    private boolean mStatus;
    private String mDays;

    public void setId(int Id) {
        mId = Id;
    }

    public int getId() {
        return mId;
    }

    public void setTitle(String Title) {
        mTitle = Title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setCreatedat(String Createdat) {
        mCreatedat = Createdat;
    }

    public String getCreatedat() {
        return mCreatedat;
    }

    public void setStatus(boolean Status) {
        mStatus = Status;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public void setDays(String Days) {
        mDays = Days;
    }

    public String getDays() {
        return mDays;
    }
}
