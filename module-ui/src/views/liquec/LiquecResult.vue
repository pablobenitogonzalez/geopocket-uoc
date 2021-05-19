<template>
    <CRow v-show="!loading">
      <CCol col>
        <CCard>
          <CCardHeader>
            <CRow>
              <CCol col="6" class="text-left" style="line-height: 38px">
                Liquec - Calculation Results
              </CCol>
              <CCol col="6" class="text-right">
                <CButton
                        style="padding-right: 8px"
                        color="primary"
                        shape="pill"
                        size="sm"
                        @click="cloneLiquec"
                        v-c-tooltip.hover="{ content: 'Clone Liquec' }"
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
                          <CListGroupItem><b>Calculation identifier:</b> {{liquec.id}}</CListGroupItem>
                          <CListGroupItem v-if="liquec.code === EUROCODE"><b>Code:</b> {{literal_eurocode}}</CListGroupItem>
                          <CListGroupItem v-if="liquec.code === NCSE_02"><b>Code:</b> {{literal_ncse_02}}</CListGroupItem>
                          <CListGroupItem><b>Peak Ground Acceleration:</b> {{liquec.peakGroundAcceleration}} (a/g)</CListGroupItem>
                          <CListGroupItem v-if="liquec.code === EUROCODE"><b>Earthquake Magnitude:</b> {{liquec.earthquakeMagnitude}} (Mw)</CListGroupItem>
                          <CListGroupItem v-if="liquec.code === NCSE_02"><b>Coefficient Of Contribution:</b> {{liquec.coefficientOfContribution}} (K)</CListGroupItem>
                          <CListGroupItem><b>Calculated By:</b> {{liquec.audit.updatedBy}}</CListGroupItem>
                          <CListGroupItem><b>Calculated On:</b> {{getUpdated(liquec.audit.updatedOn)}}</CListGroupItem>
                        </CListGroup>
                      </CTab>
                      <CTab active>
                        <template slot="title">
                          <CIcon name="cil-circle"/> <span style="margin-left: 8px">Soil Details</span>
                        </template>
                        <CSoilTableWrapper
                                :items="liquec.soilLayers"
                                hover
                                striped
                                bordered
                                small
                                fixed
                                :fields="['startDepth', 'finalDepth', 'soilType', 'finesContent']"
                        />
                      </CTab>
                      <CTab active>
                        <template slot="title">
                          <CIcon name="cil-folder"/> <span style="margin-left: 8px">Project</span>
                        </template>
                        <CListGroup>
                          <CListGroupItem><b>Project identifier:</b> {{liquec.project.id}}</CListGroupItem>
                          <CListGroupItem><b>Project Name:</b> {{liquec.project.name}}</CListGroupItem>
                          <CListGroupItem><b>Project Location:</b> {{liquec.project.location}}</CListGroupItem>
                          <CListGroupItem><b>Project Organization:</b> {{liquec.project.organization}}</CListGroupItem>
                          <CListGroupItem><b>Owner:</b> {{liquec.project.user}}</CListGroupItem>
                          <CListGroupItem><b>Created On:</b> {{getUpdated(liquec.project.audit.createdOn)}}</CListGroupItem>
                          <CListGroupItem><b>Updated On:</b> {{getUpdated(liquec.project.audit.updatedOn)}}</CListGroupItem>
                        </CListGroup>
                      </CTab>
                      <CTab v-if="!!liquec.calculationInfo">
                        <template slot="title">
                          <CIcon name="cil-bookmark"/> <span style="margin-left: 8px">Admin Info</span>
                        </template>
                        <CListGroup>
                          <CListGroupItem><b>Build Version:</b> {{liquec.calculationInfo.buildVersion}}</CListGroupItem>
                          <CListGroupItem><b>Build Group:</b> {{liquec.calculationInfo.buildGroup}}</CListGroupItem>
                          <CListGroupItem><b>Build Artifact:</b> {{liquec.calculationInfo.buildArtifact}}</CListGroupItem>
                          <CListGroupItem><b>Build Time:</b> {{liquec.calculationInfo.buildTime}}</CListGroupItem>
                          <CListGroupItem><b>Active Profile:</b> {{liquec.calculationInfo.activeProfiles}}</CListGroupItem>
                          <CListGroupItem><b>Elapsed Time:</b> {{liquec.calculationInfo.elapsedTime}} (ns)</CListGroupItem>
                        </CListGroup>
                      </CTab>
                    </CTabs>
                  </CCardBody>
                </CCard>
              </CCol>
              <CCol sm="6">
                <CJumbotron style="margin-bottom: 8px; min-height: 425px">
                  <CResultTableWrapper
                          :items="liquec.spts"
                          hover
                          striped
                          bordered
                          small
                          fixed
                  />
                </CJumbotron>
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="3">
                <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                  <highcharts :options="sptCorrectedChartOptions"></highcharts>
                </CJumbotron>
              </CCol>
              <CCol sm="3">
                <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                  <highcharts :options="cycleStressRatioChartOptions"></highcharts>
                </CJumbotron>
              </CCol>
              <CCol sm="3">
                <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                  <highcharts :options="cycleResistRatioChartOptions"></highcharts>
                </CJumbotron>
              </CCol>
              <CCol sm="3">
                <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                  <highcharts :options="safetyFactorChartOptions"></highcharts>
                </CJumbotron>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
</template>

<script>
  import Code from '../../assets/constants/Code';
  import Result from '../../assets/constants/Result';
  import Functions from '../../assets/constants/Functions';
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CResultTableWrapper from './ResultTable.vue'
  import CSoilTableWrapper from './SoilTable.vue'
  import CSptTableWrapper from './SptTable.vue'
  import moment from 'moment'
  import router from "../../router";

  const LiquecRepository = RepositoryFactory.get('liquec');
  export default {
    name: 'LiquecResult',
    components: {
      CResultTableWrapper: CResultTableWrapper,
      CSoilTableWrapper: CSoilTableWrapper,
      CSptTableWrapper: CSptTableWrapper
    },
    created() {
      this.id = this.$route.params.id;
    },
    data () {
      return {
        loading: true,
        EUROCODE: Code.EUROCODE,
        NCSE_02: Code.NCSE_02,
        literal_eurocode: Functions.getLiteralName('code', Code.EUROCODE),
        literal_ncse_02: Functions.getLiteralName('code', Code.NCSE_02),
        id: null,
        liquec: {
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
          code: null,
          peakGroundAcceleration: null,
          earthquakeMagnitude: null,
          coefficientOfContribution: null,
          groundWaterTableDepth: null,
          soilLayers: [],
          spts: [],
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
        mainIconHeight: 20,
        sptCorrectedChartOptions: {
          chart: {
            type: 'spline',
            inverted: true
          },
          title: {
            text: 'SPT Corrected'
          },
          subtitle: {
            text: null
          },
          xAxis: {
            max: 0,
            min: -30,
            reversed: false,
            title: {
              enabled: false,
              text: null
            },
            labels: {
              format: '{value} m'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to -30'
            },
            maxPadding: 0.05,
            showLastLabel: true,
            plotLines: [
              {
                label: {
                  text: '',
                  align: 'right',
                  style: {
                    color: 'black',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'black',
                dashStyle: 'dash',
                value: 0,
                width: 0.5
              }
            ]
          },
          yAxis: {
            min: 0,
            max: 50,
            opposite: true,
            title: {
              text: null
            },
            labels: {
              format: '{value}'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to 20'
            },
            lineWidth: 2
          },
          legend: {
            enabled: false
          },
          tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x} m: {point.y}'
          },
          plotOptions: {
            spline: {
              marker: {
                enable: false
              }
            }
          },
          series: [{
            name: 'SPT(60)',
            color: 'magenta',
            data: []
          }]
        },
        cycleStressRatioChartOptions: {
          chart: {
            type: 'spline',
            inverted: true
          },
          title: {
            text: 'Cyclic Stress Ratio (CSR)'
          },
          subtitle: {
            text: null
          },
          xAxis: {
            max: 0,
            min: -30,
            reversed: false,
            title: {
              enabled: false,
              text: null
            },
            labels: {
              format: '{value} m'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to -30'
            },
            maxPadding: 0.05,
            showLastLabel: true,
            plotLines: [
              {
                label: {
                  text: '',
                  align: 'right',
                  style: {
                    color: 'black',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'black',
                dashStyle: 'dash',
                value: 0,
                width: 0.5
              }
            ]
          },
          yAxis: {
            min: 0,
            max: 50,
            opposite: true,
            title: {
              text: null
            },
            labels: {
              format: '{value}'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to 20'
            },
            lineWidth: 2
          },
          legend: {
            enabled: false
          },
          tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x} m: {point.y}'
          },
          plotOptions: {
            spline: {
              marker: {
                enable: false
              }
            }
          },
          series: [{
            name: 'CSR',
            color: 'orange',
            data: []
          }]
        },
        cycleResistRatioChartOptions: {
          chart: {
            type: 'spline',
            inverted: true
          },
          title: {
            text: 'Cyclic Resistance Ratio (CRR)'
          },
          subtitle: {
            text: null
          },
          xAxis: {
            max: 0,
            min: -30,
            reversed: false,
            title: {
              enabled: false,
              text: null
            },
            labels: {
              format: '{value} m'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to -30'
            },
            maxPadding: 0.05,
            showLastLabel: true,
            plotLines: [
              {
                label: {
                  text: '',
                  align: 'right',
                  style: {
                    color: 'black',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'black',
                dashStyle: 'dash',
                value: 0,
                width: 0.5
              }
            ]
          },
          yAxis: {
            min: 0,
            max: 50,
            opposite: true,
            title: {
              text: null
            },
            labels: {
              format: '{value}'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to 20'
            },
            lineWidth: 2
          },
          legend: {
            enabled: false
          },
          tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x} m: {point.y}'
          },
          plotOptions: {
            spline: {
              marker: {
                enable: false
              }
            }
          },
          series: [{
            name: 'CRR',
            data: []
          }]
        },
        safetyFactorChartOptions: {
          chart: {
            type: 'spline',
            inverted: true
          },
          title: {
            text: 'Safety Factor'
          },
          subtitle: {
            text: null
          },
          xAxis: {
            max: 0,
            min: -30,
            reversed: false,
            title: {
              enabled: false,
              text: null
            },
            labels: {
              format: '{value} m'
            },
            accessibility: {
              rangeDescription: 'Range: 0 to -30'
            },
            maxPadding: 0.05,
            showLastLabel: true,
            plotLines: [
              {
                label: {
                  text: '',
                  align: 'right',
                  style: {
                    color: 'black',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'black',
                dashStyle: 'dash',
                value: 0,
                width: 0.5
              }
            ]
          },
          yAxis: {
            min: 0,
            max: 50,
            opposite: true,
            title: {
              text: null
            },
            labels: {
              format: '{value}'
            },
            plotBands: [
              {
                color: 'rgba(245, 185, 185, 1)',
                zIndex: -999,
                from: 0,
                to: 1
              },
              {
                color: 'rgba(245, 201, 70, 0.3)',
                zIndex: -999,
                from: 1,
                to: 1.25
              }
            ],
            plotLines: [
              {
                label: {
                  text: 'SF: 1.25',
                  verticalAlign: 'bottom',
                  textAlign: 'center',
                  rotation: 0,
                  style: {
                    color: 'black',
                    fontWeight: 'light'
                  }
                },
                zIndex: -99,
                color: 'black',
                dashStyle: 'dash',
                value: 1.25,
                width: 1
              }
            ],
            accessibility: {
              rangeDescription: 'Range: 0 to 20'
            },
            lineWidth: 2
          },
          legend: {
            enabled: false
          },
          tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x} m: {point.y}'
          },
          plotOptions: {
            spline: {
              marker: {
                enable: false
              }
            }
          },
          series: [{
            name: 'SF',
            color: 'green',
            data: []
          }]
        }
      }
    },
    mounted() {
      this.fetch()
    },
    methods: {
      async fetch() {
        const {data} = await LiquecRepository.getLiquec(this.id);
        this.liquec = this.addClasses(data);
        this.initCharts();
        this.loading = false;
      },
      addClasses(data) {
        data.spts.forEach(function (spt) {
          if (spt.sptResult.result === Result.ERROR) {
            spt._classes = 'table-dark';
          } else if (spt.sptResult.result === Result.SKIP) {
            spt._classes = 'table-dark';
          } else if (spt.sptResult.safetyFactor < 1) {
            spt._classes = 'table-danger';
          } else if (spt.sptResult.safetyFactor < 1.25) {
            spt._classes = 'table-warning';
          }
        });
        return data;
      },
      getUpdated (date) {
        return date !== null ? this.formatDate(date) : '-'
      },
      formatDate(date) {
        return moment(date).format('DD-MM-YYYY HH:mm:ss')
      },
      async cloneLiquec() {
        const {data} = await LiquecRepository.clone(this.liquec.id);
        await router.push(`/liquec/create/draft/${data.id}`);
      },
      initCharts() {
        const self = this;
        const gwt = {
          label: {
            text: '',
            style: {
              color: 'blue',
              fontWeight: 'bold'
            }
          },
          zIndex: -99,
          color: 'blue',
          dashStyle: 'dash',
          value: -parseFloat(this.liquec.groundWaterTableDepth),
          width: 1
        };
        const xBound = this.getXChartBound();
        this.sptCorrectedChartOptions.xAxis.plotLines.push(gwt);
        this.sptCorrectedChartOptions.xAxis.min = xBound;
        this.sptCorrectedChartOptions.yAxis.max = this.getYSptCorrectedChartBound();
        this.cycleStressRatioChartOptions.xAxis.plotLines.push(gwt);
        this.cycleStressRatioChartOptions.xAxis.min = xBound;
        this.cycleStressRatioChartOptions.yAxis.max = this.getYCycleStressRatioChartBound();
        this.cycleResistRatioChartOptions.xAxis.plotLines.push(gwt);
        this.cycleResistRatioChartOptions.xAxis.min = xBound;
        this.cycleResistRatioChartOptions.yAxis.max = this.getYCycleResistanceRatioChartBound();
        this.safetyFactorChartOptions.xAxis.plotLines.push(gwt);
        this.safetyFactorChartOptions.xAxis.min = xBound;
        this.safetyFactorChartOptions.yAxis.max = this.getYSafetyFactorChartBound();
        this.liquec.spts.forEach(function (spt) {
          if (spt.sptResult.result !== Result.ERROR && spt.sptResult.result !== Result.SKIP) {
            // highcharts: spt corrected
            self.sptCorrectedChartOptions.series[0].data.push([-parseFloat(spt.depth), parseFloat(spt.sptResult.sptCorrected)]);
            self.sptCorrectedChartOptions.series[0].data.sort(function (a, b) {
              return a[0] - b[0];
            });
            // highcharts: cycle stress ratio
            self.cycleStressRatioChartOptions.series[0].data.push([-parseFloat(spt.depth), parseFloat(spt.sptResult.cycleStressRatio)]);
            self.cycleStressRatioChartOptions.series[0].data.sort(function (a, b) {
              return a[0] - b[0];
            });
            // highcharts: cycle resistance ratio
            self.cycleResistRatioChartOptions.series[0].data.push([-parseFloat(spt.depth), parseFloat(spt.sptResult.cycleResistanceRatio)]);
            self.cycleResistRatioChartOptions.series[0].data.sort(function (a, b) {
              return a[0] - b[0];
            });
            // highcharts: safety factor
            self.safetyFactorChartOptions.series[0].data.push([-parseFloat(spt.depth), parseFloat(spt.sptResult.safetyFactor)]);
            self.safetyFactorChartOptions.series[0].data.sort(function (a, b) {
              return a[0] - b[0];
            });
          }
        });
      },
      getXChartBound() {
        const margin = 3.0;
        const layerBound = ((this.liquec.soilLayers.length > 0)? -parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth) : 0.0) - margin;
        const gwtBound = ((this.liquec.groundWaterTableDepth != null && this.liquec.groundWaterTableDepth !== '')? -parseFloat(this.liquec.groundWaterTableDepth) : 0.0) - margin;
        const sptBound = ((this.liquec.spts.length > 0)? -parseFloat(this.liquec.spts[this.liquec.spts.length-1].depth) : 0.0) - margin;
        const lower = Math.min(layerBound, gwtBound, sptBound);
        return (lower < -30 || lower === -margin)? -30 : lower;
      },
      getYSptCorrectedChartBound() {
        return Math.max.apply(Math, this.liquec.spts.map(function(o) { return o.sptResult.sptCorrected; })) * 1.25;
      },
      getYCycleStressRatioChartBound() {
        return Math.max.apply(Math, this.liquec.spts.map(function(o) { return o.sptResult.cycleStressRatio; })) * 1.25;
      },
      getYCycleResistanceRatioChartBound() {
        return Math.max.apply(Math, this.liquec.spts.map(function(o) { return o.sptResult.cycleResistanceRatio; })) * 1.25;
      },
      getYSafetyFactorChartBound() {
        return Math.max.apply(Math, this.liquec.spts.map(function(o) { return o.sptResult.safetyFactor; })) * 1.25;
      }
    }
  }
</script>

<style>
</style>
