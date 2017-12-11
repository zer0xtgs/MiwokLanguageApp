package com.example.android.miwok;

public class UniversalObject {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mSoundResorseId;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;


    public UniversalObject(String mDefaultTranslation, String mMiwokTranslation, int mImageResouseId, int mSoundResorseId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResouseId;
        this.mSoundResorseId = mSoundResorseId;
    }

    public UniversalObject(String mDefaultTranslation, String mMiwokTranslation, int mSoundResorseId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mSoundResorseId = mSoundResorseId;
    }

    public int getmSoundResorseId() {
        return mSoundResorseId;
    }

    public int getmImageResouseId() {
        return mImageResourceId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}
