package br.com.minusculi.ifoodmobiletest.main.objects;

public class Tweet {

    private final String screenName;
    private final String profileImageUrl;
    private final String text;

    public Tweet(String screenName, String profileImageURL, String text) {
        this.screenName = screenName;
        this.profileImageUrl = profileImageURL;
        this.text = text;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getText() {
        return text;
    }
}
