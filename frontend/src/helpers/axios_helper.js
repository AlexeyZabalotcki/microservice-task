import axios from 'axios';


export const getAuthToken = () => {
    return window.sessionStorage.getItem('auth_token');
};

export const setAuthHeader = (token) => {
    window.sessionStorage.setItem('auth_token', token);
};

axios.defaults.baseURL = 'http://localhost:8000';
axios.defaults.headers.post['Content-Type'] = 'application/json';

export const request = (method, url, data) => {

    let headers = {};
    if (getAuthToken() !== null && getAuthToken() !== "null") {
        headers = {'Authorization': `Bearer ${getAuthToken()}`};
    }

    return axios({
        method: method,
        url: url,
        headers: headers,
        data: data});
};