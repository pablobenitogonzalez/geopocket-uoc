import axios from 'axios'
import Repository from './Repository'

const resource = '/security';
export default {
    login(username, password) {
        return Repository.post(`${resource}/login`, {}, {
            withCredentials: true,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + window.btoa(username + ':' + password)
            }
        }).then(user => {
            // login successful if there's a user in the response
            if(user) {
                // store user details and basic auth credentials in local storage
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(username + ':' + password);
                localStorage.setItem('user', JSON.stringify(user));
                axios.defaults.headers['Authorization'] = 'Basic ' + user.authdata
            }
            return user;
        });
    },
    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('user');
        axios.defaults.headers['Authorization'] = '';
    }
}
