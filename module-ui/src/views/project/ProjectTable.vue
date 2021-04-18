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
                <template #created="{item}">
                    <td>{{getCreated(item.audit)}}</td>
                </template>
                <template #updated="{item}">
                    <td>{{getUpdated(item.audit)}}</td>
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
    import moment from 'moment'

    export default {
        name: 'ProjectTable',
        data () {
            return {
                currentPage: this.activePage,
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['id', 'name', 'location', 'organization', 'user', 'created', 'updated']
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
                return moment(date).format('DD-MM-YYYY')
            },
            onChangePagination(value) {
                this.$emit('updatePagination:activePage', value)
            }
        }
    }
</script>