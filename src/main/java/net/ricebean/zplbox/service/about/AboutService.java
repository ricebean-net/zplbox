package net.ricebean.zplbox.service.about;

/**
 * Service interface providing details about the current library.
 */
public interface AboutService {

    /**
     * Returns the name of the application.
     * @return The applications name.
     */
    String getAppName();

    /**
     * Returns the version of the application.
     * @return The applications version.
     */
    String getAppVersion();

    /**
     * Retruns the build time of the application.
     * @return The applications build time.
     */
    String getBuildTime();

    /**
     * Returns the full commit id of the latest change.
     * @return The latest full commit id.
     */
    String getCommitId();

    /**
     * Returns the service's start time.
     * @return The service's start time.
     */
    long getStartTime();

    /**
     * Returns the service's hostname.
     * @return The service's hostname.
     */
    String getHostname();

}
