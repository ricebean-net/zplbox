import { useState } from "react";
import {saveAs} from "file-saver"

function Main() {


    // label url handling
    const [urlLabel, setUrlLabel] = useState("");

    const baseUrl = `${window.location.protocol}//${window.location.hostname}${window.location.port ? ':' + window.location.port : ''}`;
    const urlTestLabels = [
        { descriptiveName: "UPS Example (4 x 8 inches)", url: baseUrl + "/labels/ups-example.html" },
        { descriptiveName: "Generic Example (4 x 6 inches)", url: baseUrl + "/xxx.html" }
    ]


    // zpl label generation
    const [zplData, setZplData] = useState("")

    const generateZplLabel = () => {

        // create data
        const data = {
            url: urlLabel,
            widthPts: 812,
            heightPts: 1624
        }

        fetch('/v1/html2zpl', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(zplData => {
                setZplData(zplData);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }


    // zpl export 
    const copyZplToClipboard = () => {
        navigator.clipboard.writeText(zplData)
            .then(() => console.log("Copy to clipboard successful"))
            .catch(() => console.log("Error copying to clipboard"))
    }

    const saveZplAsFile = () => {
        var blob = new Blob([zplData], {type: "text/plain;charset=utf-8"});
        saveAs(blob, "hello world.zpl.txt");
    }


    // return final output
    return (
        <div className="container mt-4">

            {/* label url */}
            <div className="row">
                <div className="col-2">Label URL (HTML/PDF):</div>
                <div className="col-4">
                    <div className="input-group">
                        <input type="text" className="form-control" placeholder="name@example.com" value={urlLabel} onChange={(e) => setUrlLabel(e.target.value)} />
                        <button type="button" className="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                            <span className="visually-hidden">Toggle Dropdown</span>
                        </button>
                        <ul className="dropdown-menu dropdown-menu-end w-100">
                            {urlTestLabels.map((urlTestUrl, idx) =>
                                <li key={'testLabel-' + idx}>
                                    <button type="button" className="dropdown-item py-2" onClick={() => setUrlLabel(urlTestUrl.url)}>
                                        {urlTestUrl.descriptiveName}<br />
                                        <small>{urlTestUrl.url}</small>
                                    </button>

                                </li>
                            )}

                        </ul>
                    </div>
                </div>
                <div className="col-6">asfa</div>
            </div>

            {/* create label */}
            <div className="row mt-2">
                <div className="col-2"></div>
                <div className="col-4 text-end"><button type="button" className="btn btn-outline-primary" onClick={generateZplLabel}>Create ZPL Label</button></div>
                <div className="col-6"></div>
            </div>

            {/* zpl data */}
            <div className="row mt-5">
                <div className="col-2">ZPL Data:</div>
                <div className="col-10">
                    <textarea className="form-control form-control-sm font-monospace" rows="10" value={zplData} readOnly={true}></textarea>
                </div>
            </div>
            <div className="row mt-2">
                <div className="col-2"></div>
                <div className="col-5"></div>
                <div className="col-5 text-end">
                    <button type="button" className="btn btn-outline-secondary btn-sm me-3" onClick={copyZplToClipboard}><i className="bi bi-copy me-2"></i>Copy to Clipboard</button>
                    <button type="button" className="btn btn-outline-secondary btn-sm" onClick={saveZplAsFile}><i className="bi bi-cloud-download me-2"></i>Download</button>
                </div>
            </div>
        </div>
    );
}

export default Main;