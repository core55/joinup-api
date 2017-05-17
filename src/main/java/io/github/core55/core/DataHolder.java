/*
  Authors: S. Stefani
 */

package io.github.core55.core;

public class DataHolder {

    private static final DataHolder holder = new DataHolder();

    private String sendGridKey;
    private String googleSignInKey;

    public static DataHolder getInstance() {
        return holder;
    }

    public String getSendGridKey() {
        return sendGridKey;
    }

    public void setSendGridKey(String sendGridKey) {
        this.sendGridKey = sendGridKey;
    }

    public String getGoogleSignInKey() {
        return googleSignInKey;
    }

    public void setGoogleSignInKey(String googleSignInKey) {
        this.googleSignInKey = googleSignInKey;
    }
}
