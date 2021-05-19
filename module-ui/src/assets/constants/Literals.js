import Code from './Code';
import Result from './Result';
import RockType from './RockType';
import Status from './Status';
import WeatheringDegree from './WeatheringDegree';

export default Object.freeze({
    code: [
        { key: Code.EUROCODE, name: 'Eurocode', shortName: '', metadata: []},
        { key: Code.NCSE_02, name: 'NCSE-02', shortName: '', metadata: []},
    ],
    result: [
        { key: Result.OK, name: 'ok', shortName: '', metadata: []},
        { key: Result.SKIP, name: 'skip', shortName: '', metadata: []},
        { key: Result.ERROR, name: 'error', shortName: '', metadata: []},
    ],
    status: [
        { key: Status.DRAFT, name: 'Draft', shortName: '', metadata: []},
        { key: Status.CALCULATED, name: 'Calculated', shortName: '', metadata: []},
    ],
    rockType: [
        {
            key: RockType.GROUP_1,
            name: 'Group I (carbonate rock, well structured)',
            shortName: 'Group I',
            metadata: [
                'Limestone, dolostone',
                'Pure marbles',
                'Low porosity calcarenite'
            ]
        },
        {
            key: RockType.GROUP_2,
            name: 'Group II (igneous and metamorphic rocks)',
            shortName: 'Group II',
            metadata: [
                'Granite, cuarcite',
                'Andesite, rhyolite',
                'Slate, schist and gneiss (horizontal schistosity)'
            ]
        },
        {
            key: RockType.GROUP_3,
            name: 'Group III (sedimentary and some metamorphic rocks)',
            shortName: 'Group III',
            metadata: [
                'Marly limestone, claystone, siltstone, sandstone and conglomerate',
                'Slate and schist (vertical schistosity)',
                'Gypsum rock'
            ]
        },
        {
            key: RockType.GROUP_4,
            name: 'Group IV (other rocks)',
            shortName: 'Group IV',
            metadata: [
                'Sandstone, siltstone',
                'Conglomerate slightly cemented',
                'Marls'
            ]
        },
    ],
    weatheringDegree: [
        { key: WeatheringDegree.DEGREE_1, name: 'Degree I (unweathered rock)', shortName: 'Degree I', metadata: []},
        { key: WeatheringDegree.DEGREE_2, name: 'Degree II (slightly weathered rock)', shortName: 'Degree II', metadata: []},
        { key: WeatheringDegree.DEGREE_3, name: 'Degree III (moderately weathered rock)', shortName: 'Degree III', metadata: []}
    ]
})