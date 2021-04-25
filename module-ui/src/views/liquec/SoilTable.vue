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
        <template #startDepth="{item}">
            <td>{{getSign(item.startDepth)}}{{formatNumber(item.startDepth)}} m</td>
        </template>
        <template #finalDepth="{item}">
            <td>-{{formatNumber(item.finalDepth)}} m</td>
        </template>
        <template #aboveGwt="{item}">
            <td>{{formatNumber(item.aboveGwt)}} KN/m<sup>3</sup></td>
        </template>
        <template #belowGwt="{item}">
            <td>{{formatNumber(item.belowGwt)}} KN/m<sup>3</sup></td>
        </template>
        <template #finesContent="{item}">
            <td>{{formatNumber(item.finesContent)}} %</td>
        </template>
    </CDataTable>
</template>

<script>
    export default {
        name: 'SoilTable',
        data () {
            return {
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['startDepth', 'finalDepth', 'soilType', 'aboveGwt', 'belowGwt', 'finesContent', 'checkLiquefaction']
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
