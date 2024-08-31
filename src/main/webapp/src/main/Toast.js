import Bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min.js';

const { useEffect, useRef, useState } = require("react")

function Toast({ event, onResetEvent }) {

    // init toast
    const REF_TOAST = useRef(undefined);
    const [toastInstance, setToastInstance] = useState(undefined);

    useEffect(() => {
        const toastInstance = REF_TOAST.current ? Bootstrap.Toast.getOrCreateInstance(REF_TOAST.current) : undefined;
        setToastInstance(toastInstance);

        return () => {
            toastInstance?.dispose();
        }
    }, [])


    // show event
    useEffect(() => {
        if (toastInstance === undefined || toastInstance['_config'] === null) {
            return;
        }

        if (event === undefined) {
            toastInstance?.hide();
        } else {
            toastInstance?.show();
        }

    }, [toastInstance, event])


    // return final toast output
    return (
        <div className="toast-container position-fixed top-0 end-0 px-2">
            <div className={`toast bg-${event?.severity}-subtle border border-${event?.severity} m-3`} role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="5000" data-bs-autohide="true" ref={REF_TOAST}>
                <div className={`toast-header bg-${event?.severity}-subtle`}>

                    <strong className="me-auto">{event?.title}</strong>

                    <button type="button" className="btn-close" data-bs-dismiss="toast" aria-label="Close" onClick={onResetEvent}></button>
                </div>
                <div className="toast-body">
                    {event?.message}
                </div>
            </div>
        </div>
    )
}

export default Toast; 
