package com.exozz.topquiz.model;

/**
 * Created by Axel on 28/04/2019.
 */

public class User {
    private String mFirstName;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    @Override
    public String toString() {
        return "user{" +
                "mFirstName='" + mFirstName + '\'' +
                '}';
    }

}
