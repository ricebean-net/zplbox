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
            <div className="flex-fill">
                <Main />
            </div>
            <div>
                <Footer />
            </div>
        </div>

    );
}

export default App;
