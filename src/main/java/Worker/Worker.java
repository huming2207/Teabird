package Worker;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Worker
{
    private Twitter twitterClient;

    public Worker()
    {
        // Initialise twitter client
        TwitterFactory twitterFactory = new TwitterFactory();
        twitterClient = twitterFactory.getInstance();
    }

    public void login(AccessToken accessToken, String consumerToken, String consumerSecret)
    {
        twitterClient.setOAuthConsumer(consumerToken, consumerSecret);
        twitterClient.setOAuthAccessToken(accessToken);
    }


}
