import Repository from './Repository'

const resource = '/statistics';
export default {
    countAll() {
        return Repository.get(`${resource}/count`);
    },
    getUsages(id) {
        return Repository.get(`${resource}/${id}`);
    }
}
