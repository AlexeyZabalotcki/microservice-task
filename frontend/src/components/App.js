import logo from '../logo.svg';
import './App.css';

import Header from './Header';
import AppContentPage from '../pages/AppContentPage';


function App() {
  return (
    <div className="App">
      {/* <Header pageTitle="Currency exchanger" logoSrc={logo} /> */}
      <div className="container-fluid">
        <div className="row">
          <div className="col">
            <AppContentPage />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;