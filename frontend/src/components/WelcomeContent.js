import * as React from 'react';
import { request, setAuthHeader } from '../helpers/axios_helper';

import LoginForm from '../components/LoginForm';
import AuthContentPage from '../pages/AuthContentPage';
import Header from '../components/Header';
import logo from '../logo.svg';

export default class WelcomeContent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      componentToShow: "welcome",
    }
  };

  login = () => {
    this.setState({
      componentToShow: "login",
    });
    document.getElementById('login').style.display = 'none';
    document.getElementById('welcome').style.display = 'none';
  };

  logout = () => {
    this.setState({
      componentToShow: "login",
    });
    setAuthHeader(null);
    document.getElementById('login').style.display = 'none';
    document.getElementById('welcome').style.display = 'none';
  };


  onLogin = (e, email, password) => {
    e.preventDefault();
    fetch("http://localhost:8000/users/authenticate", {
      method: "POST",
      body: JSON.stringify({
        email: email,
        password: password
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    }).then(response => {
      if (response.ok) {

        response.json().then(data => {
          const token = data['accessToken'];
          setAuthHeader(token);
          window.sessionStorage.setItem('auth_token', token);
          console.log(token);
          this.setState({ componentToShow: "messages" })
        });
      }
    });
  };

  onRegister = (event, email, password) => {
    event.preventDefault();

    fetch("http://localhost:8000/users/sign-in", {
      method: "POST",
      body: JSON.stringify({
        email: email,
        password: password
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    }).then(response => {
      if (response.ok) {
        response.json().then(data => setAuthHeader(data['accessToken']));
        const token = window.sessionStorage.getItem('auth_token');
        console.log(token);
        this.setState({ componentToShow: "messages" });
      }
    });
  };

  goToNewPage = () => {
    window.location.href = "/currency";
};

  render() {
    return (
      <>
      <Header pageTitle="Currency exchanger" logoSrc={logo} />
        <div  id='login' className="row justify-content-center align-items-center" style={{ height: "35vh" }}>
          <div className="row">
          <div className="col-md-12 text-center" style={{ marginTop: '30px' }}>
            <button id="aaa" className="btn btn-primary" style={{ margin: '10px' }} onClick={this.login}>
              Login
            </button>
          </div>
        </div>
          <div id='welcome' className="col-md-6">
            <div className="jumbotron">
              <div className="container">
                <h1 className="display-4 text-center">Welcome</h1>
                <p className="lead text-center">Login to work with currency.</p>
              </div>
            </div>
          </div>
        </div>
        {this.state.componentToShow === "login" && <LoginForm onLogin={this.onLogin} onRegister={this.onRegister} />}
        {this.state.componentToShow === "messages" && <AuthContentPage goToNewPage={this.goToNewPage} />}
      </>
    );
  }
}