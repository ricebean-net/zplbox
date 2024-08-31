import logo from './../assets/zplbox-logo-h.png';

function Header() {

    // return final outpout
    return (
        <div>
            <div className='border-bottom pb-3 px-2 pt-1 shadow-sm'>
                <img src={logo} alt="ZplBox Logo" className="d-inline-block align-text-top" style={{ height: 50 }} />
            </div>
        </div>
    )

}

export default Header;