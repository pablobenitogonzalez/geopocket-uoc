import axiosInstance from './Repository'

const resource = '/statistics';
export default {
    getStatistics() {
        return axiosInstance.get(`${resource}/dashboard`);
    },
    countAll() {
        return axiosInstance.get(`${resource}/count`);
    },
    getUsages(id) {
        return axiosInstance.get(`${resource}/${id}/usages`);
    },
    getSummaries(id) {
        return axiosInstance.get(`${resource}/${id}/summaries`);
    }
}
