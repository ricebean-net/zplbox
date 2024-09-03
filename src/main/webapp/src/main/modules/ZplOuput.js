import { saveAs } from 'file-saver';
import moment from 'moment';
import Dimensions from './../../common/util/Dimensions';


function ZplOutput({ zplData, tcpAddress, isTcpForward, onEvent, onTcpAddressUpdate }) {


    // print zpl label
    const printZplLabel = () => {
        fetch('/v1/print/' + tcpAddress, {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            },
            body: zplData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.status + " " + response.statusText);
                }
                return response.text();
            })
            .catch(error => {
                console.error('Error:', error);
                onEvent("Error Print Label.", error.message, "danger")
            });
        // .finally(() => setZplGenerationActive(false));
    }


    const copyZplToClipboard = () => {
        navigator.clipboard.writeText(zplData)
            .then(() => onEvent("Copy to clipboard successful", "Your ZPL label has been copied successfully to the clipboard.", "success"))
            .catch(() => onEvent("Error copy to clipboard", "Error copying to clipboard.", "danger"));
    }

    const saveZplAsFile = () => {
        var blob = new Blob([zplData], { type: "text/plain;charset=utf-8" });
        saveAs(blob, `label-${moment().format("YYYYMMDD-HHmmss")}.zpl.txt`);
    }


    // return final output
    return (isTcpForward === false &&
        <div>
            <div className="row mt-5 pt-2 border-top">
                <div className="col-12">
                    <small><i>ZPL Data (output):</i></small>
                    <textarea className="form-control form-control-sm font-monospace bg-body-tertiary" rows={5} value={zplData === undefined ? "" : zplData} readOnly={true} style={{ resize: 'none' }} />
                </div>
            </div>
            <div className="row mt-2">
                <div className="col-6">{zplData && Dimensions.bytes2readable(new Blob([zplData]).size)}</div>
                <div className="col-6 text-end">
                    <div className="d-inline-block me-3" style={{ width: 240 }}>
                        <div className="input-group input-group-sm">
                            <span className={`input-group-text ${zplData === undefined && 'text-body-tertiary'}`}><small>tcp://</small></span>
                            <input type="text" className={`form-control ${zplData === undefined && 'text-body-tertiary'}`} placeholder="127.0.0.1:9100" disabled={zplData === undefined} value={tcpAddress ? tcpAddress : ""} onChange={(e) => e.target.value === "" ? onTcpAddressUpdate(undefined) : onTcpAddressUpdate(e.target.value)} />
                            <button type="button" className="btn btn-outline-secondary" onClick={printZplLabel} disabled={zplData === undefined}><i className="bi bi-printer me-2"></i>Print</button>
                        </div>
                    </div>
                    <button type="button" className="btn btn-outline-secondary btn-sm me-3" onClick={copyZplToClipboard} disabled={zplData === undefined}><i className="bi bi-copy me-2"></i>Copy to Clipboard</button>
                    <button type="button" className="btn btn-outline-secondary btn-sm" onClick={saveZplAsFile} disabled={zplData === undefined}><i className="bi bi-cloud-download me-2"></i>Download</button>


                </div>
            </div>
        </div>
    )
}

export default ZplOutput;