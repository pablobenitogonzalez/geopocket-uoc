import ajax from './Repository'

const resource = '/project';
export default {
    get(page = 0, size = 10) {
        return ajax.get(`${resource}?page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    autocomplete(name) {
        return ajax.get(`${resource}/autocomplete?name=${name}`);
    },
    getProject(id) {
        return ajax.get(`${resource}/${id}`);
    },
    createProject(playload) {
        return ajax.post(`${resource}`, playload);
    },
    updateProject(id, playload) {
        return ajax.put(`${resource}/${id}`, playload);
    },
    deleteProject(id) {
        return ajax.delete(`${resource}/${id}`);
    },
    countProjects() {
        return ajax.get(`${resource}/total`);
    }
}
