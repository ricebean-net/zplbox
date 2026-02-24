import { useEffect, useState } from "react";
import Dimensions from './../../common/util/Dimensions';

function ZplSource({ onLabelUpdate }) {


    // reference handling
    const [refLabel, setRefLabel] = useState("");

    const urlTestLabels = [
        { descriptiveName: "UPS Example (4 x 8 inches)", url: "https://zplbox.org/test-labels/ups-example.html" },
        { descriptiveName: "Generic Example (4 x 6 inches)", url: "https://zplbox.org/test-labels/test-label-2.pdf" }
    ]

    const [refLabelValidation, setRefLabelValidation] = useState(undefined);

    useEffect(() => {
        if (refLabel === "") {
            setRefLabelValidation(undefined);
            return;
        }

        fetch(refLabel, { method: 'HEAD' })
            .then(response => {
                setRefLabelValidation({ status: response.status, message: response.status + " " + response.statusText })

                if (response.status === 200) {
                    onLabelUpdate({
                        url: refLabel,
                        mimeType: response.headers.get('Content-Type')
                    });
                } else {
                    onLabelUpdate(undefined);
                }
            })
            .catch(error => {
                setRefLabelValidation({ status: "XXX", message: error.message });
                onLabelUpdate(undefined);
            });

    }, [refLabel])


    // file handling
    const [fileLabel, setFileLabel] = useState(undefined);

    useEffect(() => {
        if (fileLabel === undefined) {
            return;
        }

        // convert to base64
        const reader = new FileReader();
        reader.onloadend = () => {
            onLabelUpdate({
                dataBase64: reader.result.split(',')[1],
                mimeType: fileLabel.type
            });
        };
        reader.readAsDataURL(fileLabel);
    }, [fileLabel])


    // switch: ref / file
    const [isRefByUrl, setRefByUrl] = useState(true);

    useEffect(() => {

        // reset
        setRefLabel("");
        setFileLabel(undefined);

        onLabelUpdate(undefined);

    }, [isRefByUrl])


    // return final output
    return (
        <div>

            {/* url / upload */}
            <div className="row">
                <div className="col-2 d-flex">
                    <div className="align-self-center">
                        Label File (HTML / PDF):
                    </div>
                </div>
                <div className="col-5">

                    {/* ref by url */}
                    {isRefByUrl === true &&
                        <div className="input-group">
                            <input type="text" className="form-control" value={refLabel} onChange={(e) => setRefLabel(e.target.value)} />
                            <button type="button" className="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span className="visually-hidden">Toggle Dropdown</span>
                            </button>
                            <ul className="dropdown-menu dropdown-menu-end w-100">
                                {urlTestLabels.map((urlTestUrl, idx) =>
                                    <li key={'testLabel-' + idx}>
                                        <button type="button" className="dropdown-item py-2" onClick={() => setRefLabel(urlTestUrl.url)}>
                                            {urlTestUrl.descriptiveName}<br />
                                            <small>{urlTestUrl.url}</small>
                                        </button>

                                    </li>
                                )}

                            </ul>
                        </div>
                    }

                    {/* upload file */}
                    {isRefByUrl === false &&
                        <input className="form-control" type="file" accept="application/pdf, text/html" onChange={(e) => setFileLabel(e.target.files[0])} />
                    }
                </div>
                <div className="col-5 d-flex">

                    {/* ref by url */}
                    {isRefByUrl === true && refLabelValidation &&
                        <div className="align-self-center">
                            {
                                refLabelValidation.status === 200
                                    ? <div><small className="text-success me-4"><i className="bi bi-check-circle-fill me-2"></i>{refLabelValidation.message}</small> <small><i><a href={refLabel} target="_blank">[Open in new tab]</a></i></small></div>
                                    : <div><small className="text-danger"><i className="bi bi-x-circle-fill me-2"></i>{refLabelValidation.message}</small></div>
                            }
                        </div>
                    }

                    {/* upload file */}
                    {isRefByUrl === false && fileLabel &&
                        <div className="align-self-center">
                            {Dimensions.bytes2readable(fileLabel.size)}
                        </div>
                    }
                </div>
            </div>

            {/* switch: upload / ref */}
            <div className="row mt-2">
                <div className="col-2"></div>
                <div className="col-5">
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" checked={isRefByUrl} onChange={(e) => setRefByUrl(e.target.checked)} />
                        <label className="form-check-label">Reference by URL</label>
                    </div>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" checked={!isRefByUrl} onChange={(e) => setRefByUrl(!e.target.checked)} />
                        <label className="form-check-label">Upload File</label>
                    </div>
                </div>
                <div className="col-5"></div>
            </div>
        </div>
    );
}

export default ZplSource;