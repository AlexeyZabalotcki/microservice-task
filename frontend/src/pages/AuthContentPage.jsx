import * as React from 'react';

import { request, setAuthHeader } from '../helpers/axios_helper';

export default class AuthContentPage extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      data: []
    }
  };

  componentDidMount() {
    const today = new Date().toISOString().slice(0, 10);
    request(
      "GET",
      `/client/date/${today}`,
      {}).then(
        (response) => {
          this.setState({ data: response.data })
        }).catch(
          (error) => {
            if (error.response.status === 401) {
              setAuthHeader(null);
            } else {
              this.setState({ data: error.response.code })
            }

          }
        );
  };

  logout = () => {
    window.location.href = `/`;
    setAuthHeader(null);
  };

  render() {
    return (
      <>
        <div className="row justify-content-md-center">
          <div>
            <button className="btn btn-dark" style={{ margin: '10px' }} onClick={this.logout}>
              Logout
            </button>
          </div>
          <div className="col-6">
            <div className="card" style={{ width: "54rem" }}>
              <div className="card-body">
                <h5 className="card-title">Latest exchange rates</h5>
                <p className="card-text">Rates:</p>
                <div className="table-responsive" style={{ maxHeight: "300px", overflowY: "scroll" }}>
                  <table className="table table-striped">
                    <thead className="thead-dark">
                      <tr>
                        <th>From</th>
                        <th>To</th>
                        <th>Amount</th>
                        <th>Rate</th>
                        <th>Date</th>
                        <th>Result</th>
                      </tr>
                    </thead>
                    <tbody>
                      {this.state.data &&
                        this.state.data.map((dto, index) => (
                          <tr key={index}>
                            <td>{dto.from}</td>
                            <td>{dto.to}</td>
                            <td>{dto.amount}</td>
                            <td>{dto.rate}</td>
                            <td>{dto.date}</td>
                            <td>{dto.result}</td>
                          </tr>
                        ))}
                    </tbody>
                  </table>
                </div>
                <div className="text-center">
                  <button
                    className="btn btn-primary mb-3"
                    onClick={this.goToNewPage}
                  >
                    Create Request
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }


  goToNewPage = () => {
    window.location.href = "/new";
  };
}