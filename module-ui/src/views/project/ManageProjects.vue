<template>
  <CRow>
    <CCol col>
      <CCard>
        <CCardHeader>
          <CIcon name="cil-justify-center"/><strong> Manage Projects</strong>
        </CCardHeader>
        <CCardBody>

        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'

  const ProjectRepository = RepositoryFactory.get('projects');
  export default {
    name: 'ManageProjects',
    data () {
      return {
        loading: false,
        projects: []
      }
    },
    created() {
      this.fetch()
    },
    methods: {
      async fetch() {{
        console.log('start')
        this.loading = true;
        const {data} = await ProjectRepository.get();
        console.log(data)
        this.loading = false;
        this.projects = data;
      }}
    }
  }
</script>

<style>
</style>
