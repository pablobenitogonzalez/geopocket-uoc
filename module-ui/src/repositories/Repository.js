import axios from 'axios'

const baseURL = 'http://localhost:8080';

export default axios.create({
    baseURL
    // In case that you need a token:
    // headers: { 'Authorization': 'Bearer yourToken' }
})

