import { Pool } from 'app/pool/pool';

// TODO delete when endpoint is ready
export const POOLS_MOCK_NEW: Pool[] = [
    {
        poolId: 's7n-girls',
        displayName: 'Green Lights 4 Girls (Win7)',
        maximumCount: 2,
        enabled: false,
        description: [
            {
                id: 1,
                name: 'View Agent',
                version: '6.2.2'
            },
            {
                id: 2,
                name: 'Firefox',
                version: '55.0.3'
            },
            {
                id: 3,
                name: 'Chrome',
                version: '61.0'
            },
            {
                id: 4,
                name: 'Flash',
                version: '27.0.0'
            },
            {
                id: 5,
                name: 'Libre Office',
                version: '5.4.1.2'
            },
            {
                id: 6,
                name: 'Adobe Reader DC',
                version: '17.012'
            },
            {
                id: 7,
                name: 'JRE',
                version: '8u144'
            }
        ]
    },
    {
        poolId: 's7n-gram',
        displayName: 'Gramatyki grafowe(Win7)',
        maximumCount: 1,
        enabled: false,
        description: [
            {
                id: 1,
                name: 'View Agent',
                version: '6.2.2'
            },
            {
                id: 2,
                name: 'Firefox',
                version: '55.0.3'
            },
            {
                id: 11,
                name: 'Chrome',
                version: '62.0'
            },
            {
                id: 8,
                name: 'IE',
                version: '10'
            },
            {
                id: 9,
                name: 'Flash',
                version: '21.0.0'
            },
            {
                id: 10,
                name: 'Adobe Reader DC',
                version: '15.010'
            },
            {
                id: 5,
                name: 'Libre Office',
                version: '5.4.1.2'
            },
            {
                id: 7,
                name: 'JRE',
                version: '8u144'
            }
        ]
    }
];
