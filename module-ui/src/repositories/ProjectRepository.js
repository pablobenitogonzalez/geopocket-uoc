import Repository from './Repository'

const resource = '/project';
export default {
    get(page = 0, size = 10) {
        return Repository.get(`${resource}?page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    getProject(id) {
        return Repository.get(`${resource}/${id}`);
    },
    createProject (playload) {
        return Repository.post(`${resource}`, playload);
    },
    updateProject (id, playload) {
        return Repository.post(`${resource}/${id}`, playload);
    },
    deleteProject (id) {
        return Repository.delete(`${resource}/${id}`);
    }
}
