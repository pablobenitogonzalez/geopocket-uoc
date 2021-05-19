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
        <template #spacing="{item}">
            <td>{{formatNumber(item.spacing)}} m</td>
        </template>
        <template #family="{item}">
            <td v-if="!!item.family">{{item.family}}</td>
            <td v-if="!item.family">-</td>
        </template>
    </CDataTable>
</template>

<script>
    export default {
        name: 'JointTable',
        data () {
            return {
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['spacing', 'family']
                }
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
            formatNumber(number) {
                return parseFloat(number).toFixed(2);
            }
        }
    }
</script>
