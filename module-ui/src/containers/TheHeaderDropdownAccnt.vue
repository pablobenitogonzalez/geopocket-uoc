<template>
  <CDropdown
          inNav
          class="c-header-nav-items"
          placement="bottom-end"
          add-menu-classes="pt-0"
          @update:show="onClickDropdown"
  >
    <template #toggler>
      <CHeaderNavLink>
        <div class="c-avatar">
          <img
                  src="img/avatars/default.jpg"
                  class="c-avatar-img "
          />
        </div>
      </CHeaderNavLink>
    </template>
    <CDropdownHeader tag="div" class="text-center" color="light">
      <strong>Account</strong>
    </CDropdownHeader>
    <CDropdownItem v-on:click="goToPath('/dashboard')">
      <CIcon name="cil-user" /> {{userName}}
    </CDropdownItem>
    <CDropdownItem v-on:click="goToPath('/project/manage')">
      <CIcon name="cil-folder" /> Projects
      <CBadge color="primary" class="ml-auto">{{projects}}</CBadge>
    </CDropdownItem>
    <CDropdownHeader tag="div" class="text-center" color="light">
      <strong>Applications</strong>
    </CDropdownHeader>
    <CDropdownItem v-for="app in apps"
                   v-on:click="goToPath(tools[app.tool].path)">
      <CIcon name="cil-applications" /> {{tools[app.tool].name}}
      <CBadge color="warning" class="ml-auto">{{app.count}}</CBadge>
    </CDropdownItem>
    <CDropdownDivider/>
    <CDropdownItem v-on:click="logout()">
      <CIcon name="cil-lock-locked" /> Logout
    </CDropdownItem>
  </CDropdown>
</template>

<script>
  import router from './../router'
  import {RepositoryFactory} from './../repositories/RepositoryFactory'

  const SecurityRepository = RepositoryFactory.get('security');
  const ProjectRepository = RepositoryFactory.get('projects');
  const StatisticsRepository = RepositoryFactory.get('statistics');
  export default {
    name: 'TheHeaderDropdownAccnt',
    data () {
      return {
        userName: null,
        projects: 0,
        apps: [],
        tools: {
          liquec: {
            name: 'Liquec',
            path: '/liquec/calculations'
          }
        }
      }
    },
    created() {
    },
    mounted() {
      this.setProfile();
      this.fetch();
    },
    methods: {
      logout() {
        router.push('/login');
      },
      setProfile() {
        this.userName = SecurityRepository.getUserName();
      },
      async fetch() {
        const self = this;
        await ProjectRepository.countProjects().then(response => self.projects = response.data);
        await StatisticsRepository.countAll().then(response => {
          self.apps = response.data
        });
      },
      goToPath(path) {
        if (router.currentRoute.path !== path) {
          router.push(path);
        }
      },
      onClickDropdown(show) {
        if (show) {
          this.fetch();
        }
      }
    }
  }
</script>

<style scoped>
  .c-icon {
    margin-right: 0.3rem;
  }
</style>
