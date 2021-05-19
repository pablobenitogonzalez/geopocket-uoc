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
                caption="Berock Calculations"
                :totalPages="totalPages"
                :activePage="pageNumber+1"
                @updatePagination:activePage="onChangePagination"
        />
      </CCol>
    </CRow>
  </div>
</template>

<script>
  import Result from "../../assets/constants/Result";
  import Status from "../../assets/constants/Status";
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CTableWrapper from './BerockTable.vue'

  const BerockRepository = RepositoryFactory.get('berock');
export default {
  name: 'ManageBerockCalculations',
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
      const {data} = await BerockRepository.get(Status.CALCULATED, page);
      this.berockCollection = this.addClasses(data.content);
      this.totalPages = data.totalPages;
      this.pageNumber = data.pageable.pageNumber;
      this.loading = false;
    },
    addClasses(collection) {
      collection.forEach(function (berock) {
        berock._result = 'success';
        if (berock.result.result === Result.ERROR) {
          berock._result = 'danger';
        }
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
