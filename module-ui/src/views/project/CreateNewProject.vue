<template>
  <CRow>
    <CCol col>
      <CCard>
        <CCardHeader>
          <CRow>
            <CCol col="6" class="text-left" style="line-height: 38px">
              Create a New Project
            </CCol>
            <CCol col="6" class="text-right">
              <CButton
                      style="padding-right: 8px"
                      color="primary"
                      shape="pill"
                      size="sm"
                      :class="'btn-actions'"
                      :disabled="saveButton"
                      @click="save"
                      v-c-tooltip.hover="{ content: 'Create project' }"
              >
                <CIcon :height="mainIconHeight" name="cil-save" />
              </CButton>
            </CCol>
          </CRow>
        </CCardHeader>
        <CCardBody>
          <CRow>
            <CCol sm="12">
              <CJumbotron style="margin-bottom: 8px">
                <CInput
                        label="Project Name:"
                        placeholder="Enter project name"
                        description="Range of supported characters: {3-60}"
                        type="text"
                        :lazy=false
                        v-model="project.name"
                        :isValid="nameInputValidation"
                        @input="onNameInput"
                        horizontal
                />
                <hr/>
                <CInput
                        label="Project Location:"
                        placeholder="Enter project location"
                        description="Range of supported characters: {3-60}"
                        type="text"
                        :lazy=false
                        v-model="project.location"
                        :isValid="locationInputValidation"
                        @input="onLocationInput"
                        horizontal
                />
                <hr/>
                <CInput
                        label="Project Organization:"
                        placeholder="Enter project organization"
                        description="Range of supported characters: {3-60}"
                        type="text"
                        :lazy=false
                        v-model="project.organization"
                        :isValid="organizationInputValidation"
                        @input="onOrganizationInput"
                        horizontal
                />
              </CJumbotron>
            </CCol>
          </CRow>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import router from "../../router";

  const ProjectRepository = RepositoryFactory.get('projects');
  export default {
    name: 'CreateNewProject',
    data () {
      return {
        stringMin: 3,
        stringMax: 60,
        mainIconHeight: 20,
        saveButton: "disabled",
        nameInputValidation: null,
        locationInputValidation: null,
        organizationInputValidation: null,
        project: {
          id: null,
          name: null,
          location: null,
          organization: null
        }
      }
    },
    watch: {
      project: {
        handler(val) {
          this.saveButton = (this.nameInputValidation &&
                  this.locationInputValidation &&
                  this.organizationInputValidation)? null : "disabled";
        },
        deep: true
      }
    },
    methods: {
      async save() {
        const {data} = await ProjectRepository.createProject(this.project);
        await router.push(`/project/manage`);
      },
      onNameInput(value) {
        this.nameInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      },
      onLocationInput(value) {
        this.locationInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      },
      onOrganizationInput(value) {
        this.organizationInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      }
    }
  }
</script>

<style>
</style>
