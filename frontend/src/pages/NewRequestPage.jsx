import React, { Component } from "react";
import Header from '../components/Header';
import logo from '../logo.svg';
import { request, setAuthHeader } from '../helpers/axios_helper';

export default class NewRequestPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            from: "",
            to: "",
            amount: 0,
            subscribe: false,
            result: null
        };
    }

    handleInputChange = (event) => {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });
    };

    handleBack = () => {
        const today = new Date().toISOString().slice(0, 10);
        window.location.href = `/currency`;
    };

    sendData = () => {
        const from = document.getElementById("from").value;
        const to = document.getElementById("to").value;
        const amount = document.getElementById("amount").value;
        const subscribe = document.querySelector("#subscribe").checked;

        fetch(`http://localhost:8000/client/new`, {
            method: "POST",
            body: JSON.stringify({
                from: from,
                to: to,
                amount: amount,
                subscribe: subscribe
            }),

            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": `Bearer ${window.sessionStorage.getItem('auth_token')}`
            }
        }).then(response => {
            if (response.ok) {
                response.json().then(responseJson => {
                    this.setState({
                        result: responseJson
                    })
                });
            }
        });
    }

    logout = () => {
        window.location.href = `/`;
        setAuthHeader(null);
    };

    render() {
        return (
            <>
            <Header pageTitle="Currency exchanger" logoSrc={logo}/>
                <div className="row justify-content-center">
                    <div>
                        <button className="btn btn-dark" style={{ margin: '10px' }} onClick={this.logout}>
                            Logout
                        </button>
                    </div>
                    <div className="col-sm-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">New Request</h5>
                                <div className="form-group mb-2">
                                    <label htmlFor="from">From:</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="from"
                                        name="from"
                                        value={this.state.from}
                                        onChange={this.handleInputChange}
                                    />
                                </div>
                                <div className="form-group mb-2">
                                    <label htmlFor="to">To:</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        id="to"
                                        name="to"
                                        value={this.state.to}
                                        onChange={this.handleInputChange}
                                    />
                                </div>
                                <div className="form-group mb-2">
                                    <label htmlFor="amount">Amount:</label>
                                    <input
                                        type="number"
                                        className="form-control form-control-sm"
                                        id="amount"
                                        name="amount"
                                        value={this.state.amount}
                                        onChange={this.handleInputChange}
                                    />
                                </div>
                                <div className="form-check mb-2">
                                    <input
                                        type="checkbox"
                                        className="form-check-input"
                                        id="subscribe"
                                        name="subscribe"
                                        checked={this.state.subscribe}
                                        onChange={this.handleInputChange}
                                    />
                                    <label className="form-check-label" htmlFor="subscribe">
                                        Subscribe
                                    </label>
                                </div>
                                <button type="submit" className="btn btn-primary btn-sm" onClick={this.sendData}>
                                    Submit
                                </button>
                                {this.state.result && (
                                    <div className="mt-3">
                                        <h5>Result:</h5>
                                        <table className="table table-bordered">
                                            <tbody>
                                                <tr>
                                                    <td>From:</td>
                                                    <td>{this.state.result.from}</td>
                                                </tr>
                                                <tr>
                                                    <td>To:</td>
                                                    <td>{this.state.result.to}</td>
                                                </tr>
                                                <tr>
                                                    <td>Amount:</td>
                                                    <td>{this.state.result.amount}</td>
                                                </tr>
                                                <tr>
                                                    <td>Rate:</td>
                                                    <td>{this.state.result.rate}</td>
                                                </tr>
                                                <tr>
                                                    <td>Date:</td>
                                                    <td>{this.state.result.date}</td>
                                                </tr>
                                                <tr>
                                                    <td>Result:</td>
                                                    <td>{this.state.result.result}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                )}
                                <div>
                                    <button className="btn btn-secondary btn-sm mt-3" onClick={this.handleBack}>
                                        Back
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}
