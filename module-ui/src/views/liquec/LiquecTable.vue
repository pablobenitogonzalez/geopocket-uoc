<template>
    <CCard>
        <CCardHeader>
            <slot name="header">
                <CIcon name="cil-grid"/> {{caption}}
            </slot>
        </CCardHeader>
        <CCardBody>
            <CDataTable
                    :hover="hover"
                    :striped="striped"
                    :bordered="bordered"
                    :small="small"
                    :fixed="fixed"
                    :items="items"
                    :fields="fields"
                    :items-per-page="small ? 10 : 5"
                    :dark="dark"
            >
                <template #id="{item}">
                    <td>
                        <CBadge v-if="item._result === 'success'" shape="pill" color="success">{{item.id}}</CBadge>
                        <CBadge v-if="item._result === 'warning'" shape="pill" color="warning">{{item.id}}</CBadge>
                        <CBadge v-if="item._result === 'danger'" shape="pill" color="danger">{{item.id}}</CBadge>
                        <CBadge v-if="item.status === DRAFT" shape="pill" color="secondary">{{item.id}}</CBadge>
                    </td>
                </template>
                <template #project="{item}">
                    <td>{{item.project.name}}</td>
                </template>
                <template #mode="{item}">
                    <td v-if="item.code === EUROCODE">{{literal_eurocode}}</td>
                    <td v-if="item.code === NCSE_02">{{literal_ncse_02}}</td>
                </template>
                <template #layers="{item}">
                    <td>{{item.soilLayers.length}}</td>
                </template>
                <template #spts="{item}">
                    <td>{{item.spts.length}}</td>
                </template>
                <template #created="{item}">
                    <td>{{getCreated(item.audit)}}</td>
                </template>
                <template #updated="{item}">
                    <td>{{getUpdated(item.audit)}}</td>
                </template>
                <template #owner="{item}">
                    <td>{{item.audit.createdBy}}</td>
                </template>
                <template #updatedBy="{item}">
                    <td>{{item.audit.updatedBy}}</td>
                </template>
                <template #actions="{item}">
                    <td v-if="item.status === CALCULATED">
                        <CButton
                                color="secondary-off"
                                shape="square"
                                size="sm"
                                @click="toResult(item.id)"
                                v-c-tooltip.hover="{ content: 'Detail' }"
                        >
                            <CIcon :height="20" name="cil-arrow-circle-right"/>
                        </CButton>
                    </td>
                    <td v-if="item.status === DRAFT">
                        <CButton
                                color="secondary-off"
                                shape="square"
                                size="sm"
                                @click="toCalculate(item.id)"
                                v-c-tooltip.hover="{ content: 'Calculate' }"
                        >
                            <CIcon height="20" name="cil-sync"/>
                        </CButton>
                    </td>
                </template>
            </CDataTable>
            <CPagination
                    v-if="totalPages > 1"
                    align="center"
                    responsive
                    :pages="totalPages"
                    :activePage.sync="currentPage"
                    @update:activePage="onChangePagination"
            />
        </CCardBody>
    </CCard>
</template>

<script>
    import Code from "../../assets/constants/Code";
    import Status from "../../assets/constants/Status";
    import Functions from '../../assets/constants/Functions';
    import moment from 'moment'
    import router from "../../router";

    export default {
        name: 'LiquecTable',
        data () {
            return {
                EUROCODE: Code.EUROCODE,
                NCSE_02: Code.NCSE_02,
                DRAFT: Status.DRAFT,
                CALCULATED: Status.CALCULATED,
                literal_eurocode: Functions.getLiteralName('code', Code.EUROCODE),
                literal_ncse_02: Functions.getLiteralName('code', Code.NCSE_02),
                currentPage: this.activePage,
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['id', 'project', 'mode', 'layers', 'spts', 'created', 'updated', 'owner', 'updatedBy', 'actions']
                }
            },
            caption: {
                type: String,
                default: 'Table'
            },
            hover: Boolean,
            striped: Boolean,
            bordered: Boolean,
            small: Boolean,
            fixed: Boolean,
            dark: Boolean,
            totalPages: Number,
            activePage: Number
        },
        methods: {
            getCreated (audit) {
                return this.formatDate(audit.createdOn)
            },
            getUpdated (audit) {
                return audit.updatedOn !== null ? this.formatDate(audit.updatedOn) : '-'
            },
            formatDate(date) {
                return moment(date).format('DD-MM-YYYY HH:mm:ss')
            },
            onChangePagination(value) {
                this.$emit('updatePagination:activePage', value)
            },
            toResult(id) {
                router.push(`/liquec/${id}`);
            },
            toCalculate(id) {
                router.push(`/liquec/create/draft/${id}`);
            }
        }
    }
</script>
