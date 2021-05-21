import axiosInstance from './Repository'

const resource = '/berock';
export default {
    get(status, page = 0, size = 10) {
        return axiosInstance.get(`${resource}?status=${status}&page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    getBerock(id) {
        return axiosInstance.get(`${resource}/${id}`);
    },
    saveDraft(playload) {
        return axiosInstance.post(`${resource}/draft`, playload);
    },
    calculate(playload) {
        return axiosInstance.post(`${resource}/calculate`, playload);
    },
    deleteLiquec(id) {
        return axiosInstance.delete(`${resource}/${id}`);
    },
    clone(id) {
        return axiosInstance.post(`${resource}/${id}/clone`);
    }
}
