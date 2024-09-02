import { useState, useEffect } from "react";

function ZplGeneration({ payload, endpoint, tcpAddress, isTcpForward, zplGenerationActive, onGenerateLabel, onTcpAddressUpdate, onTcpForwardUpdate }) {

    // construct curl request
    const [cmdCurl, setCmdCurl] = useState(undefined);

    useEffect(() => {
        if (payload === undefined) {
            setCmdCurl(undefined);
            return;
        }

        const baseUrl = `${window.location.protocol}//${window.location.hostname}${window.location.port ? ':' + window.location.port : ''}`;

        const cmdLines = [
            `curl --request POST`,
            `     --url ${baseUrl}/v1/${endpoint}`,
            `     --header 'content-type: application/json'`,
            `     --data '${JSON.stringify(payload)}'`
        ]

        setCmdCurl(cmdLines.join(" \\ \n"));
    }, [payload])


    // return final output
    return (

        <div className="row mt-2">
            <div className="col-2 pt-1">Create and Print:</div>
            <div className="col-2 pt-1">
                    <div className="form-check form-switch">
                        <input className="form-check-input" type="checkbox" role="switch" checked={isTcpForward} onChange={(e) => onTcpForwardUpdate(e.target.checked)} />
                    </div>

                    <div>
                        <small>{isTcpForward === true ? 'Generate and Send' : 'Generate only'}</small>
                    </div>
            </div>
            <div className="col-3 border-end pe-4 text-end d-flex flex-column">
                <div className="input-group mb-auto">
                    <span className={`input-group-text`}><small>tcp://</small></span>
                    <input type="text" className={`form-control`} placeholder="127.0.0.1:9100" value={tcpAddress ? tcpAddress : ""} disabled={!isTcpForward} onChange={(e) => e.target.value === "" ? onTcpAddressUpdate(undefined) : onTcpAddressUpdate(e.target.value)} />
                </div>

                <div>
                    <button type="button" className="btn btn-outline-primary" disabled={payload === undefined} onClick={onGenerateLabel}>
                        <div className={`spinner-border spinner-border-sm me-2 ${zplGenerationActive === false && 'd-none'}`} role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                        Create {isTcpForward === true ? 'and Print' : ''} ZPL Label
                    </button>
                </div>
            </div>
            <div className="col-5 ps-4">
                <small><i>CLI Command (example):</i></small>
                <textarea className="form-control form-control-sm font-monospace bg-body-tertiary mb-2" wrap="off" rows={5} value={cmdCurl === undefined ? "" : cmdCurl} readOnly={true} style={{ resize: 'none' }} />
            </div>
        </div>
    );

}

export default ZplGeneration;