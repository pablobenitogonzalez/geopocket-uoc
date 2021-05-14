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
        <template #SPT="{item}">
            <td>{{formatNumber(item.sptBlowCounts)}} N</td>
        </template>
        <template #SPT(N60)="{item}">
            <td>{{formatNumber(item.sptResult.sptCorrected)}} N<sub>(60)</sub></td>
        </template>
        <template #CSR="{item}">
            <td>{{formatNumber(item.sptResult.cycleStressRatio)}}</td>
        </template>
        <template #CRR="{item}">
            <td>{{formatNumber(item.sptResult.cycleResistanceRatio)}}</td>
        </template>
        <template #safetyFactor="{item}">
            <td>{{getSafetyFactor(item.sptResult)}}</td>
        </template>
    </CDataTable>
</template>

<script>
    import Result from "../../assets/constants/result";

    export default {
        name: 'ResultTable',
        data () {
            return {
            }
        },
        props: {
            items: Array,
            fields: {
                type: Array,
                default () {
                    return ['depth', 'SPT', 'SPT(N60)', 'CSR', 'CRR', 'safetyFactor']
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
                if (number == null || number === '') return '-';
                return parseFloat(number).toFixed(2);
            },
            getSafetyFactor(sptResult) {
                if (sptResult.result === Result.ERROR || sptResult.result === Result.SKIP) return sptResult.message;
                return this.formatNumber(sptResult.safetyFactor);
            }
        }
    }
</script>
