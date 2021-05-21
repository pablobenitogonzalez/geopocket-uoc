import axios from 'axios'

const baseURL = process.env.VUE_APP_API_BASE_URL;

const axiosInstance = axios.create({
    baseURL: baseURL
});

axiosInstance.interceptors.request.use(
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

export default axiosInstance