import ajax from './Repository'

const resource = '/statistics';
export default {
    getStatistics() {
        return ajax.get(`${resource}/dashboard`);
    },
    countAll() {
        return ajax.get(`${resource}/count`);
    },
    getUsages(id) {
        return ajax.get(`${resource}/${id}/usages`);
    },
    getSummaries(id) {
        return ajax.get(`${resource}/${id}/summaries`);
    }
}
