import axios from 'axios'

const baseURL = 'http://localhost:8080';

export default axios.create({
    baseURL,
    withCredentials: true,
    headers: authHeader()
})

export function authHeader() {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user'));
    if (user && user.authdata) {
        return {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + user.authdata};
    } else {
        return {};
    }
}
