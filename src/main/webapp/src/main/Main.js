import { useEffect, useState } from "react";
import Toast from "./Toast";
import ZplSource from "./modules/ZplSource";
import ZplConversionConfig from "./modules/ZplConversionConfig";
import ZplGeneration from "./modules/ZplGeneration";
import ZplOutput from "./modules/ZplOuput";


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
        setZplData(undefined);

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


    // endpoint handling
    const [endpointBase, setEndpointBase] = useState(undefined);

    const updateEndpointBase = (endpointBase) => {
        if (labelSource === undefined) {
            return;
        }

        setEndpointBase(endpointBase);
    }

    useEffect(() => {
        if (labelSource === undefined) {
            setEndpointBase(undefined);
            return;
        }

        if (labelSource.mimeType === "application/pdf") {
            setEndpointBase("pdf2zpl");
        } else {
            setEndpointBase("html2zpl")
        }

    }, [labelSource])


    // label printer handling
    const [tcpAddress, setTcpAddress] = useState("127.0.0.1:9100");
    const [isTcpForward, setTcpForward] = useState(false);

    // endpoint definition
    const [endpoint, setEndpoint] = useState(undefined);

    useEffect(() => {
        if(endpointBase === undefined) {
            setEndpoint(undefined)
            return;
        }

        if(isTcpForward === true && tcpAddress !== undefined) {
            setEndpoint(`${endpointBase}/print/${tcpAddress}`)
        } else {
            setEndpoint(endpointBase)
        }

    }, [endpointBase, isTcpForward, tcpAddress])


    // zpl label generation
    const [zplGenerationActive, setZplGenerationActive] = useState(false);
    const [zplData, setZplData] = useState("")

    const generateZplLabel = () => {
        if (endpoint === undefined) {
            return;
        }

        setZplGenerationActive(true);

        fetch('/v1/' + endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.status + " " + response.statusText);
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


    // return final output
    return (
        <div className="container mt-4">

            {/* label source */}
            <div className="row mb-2">
                <div className="col">
                    <h2>1. Reference or upload your label</h2>
                </div>
            </div>

            <ZplSource onLabelUpdate={setLabelSource} />


            {/* configuration config */}
            <div className="row mt-5">
                <div className="col">
                    <h2>2. Configure ZPL Conversion</h2>
                </div>
            </div>

            <ZplConversionConfig endpointBase={endpointBase} payload={payload} onEndpointBaseUpdate={updateEndpointBase} onConfigUpdate={setConversionConfig} />


            {/* create zpl (& forward) */}
            <div className="row mt-5">
                <div className="col-12">
                    <h2>3. Generate ZPL Label</h2>
                </div>
            </div>

            <ZplGeneration endpoint={endpoint} payload={payload} tcpAddress={tcpAddress} isTcpForward={isTcpForward} zplGenerationActive={zplGenerationActive}
                onGenerateLabel={generateZplLabel} onTcpAddressUpdate={setTcpAddress} onTcpForwardUpdate={setTcpForward} />


            {/* output */}
            <ZplOutput zplData={zplData} tcpAddress={tcpAddress} isTcpForward={isTcpForward} onEvent={showEvent} onTcpAddressUpdate={setTcpAddress} />


            {/* toasts */}
            <Toast event={event} onResetEvent={() => setEvent(undefined)} />
        </div>
    );
}

export default Main;