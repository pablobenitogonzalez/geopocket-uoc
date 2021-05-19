<template>
  <div v-show="!loading">
    <CRow>
      <CCol sm="12">
        <CTableWrapper
                :items="berockCollection"
                hover
                striped
                bordered
                small
                fixed
                caption="Berock Drafts"
                :totalPages="totalPages"
                :activePage="pageNumber+1"
                @updatePagination:activePage="onChangePagination"
        />
      </CCol>
    </CRow>
  </div>
</template>

<script>
  import Status from "../../assets/constants/Status";
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CTableWrapper from './BerockTable.vue'

  const BerockRepository = RepositoryFactory.get('berock');
  export default {
    name: 'ManageLBerockDrafts',
    components: { CTableWrapper },
    data () {
      return {
        loading: true,
        berockCollection: [],
        totalPages: 0,
        pageNumber: 0
      }
    },
    mounted() {
      this.fetch(0)
    },
    methods: {
      async fetch(page) {
        const {data} = await BerockRepository.get(Status.DRAFT, page);
        this.berockCollection = data.content;
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
