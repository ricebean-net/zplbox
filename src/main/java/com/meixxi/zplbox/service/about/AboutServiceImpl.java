package com.meixxi.zplbox.service.about;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class AboutServiceImpl implements AboutService {

    private static final long startTime = System.currentTimeMillis();

    private static final String hostname = readHostname();

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String version;

    @Value("${app.buildtime}")
    private String buildTime;

    @Value("${COMMIT_SHA:n. a.}")
    private String commitId;


    /**
     * Default constructor.
     */
    public AboutServiceImpl() {
    }

    /**
     * Read and returns the system's hostname.
     * @return The system's hostname as string.
     */
    private static String readHostname() {
        String hostname;

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "UNKNOWN";
        }

        return hostname;
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getAppName() {
        return this.appName;
    }

    @Override
    public String getBuildTime() {
        return this.buildTime;
    }

    @Override
    public String getCommitId() {
        return this.commitId;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public String getHostname() {
        return hostname;
    }
}
