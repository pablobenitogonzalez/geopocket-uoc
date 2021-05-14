import axios from 'axios'

const baseURL = 'http://localhost:8080';

const ajax = axios.create({
    baseURL: baseURL
});

ajax.interceptors.request.use(
    (config) => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user && user.authdata) {
            config.headers['Authorization'] = `Basic ${user.authdata}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default ajax