<template>
    <div>
        <CRow>
            <CCol sm="6" md="4">
                <CWidgetIcon
                        header="999"
                        text="Projects"
                        color="primary"
                        :icon-padding="true"
                >
                    <CIcon name="cil-folder" width="24"/>
                </CWidgetIcon>
                <CWidgetIcon v-for="app in apps"
                             :header="app.total+''"
                             :text="app.tool"
                             color="dark"
                             :icon-padding="true"
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
            <CCol sm="6" md="12">
                <CCard>
                    <CCardBody>
                        <highcharts :options="appTimeChartOptions"></highcharts>
                    </CCardBody>
                </CCard>
            </CCol>
        </CRow>

    </div>
</template>

<script>

    export default {
        name: 'Dashboard',
        components: {
        },
        data () {
            return {
                apps: [
                    {
                        tool: 'Liquec',
                        total: 10
                    } ,
                    {
                        tool: 'App2',
                        total: 20
                    },
                    {
                        tool: 'App3',
                        total: 5
                    }
                ],
                appUsageChartOptions: {
                    colors: ['#C0C0C0', '#FFD700'],
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
                        categories: [
                            'Liquec',
                            'App2',
                            'App3'
                        ]
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
                            name: 'Draft',
                            data: [10, 5, 7]
                        },
                        {
                            name: 'Calculated',
                            data: [22, 23, 8]
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
                                enabled: true
                            },
                            showInLegend: true
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    series: [{
                        name: 'Percentage',
                        colorByPoint: true,
                        data: [
                            {
                                name: 'Liquec',
                                y: 15.00
                            },
                            {
                                name: 'App2',
                                y: 30.00
                            },
                            {
                                name: 'App3',
                                y: 55.00
                            }
                        ]
                    }]
                },
                appTimeChartOptions: {
                    chart: {
                        type: 'spline'
                    },
                    legend: {
                        enabled: true,
                        symbolWidth: 40
                    },
                    title: {
                        text: 'Usages in last days'
                    },
                    subtitle: {
                        text: null
                    },
                    yAxis: {
                        title: {
                            text: null
                        }
                    },
                    xAxis: {
                        categories: ['05/04', '06/04', '07/04', '08/04', '09/04', '10/04', 'Today']
                    },
                    tooltip: {
                        valueSuffix: '%'
                    },
                    series: [
                        {
                            name: 'Liquec',
                            data: [0,1,3,5,0,1,2],
                            dashStyle: 'ShortDot',
                            color: '#f0a45d'
                        }, {
                            name: 'App2',
                            data: [2,2,10,2,9,3,3],
                            dashStyle: 'ShortDot'
                        }, {
                            name: 'App3',
                            data: [5,1,1,0,0,0,1],
                            dashStyle: 'ShortDot'
                        }
                    ]
                }
            }
        }
    }
</script>
