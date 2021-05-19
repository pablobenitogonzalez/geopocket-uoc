import ajax from './Repository'

const resource = '/berock';
export default {
    get(status, page = 0, size = 10) {
        return ajax.get(`${resource}?status=${status}&page=${page}&size=${size}&sort=audit.updatedOn,desc`);
    },
    getBerock(id) {
        return ajax.get(`${resource}/${id}`);
    },
    saveDraft(playload) {
        return ajax.post(`${resource}/draft`, playload);
    },
    calculate(playload) {
        return ajax.post(`${resource}/calculate`, playload);
    },
    deleteLiquec(id) {
        return ajax.delete(`${resource}/${id}`);
    },
    clone(id) {
        return ajax.post(`${resource}/${id}/clone`);
    }
}
