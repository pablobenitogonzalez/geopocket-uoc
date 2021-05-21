<template>
    <CRow v-show="!loading">
      <CCol col>
        <CCard>
          <CCardHeader>
            <CRow>
              <CCol col="6" class="text-left" style="line-height: 38px">
                Berock - Calculation Results
              </CCol>
              <CCol col="6" class="text-right">
                <CButton
                        style="padding-right: 8px"
                        color="primary"
                        shape="pill"
                        size="sm"
                        :class="'btn-actions'"
                        :disabled="cloneButton"
                        @click="cloneBerock"
                        v-c-tooltip.hover="{ content: 'Clone Berock' }"
                >
                  <CIcon :height="mainIconHeight" name="cil-clone" />
                </CButton>
              </CCol>
            </CRow>
          </CCardHeader>
          <CCardBody>
            <CRow>
              <CCol sm="6">
                <CCard style="min-height: 425px">
                  <CCardHeader class="information">
                    Main Information
                  </CCardHeader>
                  <CCardBody class="information">
                    <CTabs variant="pills" vertical>
                      <CTab active>
                        <template slot="title">
                          <CIcon name="cil-applications"/> <span style="margin-left: 8px">Calculation</span>
                        </template>
                        <CListGroup>
                          <CListGroupItem><b>Calculation identifier:</b> {{berock.id}}</CListGroupItem>
                          <CListGroupItem><b>Rock Type:</b> {{getName('rockType', berock.rockType)}}</CListGroupItem>
                          <CListGroupItem><b>Weathering Degree:</b> {{getName('weatheringDegree', berock.weatheringDegree)}}</CListGroupItem>
                          <CListGroupItem v-if="!!berock.rockQualityDesignation"><b>Rock Quality Designation (RQD):</b> {{berock.rockQualityDesignation}}</CListGroupItem>
                          <CListGroupItem v-if="!berock.rockQualityDesignation"><b>Rock Quality Designation (RQD):</b> -</CListGroupItem>
                          <CListGroupItem><b>Uniaxial Compressive Strength (q<sub>u</sub>):</b> {{berock.uniaxialCompressiveStrength}} MPa</CListGroupItem>
                          <CListGroupItem><b>Calculated By:</b> {{berock.audit.updatedBy}}</CListGroupItem>
                          <CListGroupItem><b>Calculated On:</b> {{getUpdated(berock.audit.updatedOn)}}</CListGroupItem>
                        </CListGroup>
                      </CTab>
                      <CTab active>
                        <template slot="title">
                          <CIcon name="cil-circle"/> <span style="margin-left: 8px">Joint Details</span>
                        </template>
                        <CJointTableWrapper
                                :items="berock.joints"
                                hover
                                striped
                                bordered
                                small
                                fixed
                        />
                      </CTab>
                      <CTab active>
                        <template slot="title">
                          <CIcon name="cil-folder"/> <span style="margin-left: 8px">Project</span>
                        </template>
                        <CListGroup>
                          <CListGroupItem><b>Project identifier:</b> {{berock.project.id}}</CListGroupItem>
                          <CListGroupItem><b>Project Name:</b> {{berock.project.name}}</CListGroupItem>
                          <CListGroupItem><b>Project Location:</b> {{berock.project.location}}</CListGroupItem>
                          <CListGroupItem><b>Project Organization:</b> {{berock.project.organization}}</CListGroupItem>
                          <CListGroupItem><b>Owner:</b> {{berock.project.user}}</CListGroupItem>
                          <CListGroupItem><b>Created On:</b> {{getUpdated(berock.project.audit.createdOn)}}</CListGroupItem>
                          <CListGroupItem><b>Updated On:</b> {{getUpdated(berock.project.audit.updatedOn)}}</CListGroupItem>
                        </CListGroup>
                      </CTab>
                      <CTab v-if="!!berock.calculationInfo">
                        <template slot="title">
                          <CIcon name="cil-bookmark"/> <span style="margin-left: 8px">Admin Info</span>
                        </template>
                        <CListGroup>
                          <CListGroupItem><b>Build Version:</b> {{berock.calculationInfo.buildVersion}}</CListGroupItem>
                          <CListGroupItem><b>Build Group:</b> {{berock.calculationInfo.buildGroup}}</CListGroupItem>
                          <CListGroupItem><b>Build Artifact:</b> {{berock.calculationInfo.buildArtifact}}</CListGroupItem>
                          <CListGroupItem><b>Build Time:</b> {{berock.calculationInfo.buildTime}}</CListGroupItem>
                          <CListGroupItem><b>Active Profile:</b> {{berock.calculationInfo.activeProfiles}}</CListGroupItem>
                          <CListGroupItem><b>Elapsed Time:</b> {{berock.calculationInfo.elapsedTime}} (ns)</CListGroupItem>
                        </CListGroup>
                      </CTab>
                    </CTabs>
                  </CCardBody>
                </CCard>
              </CCol>
              <CCol sm="6">
                <CCard style="min-height: 425px">
                  <CCardHeader class="information">
                    Result
                  </CCardHeader>
                  <CCardBody class="information">
                    <CListGroup>
                      <CListGroupItem><b>Calculation Result:</b> {{getName('result', berock.result.result)}}</CListGroupItem>
                      <CListGroupItem><b>Allowable Bearing Pressure (p<sub>v</sub>):</b> {{getFormattedNumber(berock.result.allowableBearingPressure, 3)}} MPa</CListGroupItem>
                      <CListGroupItem><b>&alpha;<sub>3a</sub>:</b> {{getFormattedNumber(berock.result.alpha3a, 3)}}</CListGroupItem>
                      <CListGroupItem><b>&alpha;<sub>3b</sub>:</b> {{getFormattedNumber(berock.result.alpha3b, 3)}}</CListGroupItem>
                      <CListGroupItem><b>Final &alpha;<sub>3</sub>:</b> {{getFormattedNumber(berock.result.finalAlpha3, 3)}}</CListGroupItem>
                      <CListGroupItem><b>Volumetric Joint Count (J<sub>v</sub>):</b> {{getFormattedNumber(berock.result.volumetricJointCount, 3)}}</CListGroupItem>
                      <CListGroupItem><b>Calculated RDQ:</b> {{getFormattedNumber(berock.result.calculatedRDQ, 2)}} %</CListGroupItem>
                    </CListGroup>
                  </CCardBody>
                </CCard>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
</template>

<script>
  import {RepositoryFactory} from '../../repositories/RepositoryFactory'
  import moment from 'moment'
  import router from "../../router";
  import Functions from "../../assets/constants/Functions";
  import CJointTableWrapper from './JointTable.vue'

  const BerockRepository = RepositoryFactory.get('berock');
  export default {
    name: 'BerockResult',
    components: {
      CJointTableWrapper: CJointTableWrapper
    },
    created() {
      this.id = this.$route.params.id;
    },
    data () {
      return {
        loading: true,
        cloneButton: null,
        id: null,
        berock: {
          id: null,
          project: {
            name: null,
            location: null,
            organization: null,
            audit: {
              createdOn: null,
              createdBy: null
            }
          },
          rockType: null,
          weatheringDegree: null,
          rockQualityDesignation: null,
          uniaxialCompressiveStrength: null,
          joints: [],
          result: {
            result: null,
            message: null,
            allowableBearingPressure: null,
            alpha3a: null,
            alpha3b: null,
            finalAlpha3: null,
            volumetricJointCount: null,
            calculatedRDQ: null
          },
          audit: {
            updatedOn: null,
            createdOn: null
          },
          calculationInfo: {
            buildVersion: null,
            buildGroup: null,
            buildArtifact: null,
            buildTime: null,
            activeProfiles: null,
            elapsedTime: null
          }
        },
        mainIconHeight: 20
      }
    },
    mounted() {
      this.fetch()
    },
    methods: {
      async fetch() {
        const {data} = await BerockRepository.getBerock(this.id);
        this.berock = data;
        this.loading = false;
      },
      getUpdated (date) {
        return date !== null ? this.formatDate(date) : '-'
      },
      formatDate(date) {
        return moment(date).format('DD-MM-YYYY HH:mm:ss')
      },
      getName(constant, key) {
        return Functions.getLiteralName(constant, key);
      },
      async cloneBerock() {
        this.cloneButton = "disabled";
        const {data} = await BerockRepository.clone(this.berock.id);
        await router.push(`/berock/create/draft/${data.id}`);
      },
      getFormattedNumber(number, fractionDigits) {
        return Functions.getFormattedNumber(number, fractionDigits);
      }
    }
  }
</script>

<style>
</style>
