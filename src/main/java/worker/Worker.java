package worker;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.ConfigurationFactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;


public class Worker
{
    private boolean isStreamStarted = false;
    private ConfigurationBuilder configBuilder;
    private FilterQuery filterQuery;
    private String outputPath;
    private Logger logger;

    public Worker(FilterQuery filterQuery, String outputPath, java.util.logging.Logger logger)
    {
        this.filterQuery = filterQuery;
        this.outputPath = outputPath;
        this.logger = logger;
    }

    public Worker(FilterQuery filterQuery, java.util.logging.Logger logger)
    {
        this.filterQuery = filterQuery;
    }

    public Worker(String outputPath, java.util.logging.Logger logger)
    {
        this.outputPath = outputPath;
    }

    public void login(String consumerToken, String consumerSecret, String accessToken, String accessSecret)
    {
        Preferences prefs = Preferences.userRoot().node("Teabird");
        configBuilder = new ConfigurationBuilder();
        configBuilder.setTweetModeExtended(true) // set true to receive full text, or the results will be truncated
                .setDebugEnabled(true)
                .setOAuthConsumerKey(prefs.get("consumerToken", ""))
                .setOAuthConsumerSecret(prefs.get("consumerSecret", ""))
                .setOAuthAccessToken(prefs.get("accessToken", ""))
                .setOAuthAccessTokenSecret(prefs.get("accessSecret", ""));
    }

    public void createStream() throws TwitterException
    {
        logger.info("Worker started, creating stream...");
        isStreamStarted = true;

        StatusListener statusListener = new StatusListener()
        {
            @Override
            public void onStatus(Status status)
            {
                StatusWriter writer;

                if(outputPath == null || outputPath.isEmpty()) {
                    writer = new StatusWriter("", logger);
                } else {
                    writer = new StatusWriter(outputPath, logger);
                }

                writer.writeStatusToText(status);
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
            public void onException(Exception error)
            {
                error.printStackTrace();
                logger.severe("Error occurs on stream listener!");
                logger.severe(error.getLocalizedMessage());
                logger.log(Level.SEVERE, error.getMessage(), error);
                isStreamStarted = false;
            }
        };


        // Here we should use ConfigurationBuilder here, not TwitterStream.setOAuthAccessToken etc.
        // That one will hangs the whole program somehow...
        TwitterStream twitterStream = new TwitterStreamFactory(configBuilder.build()).getInstance();


        // Start to run the stream crawling!
        twitterStream.addListener(statusListener);

        if(filterQuery != null) {
            logger.info("Starting filter stream...");
            twitterStream.filter(filterQuery);
        } else {
            logger.info("Starting sample stream...");
            twitterStream.sample();
        }

    }

    public boolean isStreamStarted()
    {
        return isStreamStarted;
    }
}
