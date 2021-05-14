<template>
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
        <template #tool="{item}">
            <td>{{tools[item.tool].name}}</td>
        </template>
        <template #id="{item}">
            <td><CBadge shape="pill" color="secondary">{{item.id}}</CBadge></td>
        </template>
        <template #project="{item}">
            <td>{{item.projectId}}</td>
        </template>
        <template #status="{item}">
            <td v-if="item.status === DRAFT">Draft</td>
            <td v-if="item.status === CALCULATED">Calculated</td>
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
                        @click="goToPath(tools[item.tool].paths, item.status, item.id)"
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
                        @click="goToPath(tools[item.tool].paths, item.status, item.id)"
                        v-c-tooltip.hover="{ content: 'Calculate' }"
                >
                    <CIcon height="20" name="cil-sync"/>
                </CButton>
            </td>
        </template>
    </CDataTable>
</template>

<script>
    import Status from './../../assets/constants/status';
    import moment from 'moment';
    import router from "../../router";

    export default {
        name: 'SummaryTable',
        data () {
            return {
                DRAFT: Status.DRAFT,
                CALCULATED: Status.CALCULATED,
                tools: {
                    berock: {
                        name: 'Berock',
                        paths: [
                            {key: Status.DRAFT, value: '/berock/create/draft'},
                            {key: Status.CALCULATED, value: '/berock'}
                        ]
                    },
                    liquec: {
                        name: 'Liquec',
                        paths: [
                            {key: Status.DRAFT, value: '/liquec/create/draft'},
                            {key: Status.CALCULATED, value: '/liquec'}
                        ]
                    }
                }
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['tool', 'id', 'project', 'status', 'created', 'updated', 'owner', 'updatedBy', 'actions']
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
            goToPath(paths, status, id) {
                const path = paths.find(e => e.key === status);
                router.push(`${path.value}/${id}`);
            }
        }
    }
</script>
