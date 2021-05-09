import Repository from './Repository'

const resource = '/project';
export default {
    get(page = 0, size = 10) {
        return Repository.get(`${resource}?page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    autocomplete(name) {
        return Repository.get(`${resource}/autocomplete?name=${name}`);
    },
    getProject(id) {
        return Repository.get(`${resource}/${id}`);
    },
    createProject(playload) {
        return Repository.post(`${resource}`, playload);
    },
    updateProject(id, playload) {
        return Repository.put(`${resource}/${id}`, playload);
    },
    deleteProject(id) {
        return Repository.delete(`${resource}/${id}`);
    },
    countProjects() {
        return Repository.get(`${resource}/total`);
    }
}
