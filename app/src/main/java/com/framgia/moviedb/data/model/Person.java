package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
    public static final String EMPTY_STRING = "";
    private int mId;
    private String mName;
    private String mCreditId;
    private String mProfilePath;
    private int mGender;
    private String mBirthday;
    private String mPlaceOfBirth;
    private String mBiography;

    public Person() {
    }

    public Person(JSONObject item) throws JSONException {
        mId = item.getInt(PersonJsonKey.ID);
        mName = item.getString(PersonJsonKey.NAME);
        mGender = item.getInt(PersonJsonKey.GENDER);
        mProfilePath = item.getString(PersonJsonKey.PROFILE_PATH);
        mCreditId = item.optString(PersonJsonKey.CREDIT_ID, EMPTY_STRING);
        mBiography = item.optString(PersonJsonKey.BIOGRAPHY, EMPTY_STRING);
        mBirthday = item.optString(PersonJsonKey.BIRTHDAY, EMPTY_STRING);
        mPlaceOfBirth = item.optString(PersonJsonKey.PLACE_OF_BIRTH, EMPTY_STRING);
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public int isGender() {
        return mGender;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static class PersonJsonKey {
        public static final String ID = "id";
        public static final String CREDIT_ID = "credit_id";
        public static final String CAST_ID = "cast_id";
        public static final String GENDER = "gender";
        public static final String NAME = "name";
        public static final String DEPARTMENT = "department";
        public static final String JOB = "job";
        public static final String PROFILE_PATH = "profile_path";
        public static final String ORDER = "order";
        public static final String CHARACTER = "character";
        public static final String PLACE_OF_BIRTH = "place_of_birth";
        public static final String BIRTHDAY = "birthday";
        public static final String BIOGRAPHY = "biography";
    }
}
