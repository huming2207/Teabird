package Worker;

import twitter4j.*;
import twitter4j.auth.AccessToken;


public class Worker
{
    private boolean isStreamStarted = false;
    private AccessToken accessToken;
    private String consumerToken;
    private String consumerSecret;
    private FilterQuery filterQuery;

    public FilterQuery getFilterQuery()
    {
        return filterQuery;
    }

    public void setFilterQuery(FilterQuery filterQuery)
    {
        this.filterQuery = filterQuery;
    }

    public void login(AccessToken accessToken, String consumerToken, String consumerSecret)
    {
        this.accessToken = accessToken;
        this.consumerToken = consumerToken;
        this.consumerSecret = consumerSecret;
    }

    public void createStream()
    {
        isStreamStarted = true;

        StatusListener statusListener = new StatusListener()
        {
            @Override
            public void onStatus(Status status)
            {

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
            {

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses)
            {

            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId)
            {

            }

            @Override
            public void onStallWarning(StallWarning warning)
            {

            }

            @Override
            public void onException(Exception ex)
            {
                ex.printStackTrace();
                isStreamStarted = false;
            }
        };


        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthAccessToken(this.accessToken);
        twitterStream.setOAuthConsumer(this.consumerToken, this.consumerSecret);

        // Start to run the stream crawling!
        twitterStream.addListener(statusListener);

        if(filterQuery != null) {
            twitterStream.filter(filterQuery);
        } else {
            twitterStream.sample();
        }

    }

    public boolean isStreamStarted()
    {
        return isStreamStarted;
    }
}
