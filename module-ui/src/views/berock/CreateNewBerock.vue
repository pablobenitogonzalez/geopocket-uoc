<template>
  <CCard>
    <CCardHeader>
      <CRow>
        <CCol col="6" class="text-left" style="line-height: 38px">
          Berock - Bearing Resistance in Rocks
        </CCol>
        <CCol col="6" class="text-right">
          <CButton
                  style="padding-right: 8px"
                  color="primary"
                  shape="pill"
                  size="sm"
                  :class="'btn-actions'"
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
                  :class="'btn-actions'"
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
      <CModal
              :show.sync="saveDraftModal"
              title="Berock Draft Saved"
              color="primary"
              :no-close-on-backdrop="true"
              :centered="true"
      >
        Berock has been correctly stored as draft.
      </CModal>
      <CRow>
        <CCol sm="4">
          <CJumbotron style="margin-bottom: 8px">
            <CInput
                    label="Uniaxial compressive strength (MPa)"
                    placeholder="Enter uniaxial compressive strength"
                    description="Range of supported values: {0.1-1000.0} (Mpa)"
                    type="text"
                    :lazy=false
                    v-model="berock.uniaxialCompressiveStrength"
                    :isValid="uniaxialCompressiveStrengthValidation"
                    @input="onUniaxialInput"
            />
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
              <CListGroupItem><b>Project Identifier:</b> {{selectedProject.id}}</CListGroupItem>
              <CListGroupItem><b>Project Name:</b> {{selectedProject.name}}</CListGroupItem>
              <CListGroupItem><b>Project Location:</b> {{selectedProject.location}}</CListGroupItem>
              <CListGroupItem><b>Project Organization:</b> {{selectedProject.organization}}</CListGroupItem>
            </CListGroup>
          </CJumbotron>
        </CCol>
        <CCol sm="4">
          <CJumbotron style="margin-bottom: 8px">
            <label>Rock Type Influence (&alpha;<sub>1</sub>):</label>
            <CSelect
                    :value.sync="rockTypeSelectedOption"
                    :options="rockTypeOptions"
                    placeholder="Please select"
                    @update:value="onRockTypeChange"
            />
            <small class="form-text text-muted w-100" v-for="metadata in rockTypeMetadata">{{metadata}}</small>
            <hr/>
            <br/>
            <label>Weathering Degree Influence (&alpha;<sub>2</sub>):</label>
            <CSelect
                    :value.sync="weatheringDegreeSelectedOption"
                    :options="weatheringDegreeOptions"
                    placeholder="Please select"
                    @update:value="onWeatheringDegreeChange"
            />
            <small class="form-text text-muted w-100">
              If the weathering degree is equal or bigger than grade IV the bearing pressure should be calculated with the soil method
            </small>
            <hr/>
            <br/>
            <CInput
                    label="Rock Quality Designation (%)"
                    placeholder="Enter rock quality designation"
                    description="Range of supported values: {0.1-100.0} (%)"
                    type="text"
                    :lazy=false
                    v-model="berock.rockQualityDesignation"
                    :isValid="rockQualityDesignationValidation"
                    @input="onRockQualityInput"
            />
          </CJumbotron>
        </CCol>
        <CCol sm="4">
          <CJumbotron style="margin-bottom: 8px">
            <CJointTableWrapper
                    :items="berock.joints"
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
                          @click="launchJointModal"
                          class="mr-1"
                          variant="outline"
                          :disabled="addJointModalButton"
                  >
                    Add
                  </CButton>
                  <CButton
                          color="dark"
                          @click="removeJoint"
                          class="mr-1"
                          variant="outline"
                          :disabled="removeJointButton"
                  >
                    Remove
                  </CButton>
                </div>
              </CCol>
            </CRow>
            <CModal
                    :show.sync="jointModal"
                    :no-close-on-backdrop="true"
                    :centered="true"
            >
              <template #header>
                <h6 class="modal-title">Add New Joint</h6>
                <CButtonClose @click="jointModal = false" class="text-white"/>
              </template>
              <CInput
                      label="Spacing (m):"
                      placeholder="Enter spacing"
                      description="Range of supported values: {0.01-30.0} (N)"
                      horizontal
                      type="text"
                      :lazy=false
                      v-model="joint.spacing"
                      :isValid="spacingInputValidation"
                      @input="onSpacingInput"
              />
              <CInput
                      label="Joint Family:"
                      placeholder="Enter joint family"
                      description="Range of supported characters: {1-60}"
                      horizontal
                      type="text"
                      :lazy=false
                      v-model="joint.family"
                      :isValid="familyInputValidation"
                      @input="onFamilyInput"
              />
              <template #footer>
                <CButton @click="jointModal = false" color="dark">Cancel</CButton>
                <CButton
                        @click="addJoint"
                        color="primary"
                        :disabled="addJointButton">
                  Create
                </CButton>
              </template>
            </CModal>
          </CJumbotron>
        </CCol>
      </CRow>
    </CCardBody>
  </CCard>
</template>

<script>
  import RockType from '../../assets/constants/RockType';
  import WeatheringDegree from '../../assets/constants/WeatheringDegree';
  import Functions from "../../assets/constants/Functions";
  import Autocomplete from '@trevoreyre/autocomplete-vue'
  import '@trevoreyre/autocomplete-vue/dist/style.css'
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import router from "../../router";
  import CJointTableWrapper from './JointTable.vue'

  const ProjectRepository = RepositoryFactory.get('projects');
  const BerockRepository = RepositoryFactory.get('berock');
  export default {
    name: 'CreateNewBerock',
    components: {
      Autocomplete,
      CJointTableWrapper: CJointTableWrapper
    },
    created() {
      this.berock.id = this.$route.params.id;
    },
    data () {
      return {
        mainIconHeight: 20,
        saveDraftModal: false,
        saveDraftButton: "disabled",
        calculateButton: "disabled",
        autocomplete: [],
        selectedProject: {},
        berock: {
          id: null,
          projectId: null,
          rockType: RockType.GROUP_1,
          weatheringDegree: WeatheringDegree.DEGREE_1,
          rockQualityDesignation: null,
          uniaxialCompressiveStrength: null,
          joints: []
        },
        joint: {
          spacing: null,
          family: null
        },
        uniaxialCompressiveStrengthValidation: null,
        rockQualityDesignationValidation: null,
        rockTypeOptions: [],
        rockTypeMetadata: [],
        rockTypeSelectedOption: null,
        weatheringDegreeOptions: [],
        weatheringDegreeSelectedOption: null,
        jointModal: false,
        addJointModalButton: null,
        removeJointButton: "disabled",
        spacingInputValidation: null,
        familyInputValidation: null,
        addJointButton: "disabled",
        stringMin: 1,
        stringMax: 60,
      }
    },
    mounted() {
      if (this.berock.id != null) {
        this.fetch();
      }
      this.rockTypeOptions = [
        {value: RockType.GROUP_1, label: Functions.getLiteralName('rockType', RockType.GROUP_1)},
        {value: RockType.GROUP_2, label: Functions.getLiteralName('rockType', RockType.GROUP_2)},
        {value: RockType.GROUP_3, label: Functions.getLiteralName('rockType', RockType.GROUP_3)},
        {value: RockType.GROUP_4, label: Functions.getLiteralName('rockType', RockType.GROUP_4)}
      ];
      this.rockTypeMetadata = Functions.getLiteralMetadata('rockType', RockType.GROUP_1);
      this.rockTypeSelectedOption = RockType.GROUP_1;
      this.weatheringDegreeOptions = [
        {value: WeatheringDegree.DEGREE_1, label: Functions.getLiteralName('weatheringDegree', WeatheringDegree.DEGREE_1)},
        {value: WeatheringDegree.DEGREE_2, label: Functions.getLiteralName('weatheringDegree', WeatheringDegree.DEGREE_2)},
        {value: WeatheringDegree.DEGREE_3, label: Functions.getLiteralName('weatheringDegree', WeatheringDegree.DEGREE_3)}
      ];
      this.weatheringDegreeSelectedOption = WeatheringDegree.DEGREE_1;
    },
    watch: {
      berock: {
        handler(val) {
          this.saveDraftButton = (this.berock.projectId)? null : "disabled";
          this.calculateButton = (this.berock.projectId &&
                          this.berock.uniaxialCompressiveStrength &&
                          this.berock.rockType &&
                          this.berock.weatheringDegree &&
                          this.berock.joints.length > 0)? null : "disabled";
        },
        deep: true
      }
    },
    methods: {
      async fetch() {
        const {data} = await BerockRepository.getBerock(this.berock.id);
        this.initDraft(data);
      },
      initDraft(data) {
        const self = this;
        this.berock.projectId = data.project.id;
        this.selectedProject = data.project;
        this.berock.uniaxialCompressiveStrength = data.uniaxialCompressiveStrength;
        this.onUniaxialInput(this.berock.uniaxialCompressiveStrength);
        this.berock.rockType = data.rockType;
        this.rockTypeSelectedOption = data.rockType;
        this.berock.weatheringDegree = data.weatheringDegree;
        this.weatheringDegreeSelectedOption = data.weatheringDegree;
        this.berock.rockQualityDesignation = data.rockQualityDesignation;
        this.onRockQualityInput(this.berock.rockQualityDesignation);
        data.joints.forEach(function (o) {
          self.berock.joints.push({
            spacing: o.spacing,
            family: o.family
          });
        });
        // reset
        this.uniaxialCompressiveStrengthValidation = null;
        this.rockQualityDesignationValidation = null;
        this.addJointButton = "disabled";
        this.handleRemoveJointButton();
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
        this.berock.projectId = result.id;
      },
      onUniaxialInput(value) {
        this.uniaxialCompressiveStrengthValidation = !!value && value >= 0.01 && value <= 1000.0;
      },
      onRockQualityInput(value) {
        if (!!value) {
          this.rockQualityDesignationValidation =value >= 0.01 && value <= 100.0;
        } else {
          this.rockQualityDesignationValidation = null;
        }
      },
      onRockTypeChange(value) {
        this.berock.rockType = value;
        this.rockTypeMetadata = Functions.getLiteralMetadata('rockType', value);
      },
      onWeatheringDegreeChange(value) {
        this.berock.weatheringDegree = value;
      },
      launchJointModal() {
        this.jointModal = true;
      },
      handleRemoveJointButton() {
        this.removeJointButton = (this.berock.joints.length > 0)? null : "disabled";
      },
      removeJoint() {
        if (this.berock.joints.length > 0) {
          this.berock.joints.pop();
        }
        this.handleRemoveJointButton();
      },
      handleAddJointButton() {
        this.addJointButton = (this.spacingInputValidation)? null : "disabled";
      },
      onSpacingInput(value) {
        this.spacingInputValidation = !!value && value >= 0.01 && value <= 30.0;
        this.handleAddJointButton();
      },
      onFamilyInput(value) {
        if (!!value) {
          this.familyInputValidation = value.length >= this.stringMin && value.length <= this.stringMax;
        } else {
          this.familyInputValidation = null;
        }
        this.handleAddJointButton();
      },
      addJoint() {
        this.berock.joints.push({
          spacing: this.joint.spacing,
          family: this.joint.family
        });
        // reset
        this.joint.spacing = null;
        this.joint.family = null;
        this.spacingInputValidation = null;
        this.familyInputValidation = null;
        this.addJointButton = "disabled";
        this.handleRemoveJointButton();
        this.jointModal = false;
      },
      async calculate() {
        this.calculateButton = "disabled";
        const {data} = await BerockRepository.calculate(this.berock);
        await router.push(`/berock/${data.id}`);
      },
      saveDraft() {
        const self = this;
        this.saveDraftButton = "disabled";
        BerockRepository.saveDraft(this.berock).then(response => {
          self.berock.id = response.id;
          self.saveDraftModal = true;
          self.saveDraftButton = null;
        });
      }
    }
  }
</script>

<style>
</style>
