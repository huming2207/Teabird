package worker;

import org.ini4j.Wini;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.IOException;

public class SettingLoader
{
    public static AccessToken getAccessToken()
    {
        Wini ini = null;
        try {
            ini = new Wini(new File("settings.ini"));
        } catch (IOException error) {
            System.err.println("Setting invalid, check before use.");
            return null;
        }

        String accessToken = ini.get("access", "token");
        String accessSecret = ini.get("access", "secret");

        return new AccessToken(accessToken, accessSecret);
    }

    public static String getConsumerToken()
    {
        Wini ini = null;
        try {
            ini = new Wini(new File("settings.ini"));
        } catch (IOException error) {
            System.err.println("Setting invalid, check before use.");
            return null;
        }

        return ini.get("consumer", "token");
    }

    public static String getConsumerTokenSecret()
    {
        Wini ini = null;
        try {
            ini = new Wini(new File("settings.ini"));
        } catch (IOException error) {
            System.err.println("Setting invalid, check before use.");
            return null;
        }

        return ini.get("consumer", "secret");
    }

    public static void writeSettings(String consumerToken, String consumerSecret, String accessToken, String accessSecret)
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
}
