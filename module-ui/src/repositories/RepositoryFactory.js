import SecurityRepository from './SecurityRepository'
import ProjectRepository from './ProjectRepository'

const repositories = {
    security: SecurityRepository,
    projects: ProjectRepository,
    // other repositories
};

export const RepositoryFactory = {
    get: name => repositories[name]
};
