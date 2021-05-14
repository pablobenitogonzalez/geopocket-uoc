<template>
    <div>
        <CRow>
            <CCol sm="6" md="4">
                <CWidgetIcon
                        :header="projects+''"
                        text="Projects"
                        color="primary"
                        :icon-padding="false"
                >
                    <CIcon name="cil-folder" width="24"/>
                </CWidgetIcon>
                <CWidgetIcon v-for="app in apps"
                             :header="app.total+''"
                             :text="tools[app.tool].name+' ('+tools[app.tool].description+')'"
                             :color="tools[app.tool].color"
                             :icon-padding="false"
                >
                    <CIcon name="cil-applications" width="24"/>
                </CWidgetIcon>
            </CCol>
            <CCol sm="6" md="4">
                <CCard>
                    <CCardBody>
                        <highcharts :options="appUsageChartOptions"></highcharts>
                    </CCardBody>
                </CCard>
            </CCol>
            <CCol sm="6" md="4">
                <CCard>
                    <CCardBody>
                        <highcharts :options="appPercentageChartOptions"></highcharts>
                    </CCardBody>
                </CCard>
            </CCol>
        </CRow>
        <CRow>
            <CCol sm="12">
                <CCard>
                    <CCardHeader>
                        Last Calculations
                    </CCardHeader>
                    <CCardBody>
                        <CSummaryTableWrapper v-show="!loading"
                                              :items="summaries"
                                              hover
                                              striped
                                              bordered
                                              small
                                              fixed
                        />
                    </CCardBody>
                </CCard>
            </CCol>
        </CRow>
    </div>
</template>

<script>
    import Status from './../assets/constants/status';
    import {RepositoryFactory} from './../repositories/RepositoryFactory'
    import CSummaryTableWrapper from './common/SummaryTable.vue'

    const StatisticsRepository = RepositoryFactory.get('statistics');
    export default {
        name: 'Dashboard',
        components: {
            CSummaryTableWrapper
        },
        data () {
            return {
                loading: true,
                tools: {
                    berock: {
                        name: 'Berock',
                        description: 'Bearing Resistance in Rocks',
                        color: 'primary'
                    },
                    liquec: {
                        name: 'Liquec',
                        description: 'Liquefaction According to Eurocode',
                        color: 'primary'
                    }
                },
                projects: 0,
                apps: [],
                summaries: [],
                appUsageChartOptions: {
                    colors: ['#b1b7c1', '#f9b115'],
                    chart: {
                        type: 'column',
                        inverted: true,
                        polar: true
                    },
                    title: {
                        text: 'Application usages'
                    },
                    tooltip: {
                        outside: true
                    },
                    pane: {
                        size: '95%',
                        innerSize: '20%',
                        endAngle: 270
                    },
                    xAxis: {
                        tickInterval: 1,
                        labels: {
                            align: 'right',
                            useHTML: true,
                            allowOverlap: true,
                            step: 1,
                            y: 3,
                            style: {
                                fontSize: '13px'
                            }
                        },
                        lineWidth: 0,
                        categories: []
                    },
                    yAxis: {
                        crosshair: {
                            enabled: true,
                            color: '#333'
                        },
                        lineWidth: 0,
                        tickInterval: 2,
                        reversedStacks: false,
                        endOnTick: true,
                        showLastLabel: true
                    },
                    plotOptions: {
                        column: {
                            stacking: 'normal',
                            borderWidth: 0,
                            pointPadding: 0,
                            groupPadding: 0.15
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    series: [
                        {
                            name: 'draft',
                            data: []
                        },
                        {
                            name: 'calculated',
                            data: []
                        }
                    ]
                },
                appPercentageChartOptions: {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: 'Application percentages'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    accessibility: {
                        point: {
                            valueSuffix: '%'
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                            },
                            showInLegend: false
                        }
                    },
                    legend: {
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
                const {data} = await StatisticsRepository.getStatistics();
                this.projects = data.projects;
                this.apps = data.toolMetrics;
                this.summaries = data.summaries;
                this.initUsagesChart(data);
                this.initPercentagesChart(data);
                this.loading = false;
            },
            initUsagesChart(statistics) {
                const self = this;
                statistics.toolMetrics.forEach(function (o) {
                    self.appUsageChartOptions.xAxis.categories.push(self.tools[o.tool].name);
                    statistics.toolStatusMetrics.forEach(function (e) {
                        if (o.tool === e.tool) {
                            (e.status === Status.DRAFT)? self.appUsageChartOptions.series[0].data.push(e.total) :
                                self.appUsageChartOptions.series[1].data.push(e.total);
                        }
                    })
                });

            },
            initPercentagesChart(statistics) {
                const self = this;
                const pie = {
                    name: 'Percentage',
                    colorByPoint: true,
                    data: []
                };
                statistics.toolMetrics.forEach(function (o) {
                    pie.data.push({
                        name: self.tools[o.tool].name,
                        y: o.percentage
                    })
                });
                self.appPercentageChartOptions.series.push(pie);
            }
        }
    }
</script>
