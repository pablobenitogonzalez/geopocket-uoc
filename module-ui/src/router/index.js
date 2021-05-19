import Vue from 'vue'
import Router from 'vue-router'

// Containers
const TheContainer = () => import('@/containers/TheContainer');

const Login = () => import('@/views/pages/Login');

// Views
const Dashboard = () => import('@/views/Dashboard');
const ManageProjects = () => import('@/views/project/ManageProjects');
const CreateNewProject = () => import('@/views/project/CreateNewProject');
const ProjectDetail = () => import('@/views/project/ProjectDetail');
const ManageBerockCalculations = () => import('@/views/berock/ManageBerockCalculations');
const ManageBerockDrafts = () => import('@/views/berock/ManageBerockDrafts');
const CreateNewBerock = () => import('@/views/berock/CreateNewBerock');
const BerockResult = () => import('@/views/berock/BerockResult');
const ManageLiquecCalculations = () => import('@/views/liquec/ManageLiquecCalculations');
const ManageLiquecDrafts = () => import('@/views/liquec/ManageLiquecDrafts');
const CreateNewLiquec = () => import('@/views/liquec/CreateNewLiquec');
const LiquecResult = () => import('@/views/liquec/LiquecResult');

Vue.use(Router);

const router = new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  linkActiveClass: 'active',
  scrollBehavior: () => ({ y: 0 }),
  routes: configRoutes()
});

function configRoutes () {
  return [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
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
            },
            {
              path: ':id',
              name: 'Project Detail',
              component: ProjectDetail
            }
          ]
        },
        {
          path: 'berock',
          redirect: '/berock/calculations',
          name: 'Berock',
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'calculations',
              name: 'Manage Berock Calculations',
              component: ManageBerockCalculations
            },
            {
              path: 'drafts',
              name: 'Manage Berock Drafts',
              component: ManageBerockDrafts
            },
            {
              path: 'create',
              redirect: '/liquec/create/new',
              name: 'Create',
              component: {
                render(c) {
                  return c('router-view')
                }
              },
              children: [
                {
                  path: 'new',
                  name: 'Create New Berock',
                  component: CreateNewBerock
                },
                {
                  path: 'draft/:id',
                  name: 'Create Berock From Draft',
                  component: CreateNewBerock
                }
              ]
            },
            {
              path: ':id',
              name: 'Berock Result',
              component: BerockResult
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
              redirect: '/liquec/create/new',
              name: 'Create',
              component: {
                render(c) {
                  return c('router-view')
                }
              },
              children: [
                {
                  path: 'new',
                  name: 'Create New Liquec',
                  component: CreateNewLiquec
                },
                {
                  path: 'draft/:id',
                  name: 'Create Liquec From Draft',
                  component: CreateNewLiquec
                }
              ]
            },
            {
              path: ':id',
              name: 'Liquec Result',
              component: LiquecResult
            }
          ]
        }
      ]
    },
    // otherwise redirect to home
    { path: '*', redirect: '/' }
  ]
}

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  if (authRequired && !loggedIn) {
    return next({
      path: '/login',
      query: { returnUrl: to.path }
    });
  }

  next();
});

export default router;
