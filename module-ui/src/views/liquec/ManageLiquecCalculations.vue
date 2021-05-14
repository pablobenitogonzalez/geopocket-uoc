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
                caption="Liquec Calculations"
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
  name: 'ManageLiquecCalculations',
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
      const {data} = await LiquecRepository.get(Status.CALCULATED, page);
      this.liquecCollection = this.addClasses(data.content);
      this.totalPages = data.totalPages;
      this.pageNumber = data.pageable.pageNumber;
      this.loading = false;
    },
    addClasses(collection) {
      collection.forEach(function (liquec) {
        liquec._result = 'success';
        liquec.spts.forEach(function (spt) {
          if (spt.sptResult.safetyFactor != null && spt.sptResult.safetyFactor < 1) {
            liquec._result = 'danger';
          } else if (spt.sptResult.safetyFactor != null && liquec._result !== 'danger' && spt.sptResult.safetyFactor < 1.25) {
            liquec._result = 'warning';
          }
        });
      });
      return collection;
    },
    onChangePagination(page) {
      this.fetch(page-1)
    }
  }
}
</script>

<style>
</style>
