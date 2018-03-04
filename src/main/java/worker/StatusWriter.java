package worker;

import okhttp3.*;
import twitter4j.MediaEntity;
import twitter4j.Status;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatusWriter
{
    private String outputPath;
    private Logger logger;

    public StatusWriter(String outputPath, Logger logger)
    {
        this.outputPath = outputPath;
        this.logger = logger;
    }

    public void writeStatusToText(Status status)
    {
        MediaEntity[] mediaEntities = status.getMediaEntities();

        // Create a path if it's not exist, then use toString() to grab the string out again.
        Path statusPath = Paths.get(outputPath
                + File.separator
                + String.format("%d_%s_%s",
                                status.getId(),
                                status.getUser().getScreenName(),
                                status.getUser().getId()));


        // Iterate media entities
        if(mediaEntities != null && mediaEntities.length > 0) {
            for(MediaEntity mediaEntity : mediaEntities) {
                downloadMediaContent(statusPath, mediaEntity);
            }
        }

        // Also don't forget to write status string!
        try {
            Files.write(Paths.get(statusPath.toString() + File.separator + "content.txt"),
                    status.getText().getBytes());
        } catch (IOException error) {
            System.err.println("Failed to write Twitter Status content!");
            error.printStackTrace();
            logger.severe(error.getLocalizedMessage());
            logger.log(Level.SEVERE, error.getMessage(), error);
        }
    }

    private void downloadMediaContent(Path path, MediaEntity mediaEntity)
    {
        OkHttpClient httpClient = new OkHttpClient();

        // Roll back to HTTP if HTTPS is not available
        String mediaUrl = (mediaEntity.getMediaURLHttps() == null || mediaEntity.getMediaURLHttps().isEmpty())
                ? mediaEntity.getMediaURL()
                : mediaEntity.getMediaURLHttps();

        // Create request with the media content URL
        Request request = new Request.Builder().url(mediaUrl).build();

        // Enqueue an async request
        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException error)
            {
                System.err.println("Failed to download content, exception: " + error.getMessage());
                error.printStackTrace();
                logger.severe(error.getLocalizedMessage());
                logger.log(Level.SEVERE, error.getMessage(), error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(!response.isSuccessful()) {
                    throw new IOException("Failed to download media content! " +
                            "Code: " + response.code() +
                            "URL: " + mediaUrl);
                }

                // Since the URL didn't tell the type of the media content,
                // we should use mediaEntity.getType() here to obtain the type of this file
                // then convert to a specific file extension.
                FileOutputStream outputStream = new FileOutputStream(
                        path.toString() + File.separator +
                        mediaEntity.getId() + "." + getExtensionByType(mediaEntity.getType()));

                // Write to file
                if (response.body() != null) {
                    outputStream.write(response.body().bytes());
                } else {
                    throw new IOException("Content is empty! " +
                            "Code: " + response.code() +
                            "URL: " + mediaUrl);
                }

                outputStream.close();
            }
        });
    }

    private String getExtensionByType(String type)
    {
        switch (type) {
            case "photo":
                return "jpg";
            case "video":
                return "video";
            case "animated_gif":
                return "gif";
            default:
                return "bin";
        }
    }
}
