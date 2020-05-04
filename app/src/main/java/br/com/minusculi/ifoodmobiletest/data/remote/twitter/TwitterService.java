package br.com.minusculi.ifoodmobiletest.data.remote.twitter;

import java.util.ArrayList;
import java.util.List;

import br.com.minusculi.ifoodmobiletest.main.objects.Tweet;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

    private static TwitterService instance;
    private Parameters parameters;
    private Twitter twitter;

    private TwitterService() {
    }

    public static TwitterService getInstance() {
        if (null == instance)
            instance = new TwitterService();
        return instance;
    }

    public TwitterService setParameters(Parameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public void configure() throws Exception {
        if (parameters == null) throw new TwitterException("API parameters have not been set.");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false)
                .setOAuthConsumerKey(parameters.getConsumerKey())
                .setOAuthConsumerSecret(parameters.getConsumerSecret())
                .setOAuthAccessToken(parameters.getAccessToken())
                .setOAuthAccessTokenSecret(parameters.getAccessTokenSecret());
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    public List<Tweet> loadTweets(String username, int page) throws TwitterException {
        if (twitter == null) throw new TwitterException("Twitter service has not been initialized");
        List<Status> statuses = twitter.getUserTimeline(username, new Paging(page));
        List<Tweet> tweets = new ArrayList<>();
        for (Status status : statuses) {
            tweets.add(new Tweet(status.getUser().getScreenName(), status.getUser().get400x400ProfileImageURL(), status.getText()));
        }
        return tweets;
    }

    public List<User> searchUsers(String username) throws TwitterException {
        if (twitter == null) throw new TwitterException("Twitter service has not been initialized");
        return twitter.searchUsers(username, 1);
    }
}
