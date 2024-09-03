import Footer from "./common/Footer";
import Header from "./common/Header";
import Main from "./main/Main";

function App() {

    // final output
    return (
        <div className="d-flex flex-column vh-100 bg-100">
            <div className="">
                <Header />
            </div>
            <div className="flex-fill position-relative">
                <div className="position-absolute overflow-auto" style={{top: 10, left: 0, right: 0, bottom: 10}}>
                    <Main />
                </div>
            </div>
            <div>
                <Footer />
            </div>
        </div>

    );
}

export default App;
