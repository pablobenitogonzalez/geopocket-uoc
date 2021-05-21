import axiosInstance from './Repository'

const resource = '/security';
export default {
    login(username, password) {
        return axiosInstance.post(`${resource}/login`, {}, {
            withCredentials: true,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + window.btoa(`${username}:${password}`)
            }
        }).then(user => {
            // login successful if there's a user in the response
            if(user) {
                // store user details and basic auth credentials in local storage
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(`${username}:${password}`);
                localStorage.setItem('user', JSON.stringify(user));
            }
            return user;
        });
    },
    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('user');
    },
    getUserName() {
        const user = JSON.parse(localStorage.getItem('user'));
        return user.data.name;
    }
}
