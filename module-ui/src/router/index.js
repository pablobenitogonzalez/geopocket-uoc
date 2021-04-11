import Vue from 'vue'
import Router from 'vue-router'

// Containers
const TheContainer = () => import('@/containers/TheContainer')

// Views
const Dashboard = () => import('@/views/Dashboard')
const ManageProjects = () => import('@/views/project/ManageProjects')
const CreateNewProject = () => import('@/views/project/CreateNewProject')
const ManageLiquecCalculations = () => import('@/views/liquec/ManageLiquecCalculations')
const ManageLiquecDrafts = () => import('@/views/liquec/ManageLiquecDrafts')
const CreateNewLiquec = () => import('@/views/liquec/CreateNewLiquec')

Vue.use(Router)

export default new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  linkActiveClass: 'active',
  scrollBehavior: () => ({ y: 0 }),
  routes: configRoutes()
})

function configRoutes () {
  return [
    {
      path: '/',
      redirect: '/dashboard',
      name: 'Home',
      component: TheContainer,
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: Dashboard
        },
        {
          path: 'project',
          redirect: '/project/manage',
          name: 'Project',
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'manage',
              name: 'Manage Projects',
              component: ManageProjects
            },
            {
              path: 'create',
              name: 'Create New Project',
              component: CreateNewProject
            }
          ]
        },
        {
          path: 'liquec',
          redirect: '/liquec/calculations',
          name: 'Liquec',
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'calculations',
              name: 'Manage Liquec Calculations',
              component: ManageLiquecCalculations
            },
            {
              path: 'drafts',
              name: 'Manage Liquec Drafts',
              component: ManageLiquecDrafts
            },
            {
              path: 'create',
              name: 'Create New Liquec',
              component: CreateNewLiquec
            }
          ]
        }
      ]
    }
  ]
}

