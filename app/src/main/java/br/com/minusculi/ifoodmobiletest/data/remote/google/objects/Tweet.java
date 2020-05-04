package br.com.minusculi.ifoodmobiletest.data.remote.google.objects;

import java.io.Serializable;

public class Tweet implements Serializable {

    private static final long serialVersionUID = -2427038569497198517L;

    public Tweet(String username, String userUrlPicture, String text) {
        this.username = username;
        this.userUrlPicture = userUrlPicture;
        this.text = text;
    }

    public String username;
    public String userUrlPicture;
    public String text;

}
