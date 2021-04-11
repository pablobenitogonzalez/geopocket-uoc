import ProjectRepository from './ProjectRepository'

const repositories = {
    projects: ProjectRepository,
    // other repositories
};

export const RepositoryFactory = {
    get: name => repositories[name]
};
