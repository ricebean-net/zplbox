import logo from './../assets/zplbox-logo-h.png';
import bmc from './../assets/bmc.svg';

function Header() {

    // return final outpout
    return (
        <div className='container-fluid border-bottom shadow-sm pb-2 d-flex align-items-end'>

            <div className='me-auto'>
                <img src={logo} alt="ZplBox Logo" className="d-inline-block align-text-top" style={{ height: 50 }} />
            </div>

            <div>
                <div class="btn-group pe-4">
                    <button type="button" class="btn dropdown-toggle py-0" data-bs-toggle="dropdown" aria-expanded="false">
                        Documentation
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item py-2" href="https://zplbox.org/#api-reference" target='_blank'>API Reference</a></li>
                        <li><a class="dropdown-item py-2" href="https://github.com/meiXXI/zplbox" target='_blank'>Technical Documentation</a></li>
                    </ul>
                </div>
                <div class="btn-group pe-5">
                    <button type="button" class="btn dropdown-toggle py-0" data-bs-toggle="dropdown" aria-expanded="false">
                        About
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item py-2" href="https://zplbox.org/#api-reference" target='_blank'><i class="bi bi-github me-2"></i>ZplBox on GitHub</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="https://buymeacoffee.com/meixxi" target='_blank'><img src={bmc} style={{width: 100}} /></a></li>
                    </ul>
                </div>
            </div>


        </div>
    )

}

export default Header;