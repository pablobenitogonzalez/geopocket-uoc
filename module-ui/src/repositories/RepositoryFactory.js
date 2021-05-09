import SecurityRepository from './SecurityRepository'
import ProjectRepository from './ProjectRepository'
import LiquecRepository from "./LiquecRepository";
import StatisticsRepository from "./StatisticsRepository";

const repositories = {
    security: SecurityRepository,
    projects: ProjectRepository,
    liquec: LiquecRepository,
    statistics: StatisticsRepository
    // other repositories
};

export const RepositoryFactory = {
    get: name => repositories[name]
};
