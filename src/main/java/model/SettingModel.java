package model;

import javafx.beans.property.StringProperty;

public class SettingModel
{
    private StringProperty consumerToken;
    private StringProperty consumerSecret;
    private StringProperty accessToken;
    private StringProperty accessSecret;

    public String getConsumerToken()
    {
        return consumerToken.get();
    }

    public StringProperty consumerTokenProperty()
    {
        return consumerToken;
    }

    public void setConsumerToken(String consumerToken)
    {
        this.consumerToken.set(consumerToken);
    }

    public String getConsumerSecret()
    {
        return consumerSecret.get();
    }

    public StringProperty consumerSecretProperty()
    {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret.set(consumerSecret);
    }

    public String getAccessToken()
    {
        return accessToken.get();
    }

    public StringProperty accessTokenProperty()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken.set(accessToken);
    }

    public String getAccessSecret()
    {
        return accessSecret.get();
    }

    public StringProperty accessSecretProperty()
    {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret)
    {
        this.accessSecret.set(accessSecret);
    }
}
