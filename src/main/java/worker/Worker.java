package worker;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Worker
{
    private boolean isStreamStarted = false;
    private AccessToken accessToken;
    private String consumerToken;
    private String consumerSecret;
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

    public void login(AccessToken accessToken, String consumerToken, String consumerSecret)
    {
        this.accessToken = accessToken;
        this.consumerToken = consumerToken;
        this.consumerSecret = consumerSecret;
    }

    public void createStream()
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


        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthAccessToken(this.accessToken);
        twitterStream.setOAuthConsumer(this.consumerToken, this.consumerSecret);


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
