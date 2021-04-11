import Repository from './Repository'

const resource = '/project';
export default {
    get() {
        return Repository.get(`${resource}`);
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
