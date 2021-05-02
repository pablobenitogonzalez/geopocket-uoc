<template>
  <CCard>
    <CCardHeader>
      <CRow>
        <CCol col="6" class="text-left" style="line-height: 38px">
          Liquec - Liquefaction According to Eurocode
        </CCol>
        <CCol col="6" class="text-right">
          <CButton
                  style="padding-right: 8px"
                  color="primary"
                  shape="pill"
                  size="sm"
                  :disabled="saveDraftButton"
                  @click="saveDraft"
                  v-c-tooltip.hover="{ content: 'Save draft' }"
          >
            <CIcon :height="mainIconHeight" name="cil-save" />
          </CButton>
          <span style="padding-right: 10px"></span>
          <CButton
                  color="primary"
                  shape="pill"
                  size="sm"
                  :disabled="calculateButton"
                  @click="calculate"
                  v-c-tooltip.hover="{ content: 'Calculate' }"
          >
            <CIcon :height="mainIconHeight" name="cil-media-play"/>
          </CButton>
        </CCol>
      </CRow>
    </CCardHeader>
    <CCardBody>
      <CTabs>
        <CTab title="Main"
              active
        >
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
                            :checked="code.selected"
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
                <CSoilTableWrapper
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
                  <span style="padding-right: 30px">Check Liquefaction:</span>
                  <CInputRadio
                          v-for="(code, optionIndex) in checkLiquefaction"
                          :key="code.key"
                          :label="code.name"
                          type="radio"
                          :value="code.key"
                          :custom="true"
                          :name="`Option ${0}`"
                          :checked="optionIndex === 0"
                          :inline="true"
                          @update:checked="onChangeCheckLiquefaction"
                  ></CInputRadio>
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
                <CModal
                        :show.sync="checkSptModal"
                        title="Cannot Remove Last Layer"
                        color="primary"
                        :no-close-on-backdrop="true"
                        :centered="true"
                >
                  The last soil layer cannot be deleted because there are SPTs within it.
                  Remove SPTs before removing the soil layer.
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
              <br/>
              <CJumbotron style="margin-bottom: 8px">
                <CSptTableWrapper
                        :items="liquec.spts"
                        hover
                        striped
                        bordered
                        small
                        fixed
                />
                <CRow>
                  <CCol sm="6"></CCol>
                  <CCol sm="6">
                    <div style="float: right">
                      <CButton
                              color="dark"
                              @click="launchSptModal"
                              class="mr-1"
                              variant="outline"
                              :disabled="addSptModalButton"
                      >
                        Add
                      </CButton>
                      <CButton
                              color="dark"
                              @click="removeSpt"
                              class="mr-1"
                              variant="outline"
                              :disabled="removeSptButton"
                      >
                        Remove
                      </CButton>
                    </div>
                  </CCol>
                </CRow>
                <CModal
                        :show.sync="sptModal"
                        :no-close-on-backdrop="true"
                        :centered="true"
                >
                  <template #header>
                    <h6 class="modal-title">Add New SPT</h6>
                    <CButtonClose @click="sptModal = false" class="text-white"/>
                  </template>
                  <CInput
                          label="Depth (m):"
                          placeholder="Enter depth"
                          :description="depthDescription"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="spt.depth"
                          :isValid="depthInputValidation"
                          @input="onDepthInput"
                  />
                  <CInput
                          label="SPT Blow Counts (N):"
                          placeholder="Enter SPT blow counts"
                          description="Range of supported values: {0-50} (N)"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="spt.sptBlowCounts"
                          :isValid="sptBlowCountsInputValidation"
                          @input="onSptBlowCountsInput"
                  />
                  <CInput
                          label="Energy Ratio (%):"
                          placeholder="Enter energy ratio"
                          description="Range of supported values: {0.0-100.0} (%)"
                          horizontal
                          type="text"
                          :lazy=false
                          v-model="spt.energyRatio"
                          :isValid="energyRatioInputValidation"
                          @input="onEnergyRatioInput"
                  />
                  <template #footer>
                    <CButton @click="sptModal = false" color="dark">Cancel</CButton>
                    <CButton
                            @click="addSpt"
                            color="primary"
                            :disabled="addSptButton">
                      Create
                    </CButton>
                  </template>
                </CModal>
              </CJumbotron>
            </CCol>
            <CCol sm="6">
              <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                <highcharts :options="sptChartOptions"></highcharts>
              </CJumbotron>
            </CCol>
          </CRow>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
  import Autocomplete from '@trevoreyre/autocomplete-vue'
  import '@trevoreyre/autocomplete-vue/dist/style.css'
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import CSoilTableWrapper from './SoilTable.vue'
  import CSptTableWrapper from './SptTable.vue'
  import {Chart} from 'highcharts-vue'
  import router from "../../router";

  const ProjectRepository = RepositoryFactory.get('projects');
  const LiquecRepository = RepositoryFactory.get('liquec');
  export default {
    name: 'CreateNewLiquec',
    components: {
      Autocomplete,
      CSoilTableWrapper: CSoilTableWrapper,
      CSptTableWrapper: CSptTableWrapper,
      highcharts: Chart
    },
    created() {
      this.liquec.id = this.$route.params.id;
    },
    data () {
      return {
        codes: [{key: "EUROCODE", name: "Eurocode", selected: true}, {key: "NCSE_02", name: "NCSE-02", selected: false}],
        mainIconHeight: 20,
        saveDraftButton: "disabled",
        calculateButton: "disabled",
        autocomplete: [],
        selectedProject: {},
        earthquakeReadonly: false,
        coefficientReadonly: false,
        peakInputValidation: null,
        earthquakeInputValidation: null,
        coefficientInputValidation: null,
        groundInputValidation: null,
        checkSptModal: false,
        checkLiquefaction: [{key: true, name: "Yes"}, {key: false, name: "No"}],
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
        sptModal: false,
        spt: {
          depth: null,
          sptBlowCounts: null,
          energyRatio: 60.0
        },
        depthBound: 30,
        depthDescription: "Range of supported values: {0.01-30.00} (m)",
        depthInputValidation: null,
        sptBlowCountsInputValidation: null,
        energyRatioInputValidation: true,
        addSptModalButton: "disabled",
        addSptButton: "disabled",
        removeSptButton: "disabled",
        liquec: {
          id: null,
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
              format: '{value} N'
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
            pointFormat: '{point.x} m: {point.y} N'
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
            data: []
          }]
        }
      }
    },
    mounted() {
      if (this.liquec.id != null) {
        this.fetch();
      } else  {
        this.coefficientReadonly = true;
        this.earthquakeInputValidation = true;
      }
    },
    watch: {
      liquec: {
        handler(val) {
          this.saveDraftButton = (this.liquec.projectId)? null : "disabled";
          this.calculateButton = (this.liquec.projectId &&
                  this.liquec.code &&
                  this.liquec.peakGroundAcceleration &&
                  this.liquec.earthquakeMagnitude &&
                  this.liquec.coefficientOfContribution &&
                  this.liquec.groundWaterTableDepth &&
                  this.liquec.soilLayers.length > 0 &&
                  this.liquec.spts.length > 0)? null : "disabled";

        },
        deep: true
      }
    },
    methods: {
      async fetch() {
        const {data} = await LiquecRepository.getLiquec(this.liquec.id);
        this.initDraft(data);
      },
      initDraft(data) {
        const self = this;
        this.liquec.projectId = data.project.id;
        this.selectedProject = data.project;
        this.liquec.code = data.code;
        this.liquec.peakGroundAcceleration = data.peakGroundAcceleration;
        this.onPeakInput(this.liquec.peakGroundAcceleration);
        if (this.liquec.code === 'EUROCODE') {
          this.codes[0].selected = true;
          this.codes[1].selected = false;
          this.liquec.earthquakeMagnitude = data.earthquakeMagnitude;
          this.onEarthquakeInput(this.liquec.earthquakeMagnitude);
          this.liquec.coefficientOfContribution = 1.0;
          this.coefficientReadonly = true;
        } else {
          this.codes[0].selected = false;
          this.codes[1].selected = true;
          this.liquec.earthquakeMagnitude = 7.5;
          this.earthquakeReadonly = true;
          this.liquec.coefficientOfContribution = data.coefficientOfContribution;
          this.onCoefficientInput(this.liquec.coefficientOfContribution);
        }
        this.liquec.groundWaterTableDepth = data.groundWaterTableDepth;
        this.onGroundInput(this.liquec.groundWaterTableDepth);
        data.soilLayers.forEach(function (o) {
          self.liquec.soilLayers.push({
            startDepth: o.startDepth,
            finalDepth: o.finalDepth,
            soilType: o.soilType,
            aboveGwt: o.aboveGwt,
            belowGwt: o.belowGwt,
            finesContent: o.finesContent,
            checkLiquefaction: o.checkLiquefaction
          });
        });
        // highcharts
        this.setLayerChartBound();
        this.liquec.soilLayers.forEach(function (o) {
          self.layerChartOptions.series.unshift({
            color: '#' + Math.floor(Math.random()*16777215).toString(16),
            name: o.soilType,
            data: [-(parseFloat(o.finalDepth)-parseFloat(o.startDepth)), -(parseFloat(o.finalDepth)-parseFloat(o.startDepth))]
          });
          self.sptChartOptions.xAxis.plotLines.push({
            label: {
              text: o.soilType,
              align: 'left',
              style: {
                color: 'brown',
                fontWeight: '300'
              }
            },
            zIndex: -99,
            color: 'brown',
            value: -parseFloat(o.finalDepth),
            width: 0.5
          });
        });
        this.setXSptChartBound();
        // reset
        this.soilLayer.startDepth = (this.liquec.soilLayers.length > 0)? this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth : 0.0;
        this.soilLayer.layerThickness = null;
        this.soilLayer.soilType = null;
        this.soilLayer.aboveGwt =null;
        this.soilLayer.belowGwt = null;
        this.soilLayer.finesContent = null;
        this.layerThicknessBound = (this.liquec.soilLayers.length > 0)? 30 - this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth : 30.0;
        this.layerThicknessDescription = 'Range of supported values: {0.01-' + parseFloat(this.layerThicknessBound) + '} (m)';
        this.layerThicknessInputValidation = null;
        this.soilTypeInputValidation = null;
        this.aboveGwtInputValidation = null;
        this.belowGwtInputValidation = null;
        this.finesContentInputValidation = null;
        this.addLayerButton = "disabled";
        this.handleRemoveLayerButton();
        this.handleAddSptModalButton();
        data.spts.forEach(function (o) {
          self.liquec.spts.push({
            depth: o.depth,
            sptBlowCounts: o.sptBlowCounts,
            energyRatio: o.energyRatio
          });
        });
        this.liquec.spts.sort(function(a, b) {
          return parseFloat(a.depth) - parseFloat(b.depth);
        });
        // highcharts
        this.liquec.spts.forEach(function (o) {
          self.sptChartOptions.series[0].data.push([-parseFloat(o.depth), parseFloat(o.sptBlowCounts)]);
        });
        this.sptChartOptions.series[0].data.sort(function(a, b) {
          return a[0] - b[0];
        });
        // reset
        this.spt.depth = null;
        this.spt.sptBlowCounts = null;
        this.spt.energyRatio = 60.0;
        this.depthInputValidation = null;
        this.sptBlowCountsInputValidation = null;
        this.energyRatioInputValidation = true;
        this.addSptButton = "disabled";
        this.handleRemoveSptButton();
        this.setYSptChartBound();
      },
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
        this.removeGwtFromSptChart();
        if(this.groundInputValidation) {
          const gwt = {
            label: {
              text: 'GWT',
              align: 'right',
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
          };
          this.layerChartOptions.yAxis.plotLines.push(gwt);
          this.sptChartOptions.xAxis.plotLines.push(gwt);
        }
        if(this.liquec.soilLayers.length > 0) {
          this.setLayerChartBound();
          this.setXSptChartBound();
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
      onChangeCheckLiquefaction(checked, e) {
        this.soilLayer.checkLiquefaction = e.target.value;
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
        this.setXSptChartBound();
        this.sptChartOptions.xAxis.plotLines.push({
          label: {
            text: this.soilLayer.soilType,
            align: 'left',
            style: {
              color: 'brown',
              fontWeight: '300'
            }
          },
          zIndex: -99,
          color: 'brown',
          value: -parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth),
          width: 0.5
        });
        // reset
        this.soilLayer.startDepth = this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth;
        this.soilLayer.layerThickness = null;
        this.soilLayer.soilType = null;
        this.soilLayer.aboveGwt =null;
        this.soilLayer.belowGwt = null;
        this.soilLayer.finesContent = null;
        this.layerThicknessBound = 30 - this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth;
        this.layerThicknessDescription = 'Range of supported values: {0.01-' + parseFloat(this.layerThicknessBound) + '} (m)';
        this.layerThicknessInputValidation = null;
        this.soilTypeInputValidation = null;
        this.aboveGwtInputValidation = null;
        this.belowGwtInputValidation = null;
        this.finesContentInputValidation = null;
        this.addLayerButton = "disabled";
        this.handleRemoveLayerButton();
        this.handleAddSptModalButton();
        this.soilLayerModal = false;
      },
      removeSoilLayer() {
        if (this.liquec.soilLayers.length > 0 && this.checkSptLayer()) {
          const finalDepthToRemove = this.liquec.soilLayers[this.liquec.soilLayers.length - 1].finalDepth;
          this.liquec.soilLayers.pop();
          // highcharts
          this.layerChartOptions.series.shift();
          this.removeLayerFromSptChart(-parseFloat(finalDepthToRemove));
          this.setLayerChartBound();
          this.setXSptChartBound();
        }
        this.handleRemoveLayerButton();
        this.handleAddSptModalButton();
        this.soilLayer.startDepth = (this.liquec.soilLayers.length > 0)?
                this.liquec.soilLayers[this.liquec.soilLayers.length - 1].finalDepth : 0.0;
      },
      checkSptLayer() {
        const startDepthToRemove = this.liquec.soilLayers[this.liquec.soilLayers.length - 1].startDepth;
        const finalDepthToRemove = this.liquec.soilLayers[this.liquec.soilLayers.length - 1].finalDepth;
        const index = this.liquec.spts.findIndex(function (o) {
          return o.depth <= finalDepthToRemove && o.depth >= startDepthToRemove;
        });
        if (index !== -1) {
          this.checkSptModal = true;
          return false;
        }
        return true;
      },
      setLayerChartBound() {
        const margin = 3.0;
        const layerBound = ((this.liquec.soilLayers.length > 0)? -parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth) : 0.0) - margin;
        const gwtBound = ((this.liquec.groundWaterTableDepth != null && this.liquec.groundWaterTableDepth !== '')? -parseFloat(this.liquec.groundWaterTableDepth) : 0.0) - margin;
        const lower = Math.min(layerBound, gwtBound);
        this.layerChartOptions.yAxis.min = (lower < -30 || lower === -margin)? -30 : lower;
        this.cleanLayerChart();
      },
      cleanLayerChart() {
        if (this.dirtyLayerChartSeries) {
          const index = this.layerChartOptions.series.findIndex(function (o) {
            return o.id === '#empty';
          });
          if (index !== -1) this.layerChartOptions.series.splice(index, 1);
          this.dirtyLayerChartSeries = false;
        }
      },
      removeGwtFromSptChart() {
        const index = this.sptChartOptions.xAxis.plotLines.findIndex(function (o) {
          return o.label.text === 'GWT';
        });
        if (index !== -1) this.sptChartOptions.xAxis.plotLines.splice(index, 1);
      },
      removeLayerFromSptChart(value) {
        const index = this.sptChartOptions.xAxis.plotLines.findIndex(function (o) {
          return o.value === value;
        });
        if (index !== -1) this.sptChartOptions.xAxis.plotLines.splice(index, 1);
      },
      setXSptChartBound() {
        const margin = 3.0;
        const layerBound = ((this.liquec.soilLayers.length > 0)? -parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth) : 0.0) - margin;
        const gwtBound = ((this.liquec.groundWaterTableDepth != null && this.liquec.groundWaterTableDepth !== '')? -parseFloat(this.liquec.groundWaterTableDepth) : 0.0) - margin;
        const sptBound = ((this.liquec.spts.length > 0)? -parseFloat(this.liquec.spts[this.liquec.spts.length-1].depth) : 0.0) - margin;
        const lower = Math.min(layerBound, gwtBound, sptBound);
        this.sptChartOptions.xAxis.min = (lower < -30 || lower === -margin)? -30 : lower;
      },
      setYSptChartBound() {
        const margin = 3.0;
        const maxSpt = Math.max.apply(Math, this.liquec.spts.map(function(o) { return o.sptBlowCounts; })) + margin;
        this.sptChartOptions.yAxis.max = (maxSpt < 50 || maxSpt === margin)? maxSpt : 50;
      },
      onDepthInput(value) {
        this.depthInputValidation = !!value && value >= 0.01 && value <= this.depthBound;
        this.handleAddSptButton();
      },
      onSptBlowCountsInput(value) {
        this.sptBlowCountsInputValidation = !!value && value >= 0.00 && value <= 50.0;
        this.handleAddSptButton();
      },
      onEnergyRatioInput(value) {
        this.energyRatioInputValidation = !!value && value >= 0.00 && value <= 100.0;
        this.handleAddSptButton();
      },
      handleAddSptModalButton() {
        this.addSptModalButton = (this.liquec.soilLayers.length > 0)? null : "disabled";
      },
      launchSptModal() {
        this.depthBound = (this.liquec.soilLayers.length > 0)? parseFloat(this.liquec.soilLayers[this.liquec.soilLayers.length-1].finalDepth) : 0.0;
        this.depthDescription = 'Range of supported values: {0.01-' + parseFloat(this.depthBound) + '} (m)';;
        this.sptModal = true;
      },
      handleAddSptButton() {
        this.addSptButton = (
                this.depthInputValidation &&
                this.sptBlowCountsInputValidation &&
                this.energyRatioInputValidation)? null : "disabled";
      },
      handleRemoveSptButton() {
        this.removeSptButton = (this.liquec.spts.length > 0)? null : "disabled";
      },
      addSpt() {
        this.liquec.spts.push({
          depth: this.spt.depth,
          sptBlowCounts: this.spt.sptBlowCounts,
          energyRatio: this.spt.energyRatio
        });
        this.liquec.spts.sort(function(a, b) {
          return parseFloat(a.depth) - parseFloat(b.depth);
        });
        // highcharts
        this.sptChartOptions.series[0].data.push([-parseFloat(this.spt.depth), parseFloat(this.spt.sptBlowCounts)]);
        this.sptChartOptions.series[0].data.sort(function(a, b) {
          return a[0] - b[0];
        });
        // reset
        this.spt.depth = null;
        this.spt.sptBlowCounts = null;
        this.spt.energyRatio = 60.0;
        this.depthInputValidation = null;
        this.sptBlowCountsInputValidation = null;
        this.energyRatioInputValidation = true;
        this.addSptButton = "disabled";
        this.handleRemoveSptButton();
        this.setYSptChartBound();
        this.sptModal = false;
      },
      removeSpt() {
        if (this.liquec.spts.length > 0) {
          this.liquec.spts.pop();
          // highcharts
          this.sptChartOptions.series[0].data.shift();
        }
        this.handleRemoveSptButton();
        this.setYSptChartBound();
      },
      async calculate() {
        const {data} = await LiquecRepository.calculate(this.liquec);
        await router.push(`/liquec/${data.id}`);
      },
      async saveDraft() {
        const {data} = await LiquecRepository.saveDraft(this.liquec);
        this.liquec.id = data.id;
      }
    }
  }
</script>

<style>
</style>
