<template>
  <CCard>
    <CCardHeader>
      <CRow>
        <CCol col="6" class="text-left">
          Liquec - Liquefaction According to Eurocode
        </CCol>
        <CCol col="6" class="text-right">
          <span style="padding-right: 12px">Calculate</span>
          <CButton
                  color="primary"
                  shape="pill"
                  size="sm" disabled>
            <CIcon name="cil-media-play"/>
          </CButton>
        </CCol>
      </CRow>
    </CCardHeader>
    <CCardBody>
      <CTabs>
        <CTab title="Main" active>
          <br/>
          <br/>
          <CRow>
            <CCol sm="6">
              <CJumbotron style="margin-bottom: 8px">
                <div class="form-row form-group">
                  <CCol sm="4">Calculation Mode:</CCol>
                  <CCol sm="8" :class="'form-inline'">
                    <CInputRadio
                            v-for="(code, optionIndex) in codes"
                            :key="code.key"
                            :label="code.name"
                            type="radio"
                            :value="code.key"
                            :custom="true"
                            :name="`Option ${0}`"
                            :checked="optionIndex === 0"
                            :inline="true"
                            @update:checked="onChangeChecked"
                    />
                  </CCol>
                </div>
                <hr/>
                <br/>
                <autocomplete
                        :search="search"
                        placeholder="Search for a project"
                        :get-result-value="getAutocompleteResultValue"
                        @submit="handleAutocompleteSubmit"
                />
                <br/>
                <CListGroup>
                  <CListGroupItem>Project id: {{selectedProject.id}}</CListGroupItem>
                  <CListGroupItem>Project name: {{selectedProject.name}}</CListGroupItem>
                  <CListGroupItem>Project location: {{selectedProject.location}}</CListGroupItem>
                  <CListGroupItem>Project organization: {{selectedProject.organization}}</CListGroupItem>
                </CListGroup>
              </CJumbotron>
            </CCol>
            <CCol sm="6">
              <CJumbotron style="margin-bottom: 8px">
                <CInput
                        label="Peak Ground Acceleration (a/g)"
                        placeholder="Enter peak ground acceleration"
                        description="Range of supported values: {0.01-10.00} (a/g)"
                        type="text"
                        :lazy=false
                        v-model="liquec.peakGroundAcceleration"
                        :isValid="peakInputValidation"
                        @input="onPeakInput"
                />
                <hr/>
                <CInput
                        label="Earthquake Magnitude (Mw)"
                        placeholder="Enter earthquake magnitude"
                        description="Range of supported values: {0.1-10.0} (Mw)"
                        type="text"
                        :lazy=false
                        :readonly="earthquakeReadonly"
                        v-model="liquec.earthquakeMagnitude"
                        :isValid="earthquakeInputValidation"
                        @input="onEarthquakeInput"
                />
                <hr/>
                <CInput
                        label="Coefficient of Contribution (K)"
                        placeholder="Enter coefficient of contribution"
                        description="Range of supported values: {1.0-2.0} (K)"
                        type="text"
                        :lazy=false
                        :readonly="coefficientReadonly"
                        v-model="liquec.coefficientOfContribution"
                        :isValid="coefficientInputValidation"
                        @input="onCoefficientInput"
                />
              </CJumbotron>
            </CCol>
          </CRow>
          <br/>
        </CTab>
        <CTab title="Profile">
          <br/>
          <br/>
          <CRow>
            <CCol sm="9">
              <CJumbotron style="margin-bottom: 8px">
                <CInput
                        label="Ground Water Table Depth [GWT] (m)"
                        placeholder="Enter ground water table depth"
                        description="Range of supported values: {0.00-30.00} (m)"
                        type="text"
                        horizontal
                        :lazy=false
                        v-model="liquec.groundWaterTableDepth"
                        :isValid="groundInputValidation"
                        @input="onGroundInput"
                />
                <hr/>
                <CTableWrapper
                        :items="liquec.soilLayers"
                        hover
                        striped
                        bordered
                        small
                        fixed
                />
                <CRow>
                  <CCol sm="8"></CCol>
                  <CCol sm="4">
                    <div style="float: right">
                      <CButton
                              color="dark"
                              @click="soilLayerModal = true"
                              class="mr-1"
                              variant="outline"
                      >
                        Add
                      </CButton>
                      <CButton
                              color="dark"
                              @click="removeSoilLayer"
                              class="mr-1"
                              variant="outline"
                              :disabled="removeLayerButton"
                      >
                        Remove
                      </CButton>
                    </div>
                  </CCol>
                </CRow>
                <CModal
                        :show.sync="soilLayerModal"
                        :no-close-on-backdrop="true"
                        :centered="true"
                >
                  <template #header>
                    <h6 class="modal-title">Add New Soil Layer</h6>
                    <CButtonClose @click="soilLayerModal = false" class="text-white"/>
                  </template>
                  <CInput
                          label="Start Depth (m):"
                          type="text"
                          v-model="soilLayer.startDepth"
                          disabled="disabled"
                          horizontal
                  />
                  <CInput
                          label="Layer Thickness (m):"
                          placeholder="Enter layer thickness"
                          :description="layerThicknessDescription"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="soilLayer.layerThickness"
                          :isValid="layerThicknessInputValidation"
                          @input="onLayerThicknessInput"
                  />
                  <CInput
                          label="Soil Type:"
                          placeholder="Enter soil type"
                          description="Any custom description"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="soilLayer.soilType"
                          :isValid="soilTypeInputValidation"
                          @input="onSoilTypeInput"
                  />
                  <CInput
                          label="Above GWT (KN/m3):"
                          placeholder="Enter above GWT"
                          description="Range of supported values: {11.0-50.0} (KN/m3)"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="soilLayer.aboveGwt"
                          :isValid="aboveGwtInputValidation"
                          @input="onAboveGwtInput"
                  />
                  <CInput
                          label="Below GWT (KN/m3):"
                          placeholder="Enter below GWT"
                          description="Range of supported values: {11.0-50.0} (KN/m3)"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="soilLayer.belowGwt"
                          :isValid="belowGwtInputValidation"
                          @input="onBelowGwtInput"
                  />
                  <CInput
                          label="Fines Content (%):"
                          placeholder="Enter fines content"
                          description="Range of supported values: {0.0-100.0} (%)"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="soilLayer.finesContent"
                          :isValid="finesContentInputValidation"
                          @input="onFinesContentInput"
                  />
                  <template #footer>
                    <CButton @click="soilLayerModal = false" color="dark">Cancel</CButton>
                    <CButton
                            @click="addSoilLayer"
                            color="primary"
                            :disabled="addLayerButton">
                      Create
                    </CButton>
                  </template>
                </CModal>
              </CJumbotron>
              <br/>
            </CCol>
            <CCol sm="3">
              <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                <highcharts :options="layerChartOptions"></highcharts>
              </CJumbotron>
            </CCol>
          </CRow>
        </CTab>
        <CTab title="SPT">
          <br/>
          <CRow>
            <CCol sm="6">
              <CJumbotron style="margin-bottom: 8px">
              </CJumbotron>
            </CCol>
            <CCol sm="6">
              <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                <highcharts :options="sptChartOptions"></highcharts>
              </CJumbotron>
            </CCol>
          </CRow>
        </CTab>
        <CTab title="Result" disabled>
          3. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore
          et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
          dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
          officia deserunt mollit anim id est laborum.
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>



</template>

<script>
  import Autocomplete from '@trevoreyre/autocomplete-vue'
  import '@trevoreyre/autocomplete-vue/dist/style.css'
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CTableWrapper from './SoilTable.vue'
  import {Chart} from 'highcharts-vue'

  const ProjectRepository = RepositoryFactory.get('projects');
  export default {
    name: 'CreateNewLiquec',
    components: {
      Autocomplete,
      CTableWrapper,
      highcharts: Chart
    },
    data () {
      return {
        codes: [{key: "EUROCODE", name: "Eurocode"}, {key: "NCSE_02", name: "NCSE-02"}],
        autocomplete: [],
        selectedProject: {},
        earthquakeReadonly: false,
        coefficientReadonly: true,
        peakInputValidation: null,
        earthquakeInputValidation: true,
        coefficientInputValidation: null,
        groundInputValidation: null,
        soilLayerModal: false,
        soilLayer: {
          startDepth: 0.0,
          layerThickness: null,
          soilType: null,
          aboveGwt: null,
          belowGwt: null,
          finesContent: null,
          checkLiquefaction: true,
        },
        layerThicknessBound: 30,
        layerThicknessDescription: "Range of supported values: {0.01-30.00} (m)",
        layerThicknessInputValidation: null,
        soilTypeInputValidation: null,
        aboveGwtInputValidation: null,
        belowGwtInputValidation: null,
        finesContentInputValidation: null,
        addLayerButton: "disabled",
        removeLayerButton: "disabled",
        liquec: {
          projectId: null,
          code: "EUROCODE",
          peakGroundAcceleration: null,
          earthquakeMagnitude: 7.5,
          coefficientOfContribution: 1.0,
          groundWaterTableDepth: null,
          soilLayers: [],
          spts: []
        },
        dirtyLayerChartSeries: true,
        layerChartOptions: {
          chart: {
            type: 'area'
          },
          title: {
            text: null
          },
          xAxis: {
            opposite: true,
            visible: false
          },
          yAxis: {
            max: 0,
            min: -30,
            title: null,
            plotLines: [],
            labels: {
              format: '{value} m'
            }
          },
          credits: {
            enabled: false
          },
          plotOptions: {
            series: {
              fillOpacity: 0.3
            },
            area: {
              stacking: 'normal',
              lineColor: 'black',
              lineWidth: 0.2,
              marker: {
                enabled: false
              }
            }
          },
          legend: {
            enabled: false
          },
          series: [{ id: '#empty' }]
        },
        dirtySptChartSeries: true,
        sptChartOptions: {
          chart: {
            type: 'spline',
            inverted: true
          },
          title: {
            text: null
          },
          subtitle: {
            text: null
          },
          xAxis: {
            min: -20,
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
                  text: 'GWT',
                  style: {
                    color: 'blue',
                    fontWeight: 'bold'
                  }
                },
                zIndex: 99,
                color: 'blue',
                dashStyle: 'longdashdot',
                value: -3,
                width: 2
              },
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
              },
              {
                label: {
                  text: 'rock',
                  align: 'right',
                  style: {
                    color: 'brown',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'brown',
                value: -2,
                width: 0.5
              },
              {
                label: {
                  text: 'sand',
                  align: 'right',
                  style: {
                    color: 'brown',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'brown',
                value: -10,
                width: 0.5
              },
              {
                label: {
                  text: 'limo',
                  align: 'right',
                  style: {
                    color: 'brown',
                    fontWeight: '300'
                  }
                },
                zIndex: -99,
                color: 'brown',
                value: -17,
                width: 0.5
              }
            ]
          },
          yAxis: {
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
            name: 'SPT',
            data: [[0, 2], [-5, 8], [-10, 4], [-15, 12]]
          }]
        }
      }
    },
    methods: {
      onChangeChecked(checked, e) {
        this.liquec.code = e.target.value;
        if (this.liquec.code === this.codes[1].key) { // NCSE_02
          this.earthquakeReadonly = true;
          this.earthquakeInputValidation = null;
          this.coefficientReadonly = false;
          this.onCoefficientInput(this.liquec.coefficientOfContribution);
        } else { // EUROCODE
          this.earthquakeReadonly = false;
          this.onEarthquakeInput(this.liquec.earthquakeMagnitude);
          this.coefficientReadonly = true;
          this.coefficientInputValidation = null;
        }
      },
      async fetchAutocomplete(name) {
        const {data} = await ProjectRepository.autocomplete(name);
        this.autocomplete = data;
      },
      search(input) {
        if (input.length < 2) {
          this.autocomplete = [];
          return this.autocomplete;
        }
        this.fetchAutocomplete(input);
        return this.autocomplete;
      },
      getAutocompleteResultValue(result) {
        return result.name
      },
      handleAutocompleteSubmit(result) {
        this.selectedProject = result;
        this.liquec.projectId = result.id;
      },
      onPeakInput(value) {
        this.peakInputValidation = !!value && value >= 0.01 && value <= 10.0;
      },
      onEarthquakeInput(value) {
        this.earthquakeInputValidation = !!value && value >= 0.1 && value <= 10.0;
      },
      onCoefficientInput(value) {
        this.coefficientInputValidation = !!value && value >= 1.0 && value <= 2.0;
      },
      onGroundInput(value) {
        this.groundInputValidation = !!value && value >= 0.0 && value <= 30.0;
        // highcharts
        this.layerChartOptions.yAxis.plotLines = [];
        if(this.groundInputValidation) {
          this.layerChartOptions.yAxis.plotLines.push({
            label: {
              text: 'GWT',
              style: {
                color: 'blue',
                fontWeight: 'bold'
              }
            },
            zIndex: 99,
            color: 'blue',
            dashStyle: 'dash',
            value: -parseFloat(value),
            width: 2
          })
        }
        if(this.liquec.soilLayers.length > 0) {
          this.setLayerChartBound();
        }
      },
      onLayerThicknessInput(value) {
        this.layerThicknessInputValidation = !!value && value >= 0.01 && value <= parseFloat(this.layerThicknessBound);
        this.handleAddLayerButton();
      },
      onSoilTypeInput(value) {
        this.soilTypeInputValidation = !!value;
        this.handleAddLayerButton();
      },
      onAboveGwtInput(value) {
        this.aboveGwtInputValidation = !!value && value >= 11.00 && value <= 50.0;
        this.handleAddLayerButton();
      },
      onBelowGwtInput(value) {
        this.belowGwtInputValidation = !!value && value >= 11.00 && value <= 50.0;
        this.handleAddLayerButton();
      },
      onFinesContentInput(value) {
        this.finesContentInputValidation = !!value && value >= 0.00 && value <= 100.0;
        this.handleAddLayerButton();
      },
      handleAddLayerButton() {
        this.addLayerButton = (
                this.layerThicknessInputValidation &&
                this.soilTypeInputValidation &&
                this.aboveGwtInputValidation &&
                this.belowGwtInputValidation &&
                this.finesContentInputValidation)? null : "disabled";
      },
      handleRemoveLayerButton() {
        this.removeLayerButton = (this.liquec.soilLayers.length > 0)? null : "disabled";
      },
      addSoilLayer() {
        this.liquec.soilLayers.push({
          startDepth: this.soilLayer.startDepth,
          finalDepth: parseFloat(this.soilLayer.startDepth) + parseFloat(this.soilLayer.layerThickness),
          soilType: this.soilLayer.soilType,
          aboveGwt: this.soilLayer.aboveGwt,
          belowGwt: this.soilLayer.belowGwt,
          finesContent: this.soilLayer.finesContent,
          checkLiquefaction: this.soilLayer.checkLiquefaction
        });
        // highcharts
        this.setLayerChartBound();
        this.layerChartOptions.series.unshift({
          color: '#' + Math.floor(Math.random()*16777215).toString(16),
          name: this.soilLayer.soilType,
          data: [-parseFloat(this.soilLayer.layerThickness), -parseFloat(this.soilLayer.layerThickness)]
        });
        // reset
        this.soilLayer.startDepth = this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth;
        this.soilLayer.layerThickness = null;
        this.soilLayer.soilType = null;
        this.soilLayer.aboveGwt =null;
        this.soilLayer.belowGwt = null;
        this.soilLayer.finesContent = null;
        this.soilLayer.checkLiquefaction = true;
        this.layerThicknessBound = 30 - this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth;
        this.layerThicknessDescription = 'Range of supported values: {0.01-' + parseFloat(this.layerThicknessBound) + '} (m)';
        this.layerThicknessInputValidation = null;
        this.soilTypeInputValidation = null;
        this.aboveGwtInputValidation = null;
        this.belowGwtInputValidation = null;
        this.finesContentInputValidation = null;
        this.addLayerButton = "disabled";
        this.handleRemoveLayerButton();
        this.soilLayerModal = false;
      },
      removeSoilLayer() {
        if (this.liquec.soilLayers.length > 0) {
          this.liquec.soilLayers.pop();
        }
        this.handleRemoveLayerButton();
        this.soilLayer.startDepth = (this.liquec.soilLayers.length > 0)?
                this.liquec.soilLayers[this.liquec.soilLayers.length - 1].finalDepth : 0.0;
        // highcharts
        if (this.liquec.soilLayers.length > 0) {
          this.layerChartOptions.series.shift();
        } else {
          this.layerChartOptions.series = [];
        }
        this.setLayerChartBound();
      },
      setLayerChartBound() {
        const margin = 5.0;
        const layerBound = ((this.liquec.soilLayers.length > 0)? -parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth) : 0.0) - margin;
        const gwtBound = ((this.liquec.groundWaterTableDepth != null && this.liquec.groundWaterTableDepth !== '')? -parseFloat(this.liquec.groundWaterTableDepth) : 0.0) - margin;
        const lower = Math.min(layerBound, gwtBound);
        this.layerChartOptions.yAxis.min = (lower < -30 || lower === -margin)? -30 : lower;
        this.cleanLayerChart()
      },
      cleanLayerChart() {
        if (this.dirtyLayerChartSeries) {
          const index = this.layerChartOptions.series.findIndex(function (o) {
            return o.id === '#empty';
          });
          if (index !== -1) this.layerChartOptions.series.splice(index, 1);
          this.dirtyLayerChartSeries = false;
        }
      }
    }
  }
</script>

<style>
</style>
