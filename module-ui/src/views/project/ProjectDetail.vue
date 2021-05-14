<template>
  <div>
    <CRow>
      <CCol col>
        <CCard>
          <CCardHeader>
            <CRow>
              <CCol col="6" class="text-left" style="line-height: 38px">
                Project Details
              </CCol>
              <CCol col="6" class="text-right">
                <CButton
                        style="padding-right: 8px"
                        color="primary"
                        shape="pill"
                        size="sm"
                        @click="editProject"
                        v-c-tooltip.hover="{ content: 'Edit project' }"
                >
                  <CIcon :height="mainIconHeight" name="cil-pencil" />
                </CButton>
                <span style="padding-right: 10px"></span>
                <CButton
                        style="padding-right: 8px"
                        color="primary"
                        shape="pill"
                        size="sm"
                        :disabled="deleteButton"
                        @click="deleteProject"
                        v-c-tooltip.hover="{ content: 'Delete project' }"
                >
                  <CIcon :height="mainIconHeight" name="cil-trash" />
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
                    <CListGroup>
                      <CListGroupItem><b>Project Identifier:</b> {{project.id}}</CListGroupItem>
                      <CListGroupItem><b>Project Name:</b> {{project.name}}</CListGroupItem>
                      <CListGroupItem><b>Project Location:</b> {{project.location}}</CListGroupItem>
                      <CListGroupItem><b>Project Organization:</b> {{project.organization}}</CListGroupItem>
                      <CListGroupItem><b>Project Owner:</b> {{project.user}} (<i>last updated by {{project.audit.updatedBy}}</i>)</CListGroupItem>
                      <CListGroupItem><b>Created On:</b> {{getDate(project.audit.createdOn)}}</CListGroupItem>
                      <CListGroupItem><b>Updated On:</b> {{getDate(project.audit.updatedOn)}}</CListGroupItem>
                    </CListGroup>
                  </CCardBody>
                </CCard>
              </CCol>
              <CCol sm="6">
                <CJumbotron style="margin-bottom: 8px; background-color: #ffffff; padding: 1rem 1rem">
                  <highcharts :options="usagesChartOptions"></highcharts>
                </CJumbotron>
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12">
                <CJumbotron v-show="!loading" style="margin-bottom: 8px">
                  <CSummaryTableWrapper
                          :items="summaries"
                          hover
                          striped
                          bordered
                          small
                          fixed
                  />
                </CJumbotron>
              </CCol>
            </CRow>
            <CModal
                    :show.sync="projectModal"
                    :no-close-on-backdrop="true"
                    :centered="true"
            >
              <template #header>
                <h6 class="modal-title">Edit Project</h6>
                <CButtonClose @click="closeEditModal" class="text-white"/>
              </template>
              <CInput
                      label="Project Name:"
                      placeholder="Enter project name"
                      description="Range of supported characters: {3-60}"
                      type="text"
                      :lazy=false
                      v-model="modalName"
                      :isValid="nameInputValidation"
                      @input="onNameInput"
                      horizontal
              />
              <CInput
                      label="Project Location:"
                      placeholder="Enter project location"
                      description="Range of supported characters: {3-60}"
                      type="text"
                      :lazy=false
                      v-model="modalLocation"
                      :isValid="locationInputValidation"
                      @input="onLocationInput"
                      horizontal
              />
              <CInput
                      label="Project Organization:"
                      placeholder="Enter project organization"
                      description="Range of supported characters: {3-60}"
                      type="text"
                      :lazy=false
                      v-model="modalOrganization"
                      :isValid="organizationInputValidation"
                      @input="onOrganizationInput"
                      horizontal
              />
              <template #footer>
                <CButton @click="closeEditModal" color="dark">Cancel</CButton>
                <CButton
                        @click="edit"
                        color="primary"
                        :disabled="editButton">
                  Update
                </CButton>
              </template>
            </CModal>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  </div>
</template>

<script>
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'
  import moment from 'moment'
  import router from "../../router";
  import CSummaryTableWrapper from '../common/SummaryTable.vue'
  import Status from "../../assets/constants/status";

  const ProjectRepository = RepositoryFactory.get('projects');
  const StatisticsRepository = RepositoryFactory.get('statistics');
  export default {
    name: 'ProjectDetail',
    components: {
      CSummaryTableWrapper
    },
    watch: {
      project: {
        handler(val) {
          this.editButton = (this.nameInputValidation &&
                  this.locationInputValidation &&
                  this.organizationInputValidation)? null : "disabled";
        },
        deep: true
      }
    },
    created() {
      this.id = this.$route.params.id;
    },
    data () {
      return {
        loading: true,
        id: null,
        status: [
          {key: Status.DRAFT, value: 'Draft'},
          {key: Status.CALCULATED, value: 'Calculated'}
        ],
        tools: {
          berock: {
            name: 'Berock'
          },
          liquec: {
            name: 'Liquec'
          }
        },
        summaries: [],
        stringMin: 3,
        stringMax: 60,
        mainIconHeight: 20,
        deleteButton: "disabled",
        editButton: "disabled",
        projectModal: false,
        nameInputValidation: null,
        locationInputValidation: null,
        organizationInputValidation: null,
        project: {
          id: null,
          name: null,
          location: null,
          organization: null,
          user: null,
          audit: {
            createdOn: null,
            updatedOn: null,
            updatedBy: null
          }
        },
        modalName: null,
        modalLocation: null,
        modalOrganization: null,
        toolUsages: [],
        usagesChartOptions: {
          chart: {
            type: 'bar'
          },
          title: {
            text: null
          },
          subtitle: {
            text: null
          },
          xAxis: {
            categories: [],
            title: {
              text: null
            }
          },
          yAxis: {
            min: 0,
            title: {
              text: null
            },
            labels: {
              overflow: 'justify'
            }
          },
          tooltip: {
            valueSuffix: null
          },
          plotOptions: {
            bar: {
              dataLabels: {
                enabled: true
              }
            }
          },
          credits: {
            enabled: false
          },
          series: []
        }
      }
    },
    mounted() {
      this.fetch()
    },
    methods: {
      async fetch() {
        const {data} = await ProjectRepository.getProject(this.id);
        this.project = data;
        const self = this;
        await StatisticsRepository.getUsages(this.id).then(response => {
          this.initChart(response.data);
          this.checkDeleteButton(response.data);
          self.toolUsages = response.data;
        });
        await StatisticsRepository.getSummaries(this.id).then(response => {
          self.summaries = response.data;
          this.loading = false;
        });
      },
      getDate (date) {
        return date !== null ? this.formatDate(date) : '-'
      },
      formatDate(date) {
        return moment(date).format('DD-MM-YYYY HH:mm:ss')
      },
      editProject() {
        this.modalName = this.project.name;
        this.modalLocation = this.project.location;
        this.modalOrganization = this.project.organization;
        this.onNameInput(this.modalName);
        this.onLocationInput(this.modalLocation);
        this.onOrganizationInput(this.modalOrganization);
        this.editButton = (this.nameInputValidation &&
                this.locationInputValidation &&
                this.organizationInputValidation)? null : "disabled";
        this.projectModal = true;
      },
      async deleteProject() {
        this.deleteButton = "disabled";
        await ProjectRepository.deleteProject(this.id).then(() => {
          router.push(`/project/manage`);
        });
      },
      initChart(toolUsages) {
        const self = this;
        toolUsages.forEach(function (o) {
          const status = self.status.find(e => e.key === o.status);
          self.usagesChartOptions.xAxis.categories.push(`${self.tools[o.tool].name}<br>{${status.value}}`);
        });
        let users = toolUsages[0].usages.map(usage => usage.user);
        users.forEach((function (user) {
          let data = [];
          toolUsages.forEach(function (toolUsage) {
            toolUsage.usages.forEach(function (usage) {
              if (usage.user === user) {
                data.push(usage.usage);
              }
            })
          });
          self.usagesChartOptions.series.push({
            name: user,
            data: data
          });
        }));
      },
      checkDeleteButton(toolUsages) {
        let canDelete = true;
        toolUsages.forEach(function (toolUsage) {
          toolUsage.usages.forEach(function (usage) {
            if (usage.usage > 0) canDelete = false;
          });
        });
        this.deleteButton = (canDelete)? null : "disabled";
      },
      async edit() {
        this.project.name = this.modalName;
        this.project.location = this.modalLocation;
        this.project.organization = this.modalOrganization;
        const {data} = await ProjectRepository.updateProject(this.id, this.project);
        this.project = data;
        this.modalName = null;
        this.modalLocation = null;
        this.modalOrganization = null;
        this.projectModal = false;
      },
      onNameInput(value) {
        this.nameInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      },
      onLocationInput(value) {
        this.locationInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      },
      onOrganizationInput(value) {
        this.organizationInputValidation = !!value && value.length >= this.stringMin && value.length <= this.stringMax;
      },
      closeEditModal()  {
        this.modalName = null;
        this.modalLocation = null;
        this.modalOrganization = null;
        this.projectModal = false;
      }
    }
  }
</script>

<style>
</style>
