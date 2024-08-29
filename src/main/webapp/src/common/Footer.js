import React, { useState, useEffect } from 'react';

import packageJson from '../../package.json';

function Footer() {

    // resolution
    const [resolution, setResolution] = useState(undefined);

    useEffect(() => {
        const handleResize = () => {
            setResolution(window.innerWidth + " x " + window.innerHeight);
        };

        handleResize();
        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, []);


    // update version periodically
    const [version, setVersion] = useState("n. a.");

    useEffect(() => {
        updateVersion();
        const interval = setInterval(() => updateVersion(), 5000);

        return () => clearInterval(interval);
    }, []);

    function updateVersion() {

        fetch("/version")
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
            })
            .then(version => {
                if (version == null) {
                    setVersion("n. a.");

                } else {
                    // build version text
                    var versionText = "v" + version.appVersion

                    if (version.commitId) {
                        versionText += "-" + version.commitId;
                    }

                    if (version.environment) {
                        versionText += "-" + version.environment;
                    }

                    if (version.buildTime) {
                        versionText += " · (" + version.buildTime + ")";
                    }

                    // set text
                    setVersion(versionText);
                }
            });
    }

    // return final output
    return (
        <div className="container-fluid bg-dark text-light px-3 py-2">
            <small><small>
                <div className="row row-cols-2">
                    <div className="col">
                        ZplBox Community Edition
                    </div>
                    <div className="col text-end">
                        Apache-2.0 license
                    </div>
                </div>
                <div className="row row-cols-2 pt-1">
                    <div className="col">
                        {version}
                    </div>
                    <div className="col text-end">
                        Client: v{packageJson.version} · <span title="The browsers current resolution. Optimal: 1920 x 1000 px">({resolution} px)</span>
                    </div>
                </div>
            </small></small>
        </div>
    );

}

export default Footer;

