package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class CrawlerSettingModel
{
    public boolean isStrictMode()
    {
        return strictMode.get();
    }

    public BooleanProperty strictModeProperty()
    {
        return strictMode;
    }

    public void setStrictMode(boolean strictMode)
    {
        this.strictMode.set(strictMode);
    }

    public String getKeywords()
    {
        return keywords.get();
    }

    public StringProperty keywordsProperty()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords.set(keywords);
    }

    public String getUsers()
    {
        return users.get();
    }

    public StringProperty usersProperty()
    {
        return users;
    }

    public void setUsers(String users)
    {
        this.users.set(users);
    }

    public String getOptPath()
    {
        return optPath.get();
    }

    public StringProperty optPathProperty()
    {
        return optPath;
    }

    public void setOptPath(String optPath)
    {
        this.optPath.set(optPath);
    }

    public String getLanguages()
    {
        return languages.get();
    }

    public StringProperty languagesProperty()
    {
        return languages;
    }

    public void setLanguages(String languages)
    {
        this.languages.set(languages);
    }

    private BooleanProperty strictMode;
    private StringProperty keywords;
    private StringProperty users;
    private StringProperty optPath;
    private StringProperty languages;
}
