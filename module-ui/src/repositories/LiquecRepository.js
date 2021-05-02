import Repository from './Repository'

const resource = '/liquec';
export default {
    get(status, page = 0, size = 10) {
        return Repository.get(`${resource}?status=${status}&page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    getLiquec(id) {
        return Repository.get(`${resource}/${id}`);
    },
    saveDraft(playload) {
        return Repository.post(`${resource}/draft`, playload);
    },
    calculate(playload) {
        return Repository.post(`${resource}/calculate`, playload);
    },
    deleteLiquec(id) {
        return Repository.delete(`${resource}/${id}`);
    },
    clone(id) {
        return Repository.post(`${resource}/${id}/clone`);
    }
}
