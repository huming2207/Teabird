package worker.config;

import twitter4j.FilterQuery;

public class FilterQueryGenerator
{
    public FilterQueryGenerator(long[] followUsers, String[] trackKeywords)
    {
        this.followUsers = followUsers;
        this.trackKeywords = trackKeywords;
    }

    public FilterQueryGenerator(String[] trackKeywords)
    {
        this.trackKeywords = trackKeywords;
    }

    public int getTwitterCount()
    {
        return twitterCount;
    }

    public void setTwitterCount(int twitterCount)
    {
        this.twitterCount = twitterCount;
    }


    public String[] getTrackKeywords()
    {
        return trackKeywords;
    }

    public void setTrackKeywords(String[] trackKeywords)
    {
        this.trackKeywords = trackKeywords;
    }

    public FilterLevel getFilterLevel()
    {
        return filterLevel;
    }

    public void setFilterLevel(FilterLevel filterLevel)
    {
        this.filterLevel = filterLevel;
    }

    public double[] getLocations()
    {
        return locations;
    }

    public void setLocations(double[] locations)
    {
        this.locations = locations;
    }

    public String[] getLanguage()
    {
        return languages;
    }

    public void setLanguage(String[] language)
    {
        this.languages = language;
    }

    public long[] getFollowUserId()
    {
        return followUsers;
    }

    public void setFollowUserId(long[] followUserId)
    {
        this.followUsers = followUserId;
    }

    public FilterQuery getFilterQuery()
    {
        FilterQuery query = new FilterQuery();

        if(twitterCount > 1) {
            query.count(twitterCount);
        }

        if(followUsers != null) {
            query.follow(followUsers);
        }

        if(trackKeywords != null) {
            query.track(trackKeywords);
        }

        if(filterLevel != null) {
            query.filterLevel(filterLevel.toString());
        }

        if(languages != null) {
            query.language(languages);
        }

        return query;
    }

    private int twitterCount;
    private String[] trackKeywords;
    private FilterLevel filterLevel;
    private double[] locations;
    private String[] languages;
    private long[] followUsers;
}
