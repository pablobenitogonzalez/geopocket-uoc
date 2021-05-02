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
        <template #depth="{item}">
            <td>{{getSign(item.depth)}}{{formatNumber(item.depth)}} m</td>
        </template>
        <template #sptBlowCounts="{item}">
            <td>{{formatNumber(item.sptBlowCounts)}} N</td>
        </template>
        <template #energyRatio="{item}">
            <td>{{formatNumber(item.energyRatio)}} %</td>
        </template>
    </CDataTable>
</template>

<script>
    export default {
        name: 'SptTable',
        data () {
            return {
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['depth', 'sptBlowCounts', 'energyRatio']
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
            getSign(number) {
                return (number === 0)? '' : '-';
            },
            formatNumber(number) {
                return parseFloat(number).toFixed(2);
            }
        }
    }
</script>
