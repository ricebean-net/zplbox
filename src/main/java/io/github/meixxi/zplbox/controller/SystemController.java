package io.github.meixxi.zplbox.controller;

import io.github.meixxi.zplbox.service.about.AboutService;
import io.github.meixxi.zplbox.util.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private AboutService aboutService;

    @GetMapping(value = "/version", produces= MediaType.APPLICATION_JSON_VALUE)
    public Version version() {
        return new Version();
    }

    @GetMapping(value = "/status", produces= MediaType.APPLICATION_JSON_VALUE)
    public Status status() {
        return new Status();
    }

    /**
     * Private model class for status.
     */
    private class Status {
        private final String status;
        private final long startTime;
        private final String startTimeReadable;
        private final long duration;
        private final String durationReadable;
        private final String hostname;
        private final Version version;

        private Status() {
            this.status = "UP";
            this.version = new Version();
            this.startTime = aboutService.getStartTime();
            this.startTimeReadable = Times.millis2readable(aboutService.getStartTime());
            this.duration = System.currentTimeMillis() - aboutService.getStartTime();
            this.durationReadable = Times.duration2readable(this.duration);
            this.hostname = aboutService.getHostname();
        }

        public String getStatus() {
            return status;
        }

        public long getStartTime() {
            return startTime;
        }

        public String getStartTimeReadable() {
            return startTimeReadable;
        }

        public long getDuration() {
            return duration;
        }

        public String getDurationReadable() {
            return durationReadable;
        }

        public String getHostname() {
            return hostname;
        }

        public Version getVersion() {
            return version;
        }
    }

    /**
     * Private model class for version
     */
    private class Version {
        private final String appName;
        private final String appVersion;
        private final String commitId;

        private Version() {
            this.appName = aboutService.getAppName();
            this.appVersion = aboutService.getAppVersion();
            this.commitId = aboutService.getCommitId();
        }

        public String getAppName() {
            return appName;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public String getCommitId() {
            return commitId;
        }
    }

}
