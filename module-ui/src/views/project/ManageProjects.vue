<template>
  <div>
    <CRow>
      <CCol sm="12">
        <CTableWrapper
                :items="projects"
                hover
                striped
                bordered
                small
                fixed
                caption="Projects"
                :totalPages="totalPages"
                :activePage="pageNumber+1"
                @updatePagination:activePage="onChangePagination"
        />
      </CCol>
    </CRow>
  </div>
</template>

<script>
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CTableWrapper from './ProjectTable.vue'

  const ProjectRepository = RepositoryFactory.get('projects');
  export default {
    name: 'ManageProjects',
    components: { CTableWrapper },
    data() {
      return {
        projects: [],
        totalPages: 0,
        pageNumber: 0
      }
    },
    mounted() {
      this.fetch(0)
    },
    methods: {
      async fetch(page) {
        const {data} = await ProjectRepository.get(page);
        this.projects = data.content;
        this.totalPages = data.totalPages;
        this.pageNumber = data.pageable.pageNumber;
      },
      onChangePagination(page) {
        this.fetch(page-1)
      }
    }
  }
</script>

<style>
</style>
