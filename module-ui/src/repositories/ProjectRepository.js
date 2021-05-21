import axiosInstance from './Repository'

const resource = '/project';
export default {
    get(page = 0, size = 10) {
        return axiosInstance.get(`${resource}?page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    autocomplete(name) {
        return axiosInstance.get(`${resource}/autocomplete?name=${name}`);
    },
    getProject(id) {
        return axiosInstance.get(`${resource}/${id}`);
    },
    createProject(playload) {
        return axiosInstance.post(`${resource}`, playload);
    },
    updateProject(id, playload) {
        return axiosInstance.put(`${resource}/${id}`, playload);
    },
    deleteProject(id) {
        return axiosInstance.delete(`${resource}/${id}`);
    },
    countProjects() {
        return axiosInstance.get(`${resource}/total`);
    }
}
