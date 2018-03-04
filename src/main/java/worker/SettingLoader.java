package worker;

import org.ini4j.Wini;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.IOException;

public class SettingLoader
{
    private static String getString(String section, String option)
    {
        Wini ini = null;
        try {
            ini = new Wini(new File("settings.ini"));
        } catch (IOException error) {
            System.err.println("Setting invalid, check before use.");
            return null;
        }

        return ini.get(section, option);
    }

    public static AccessToken getAccessToken()
    {
        String accessToken = getString("access", "token");
        String accessSecret = getString("access", "secret");

        if (accessToken != null && accessSecret != null) {
            return new AccessToken(accessToken, accessSecret);
        } else {
            return new AccessToken("","");
        }
    }

    public static String getAccessTokenStr()
    {
        return getString("access", "token");
    }

    public static String getAccessSecretStr()
    {
        return getString("access", "secret");
    }

    public static String getConsumerToken()
    {
        return getString("consumer", "token");
    }

    public static String getConsumerTokenSecret()
    {
        return getString("consumer", "secret");
    }

    public static String getKeywords()
    {
        return getString("crawler", "keywords");
    }

    public static String getUsers()
    {
        return getString("crawler", "users");
    }

    public static boolean getStrictFilterMode()
    {
        String value = getString("crawler", "strict");

        if(value != null)
            return value.equals("yes");
        else
            return false;
    }

    public static String getLanguages()
    {
        return getString("crawler", "languages");
    }

    public static String getOptPaths()
    {
        return getString("crawler", "optPaths");
    }

    public static void writeApiSettings(String consumerToken,
                                        String consumerSecret,
                                        String accessToken,
                                        String accessSecret)
    {
        Wini ini = null;
        try {
            ini = new Wini(new File("settings.ini"));
            ini.put("consumer", "token", consumerToken);
            ini.put("consumer", "secret", consumerSecret);
            ini.put("access", "token", accessToken);
            ini.put("access", "secret", accessSecret);
            ini.store();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void writeCrawlerSettings(String keywords,
                                            String userIds,
                                            boolean strictFilter,
                                            String languages,
                                            String optPaths)
    {
        Wini ini;

        try {
            ini = new Wini(new File("settings.ini"));
            ini.put("crawler", "keywords", keywords);
            ini.put("crawler", "users", userIds);
            ini.put("crawler", "strict", strictFilter ? "yes" : "no");
            ini.put("crawler","languages",languages);
            ini.put("crawler", "optPaths", optPaths);
            ini.store();
        } catch(IOException error) {
            error.printStackTrace();
        }
    }
}
