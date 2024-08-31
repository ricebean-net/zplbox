import { useEffect, useState } from "react";
import { saveAs } from "file-saver"
import Toast from "./Toast";
import LabelSource from "./modules/LabelSource";
import ConversionConfig from "./modules/ConversionConfig";
import Dimensions from './../common/util/Dimensions';



function Main() {


    // event handling (toasts)
    const [event, setEvent] = useState(undefined);

    const showEvent = (title, message, severity) => {
        setEvent({
            title: title,
            message: message,
            severity: severity
        })
    }


    // payload construction
    const [labelSource, setLabelSource] = useState(undefined);
    const [conversionConfig, setConversionConfig] = useState(undefined);
    const [payload, setPayload] = useState(undefined);

    useEffect(() => {
        if (labelSource === undefined || conversionConfig === undefined) {
            setPayload(undefined)
            return;
        }

        const payload = {};

        // define source
        if (labelSource === undefined) {
            payload.dataBase64 = undefined;
            payload.url = undefined;
        } else {
            payload.dataBase64 = labelSource.dataBase64;
            payload.url = labelSource.url;
        }

        // assign config
        Object.assign(payload, conversionConfig)

        // update state
        setPayload(payload);
    }, [labelSource, conversionConfig])


    // construct curl request
    const [cmdCurl, setCmdCurl] = useState("");

    useEffect(() => {
        if (payload === undefined) {
            setCmdCurl(undefined);
            return;
        }


        const baseUrl = `${window.location.protocol}//${window.location.hostname}${window.location.port ? ':' + window.location.port : ''}`;

        const cmdLines = [
            `curl --request POST`,
            `     --url ${baseUrl}/v1/TBD`,
            `     --header 'content-type: application/json'`,
            `     --data '${JSON.stringify(payload)}'`
        ]

        setCmdCurl(cmdLines.join(" \\ \n"));
    }, [payload])


    // zpl label generation
    const [zplGenerationActive, setZplGenerationActive] = useState(false);
    const [zplData, setZplData] = useState("")

    const generateZplLabel = () => {

        setZplGenerationActive(true);

        fetch('/v1/html2zpl', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(zplData => {
                setZplData(zplData);
                showEvent("ZPL Generation Successful.", "Your ZPL label has been generated successfully.", "success")
            })
            .catch(error => {
                console.error('Error:', error);
                showEvent("Error ZPL Generation.", error.message, "danger")
            })
            .finally(() => setZplGenerationActive(false));
    }

    const copyZplToClipboard = () => {
        navigator.clipboard.writeText(zplData)
            .then(() => showEvent("Copy to clipboard successful", "Your ZPL label has been copied successfully to the clipboard.", "success"))
            .catch(() => showEvent("Error copy to clipboard", "Error copying to clipboard.", "danger"));
    }

    const saveZplAsFile = () => {
        var blob = new Blob([zplData], { type: "text/plain;charset=utf-8" });
        saveAs(blob, "hello world.zpl.txt");
    }


    // return final output
    return (
        <div className="container mt-4">

            {/* label source */}
            <div className="row mb-2">
                <div className="col">
                    <h2>1. Reference or upload your label</h2>
                </div>
            </div>

            <LabelSource onLabelUpdate={setLabelSource} />

            {/* label source */}
            <div className="row mt-5">
                <div className="col">
                    <h2>2. Configure ZPL conversion</h2>
                </div>
            </div>

            <ConversionConfig onConfigUpdate={setConversionConfig} />

            <div className="row mt-3">
                <div className="col-6">
                    <small>JSON Payload:</small>
                    <textarea className="form-control form-control-sm font-monospace bg-body-tertiary" rows={5} value={payload === undefined ? "" : JSON.stringify(payload, null, 2)} readOnly={true} style={{ resize: 'none' }} />
                </div>
                <div className="col-6">
                    <small>curl command:</small>
                    <textarea className="form-control form-control-sm font-monospace bg-body-tertiary" rows={5} value={cmdCurl === undefined ? "" : cmdCurl} readOnly={true} style={{ resize: 'none' }} />
                </div>
            </div>

            {/* generate label */}
            <div className="row mt-5">
                <div className="col-6">
                    <h2>3. Generate ZPL Label</h2>
                </div>
                <div className="col-6 text-end">
                    <button type="button" className="btn btn-outline-primary" onClick={generateZplLabel} disabled={zplGenerationActive}>
                        <div className={`spinner-border spinner-border-sm me-2 ${zplGenerationActive === false && 'd-none'}`} role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                        Create ZPL Label
                    </button>
                </div>
            </div>
            <div className="row">
                <div className="col-12">
                    <small>ZPL data:</small>
                    <textarea className="form-control form-control-sm font-monospace bg-body-tertiary" rows={5} value={zplData === undefined ? "" : zplData} readOnly={true} style={{ resize: 'none' }} />
                </div>
            </div>
            <div className="row mt-2">
                <div className="col-6">{zplData && Dimensions.bytes2readable(new Blob([zplData]).size)}</div>
                <div className="col-6 text-end">
                    <button type="button" className="btn btn-outline-secondary btn-sm me-3" onClick={copyZplToClipboard}><i className="bi bi-copy me-2"></i>Copy to Clipboard</button>
                    <button type="button" className="btn btn-outline-secondary btn-sm" onClick={saveZplAsFile}><i className="bi bi-cloud-download me-2"></i>Download</button>
                </div>
            </div>

            {/* toasts */}
            <Toast event={event} onResetEvent={() => setEvent(undefined)} />
        </div>
    );
}

export default Main;