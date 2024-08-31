import { useEffect, useState } from "react";

function ConversionConfig({onConfigUpdate}) {

    // switch pdf/html
    const [type, setType] = useState("WEB")


    // pdf config
    const [widthInches, setWidthInches] = useState(undefined);
    const [heightInches, setHeightInches] = useState(undefined);
    const [resolutionDpi, setResolutionDpi] = useState(undefined);

    const [widthPts, setWidthPts] = useState(undefined);
    const [heightPts, setHeightPts] = useState(undefined);

    const resetPdfConfig = () => {
        setWidthInches(4);
        setHeightInches(6);
        setResolutionDpi(203);
    }

    const clearPdfConfig = () => {
        setWidthInches(undefined);
        setHeightInches(undefined);
        setResolutionDpi(undefined);
    }

    useEffect(() => {
        resetPdfConfig();
    }, [type]);

    useEffect(() => {
        if(widthInches === undefined || heightInches === undefined || resolutionDpi === undefined) {
            return;
        }

        setWidthPts(widthInches * resolutionDpi);
        setHeightPts(heightInches * resolutionDpi);

    }, [widthInches, heightInches, resolutionDpi]);

    useEffect(() => {
        if(widthPts === '' || heightPts === '') {
            onConfigUpdate({widthPts: undefined, heightPts: undefined});
            return;
        }
        onConfigUpdate({widthPts: widthPts, heightPts: heightPts});
    }, [widthPts, heightPts])


    // return final output
    return (
        <div>
            <div className="row">
                <div className="col-2">
                    Configuration:
                </div>
                <div className="col-5">
                    <div className="form-check form-check-inline me-4">
                        <input className="form-check-input" type="radio" checked={type === "WEB"} onChange={(e) => setType("WEB")} />
                        <label className="form-check-label">Web Content (HTML, JavaScript, CSS)</label>
                    </div>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" checked={type === "PDF"} onChange={(e) => setType("PDF")} />
                        <label className="form-check-label">PDF Document</label>
                    </div>
                </div>
                <div className="col-5">

                </div>
            </div>

            {type === "WEB" &&
                <div>
                    <div className="row mt-3">
                        <div className="col-2"></div>
                        <div className="col-5">
                            <div className="row">
                                <div className="col-3">
                                    <small className={widthInches === undefined ? 'text-secondary fst-italic' : ''}>Width (inches):</small>
                                    <input type="number" className="form-control" value={widthInches ? widthInches : ""} onChange={(e) => setWidthInches(e.target.value)} disabled={widthInches === undefined} />
                                </div>
                                <div className="col-3">
                                    <small className={heightInches === undefined ? 'text-secondary fst-italic' : ''}>Height (inches):</small>
                                    <input type="number" className="form-control" value={heightInches ? heightInches : ""} onChange={(e) => setHeightInches(e.target.value)} disabled={heightInches === undefined} />
                                </div>
                                <div className="col-3">
                                    <small className={resolutionDpi === undefined ? 'text-secondary fst-italic' : ''}>Resolution (dpi):</small>
                                    <input type="number" className="form-control" value={resolutionDpi ? resolutionDpi : ""} onChange={(e) => setResolutionDpi(e.target.value)} disabled={resolutionDpi === undefined} />
                                </div>
                                <div className="col-3 d-flex">
                                    <button type="button" className="btn btn-link btn-sm align-self-end p-2" onClick={resetPdfConfig}>reset sizes</button>
                                </div>
                            </div>
                        </div>
                        <div className="col-5"></div>
                    </div>

                    <div className="row mt-3">
                        <div className="col-2"></div>
                        <div className="col-5">
                            <div className="row">
                                <div className="col-3">
                                    <small>Width (points):</small>
                                    <input type="number" className="form-control" value={widthPts ? widthPts : ""} onChange={(e) => {setWidthPts(e.target.value === '' ? undefined : Number(e.target.value)); clearPdfConfig()}} />
                                </div>
                                <div className="col-3">
                                    <small>Height (points):</small>
                                    <input type="number" className="form-control" value={heightPts ? heightPts : ""} onChange={(e) => {setHeightPts(e.target.value === '' ? undefined : Number(e.target.value)); clearPdfConfig()}} />
                                </div>
                                <div className="col-6">

                                </div>
                            </div>
                        </div>
                        <div className="col-5"></div>
                    </div>
                </div>
            }
        </div>
    )
}

export default ConversionConfig;