<template>
  <div v-show="!loading">
    <CRow>
      <CCol sm="12">
        <CTableWrapper
                :items="liquecCollection"
                hover
                striped
                bordered
                small
                fixed
                caption="Liquec Drafts"
                :totalPages="totalPages"
                :activePage="pageNumber+1"
                @updatePagination:activePage="onChangePagination"
        />
      </CCol>
    </CRow>
  </div>
</template>

<script>
  import Status from "../../assets/constants/status";
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CTableWrapper from './LiquecTable.vue'

  const LiquecRepository = RepositoryFactory.get('liquec');
  export default {
    name: 'ManageLiquecDrafts',
    components: { CTableWrapper },
    data () {
      return {
        loading: true,
        liquecCollection: [],
        totalPages: 0,
        pageNumber: 0
      }
    },
    mounted() {
      this.fetch(0)
    },
    methods: {
      async fetch(page) {
        const {data} = await LiquecRepository.get(Status.DRAFT, page);
        this.liquecCollection = data.content;
        this.totalPages = data.totalPages;
        this.pageNumber = data.pageable.pageNumber;
        this.loading = false;
      },
      onChangePagination(page) {
        this.fetch(page-1)
      }
    }
  }
</script>

<style>
</style>
