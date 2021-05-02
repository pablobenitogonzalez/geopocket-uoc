import SecurityRepository from './SecurityRepository'
import ProjectRepository from './ProjectRepository'
import LiquecRepository from "./LiquecRepository";

const repositories = {
    security: SecurityRepository,
    projects: ProjectRepository,
    liquec: LiquecRepository,
    // other repositories
};

export const RepositoryFactory = {
    get: name => repositories[name]
};
