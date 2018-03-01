package Worker;

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
}
