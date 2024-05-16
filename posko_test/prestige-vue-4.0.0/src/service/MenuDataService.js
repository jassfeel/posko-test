export let menu = 
    [
        { label: 'Main', i18n: 'Default.main.menu.main', icon: 'pi pi-fw pi-home', to: '/', authrity: 'CURD', ord: 1 },
        {
            label: 'Basic',
            i18n: 'Default.main.menu.basic',
            icon: 'pi pi-fw pi-wrench',
            ord: 2,
            items: [
            ],
        },
        {
            label: 'System',
            i18n: 'Default.main.menu.system',
            icon: 'pi pi-fw pi-slack',
            ord: 3,
            items: [
                { label: 'System Code', icon: 'pi pi-fw pi-list', to: '/systemCode', authrity: 'CURD', ord: 1 },
                { label: 'User', icon: 'pi pi-fw pi-users', to: '/user', authrity: 'CURD', ord: 1 },
                { label: 'Authority', icon: 'pi pi-fw pi-id-card', to: '/userAuthority', authrity: 'CURD', ord: 1 },
            ],
        },
    ];

export const menuSamples = 
    [
        { label: 'Dashboard(Samples)', i18n: 'Samples.main.menu.Dashboard', icon: 'pi pi-fw pi-home', to: '/samples', authrity: 'CURD', ord: 1 },
        {
            label: 'UI Kit(Samples)',
            i18n: 'Samples.main.menu.UiKit',
            icon: 'pi pi-fw pi-star-fill',
            ord: 2,
            items: [
                { label: 'Form Layout', icon: 'pi pi-fw pi-id-card', to: '/samples/formlayout', authrity: 'CURD', ord: 1},
                { label: 'Input', icon: 'pi pi-fw pi-check-square', to: '/samples/input', authrity: 'CURD', ord: 2 },
                { label: 'Float Label', icon: 'pi pi-fw pi-bookmark', to: '/samples/floatlabel', authrity: 'CURD', ord: 3 },
                { label: 'Invalid State', icon: 'pi pi-fw pi-exclamation-circle', to: '/samples/invalidstate', authrity: 'CURD', ord: 4 },
                { label: 'Button', icon: 'pi pi-fw pi-mobile', to: '/samples/button', class: 'rotated-icon', authrity: 'CURD', ord: 5 },
                { label: 'Table', icon: 'pi pi-fw pi-table', to: '/samples/table', authrity: 'CURD', ord: 6 },
                { label: 'List', icon: 'pi pi-fw pi-list', to: '/samples/list', authrity: 'CURD', ord: 7 },
                { label: 'Tree', icon: 'pi pi-fw pi-share-alt', to: '/samples/tree', authrity: 'CURD', ord: 8 },
                { label: 'Panel', icon: 'pi pi-fw pi-tablet', to: '/samples/panel', authrity: 'CURD', ord: 9 },
                { label: 'Overlay', icon: 'pi pi-fw pi-clone', to: '/samples/overlay', authrity: 'CURD', ord: 10 },
                { label: 'Media', icon: 'pi pi-fw pi-image', to: '/samples/media', authrity: 'CURD', ord: 11 },
                { label: 'Menu', icon: 'pi pi-fw pi-bars', to: '/samples/menu', authrity: 'CURD', ord: 12 },
                { label: 'Messages', icon: 'pi pi-fw pi-comment', to: '/samples/messages', authrity: 'CURD', ord: 13 },
                { label: 'File', icon: 'pi pi-fw pi-file', to: '/samples/file', authrity: 'CURD', ord: 14 },
                { label: 'Chart', icon: 'pi pi-fw pi-chart-bar', to: '/samples/chart', authrity: 'CURD', ord: 15 },
                { label: 'Misc', icon: 'pi pi-fw pi-circle-off', to: '/samples/misc', authrity: 'CURD', ord: 16 },
                { label: 'Misc', icon: 'pi pi-fw pi-circle-off', to: '/samples/menu/payment', authrity: 'CURD', ord: 16 },
            ],
        },
        {
            label: 'Utilities(Samples)',
            i18n: 'Samples.main.menu.Utilities',
            icon: 'pi pi-fw pi-compass',
            ord: 3,
            items: [
                { label: 'PrimeIcons', icon: 'pi pi-fw pi-prime', to: '/samples/icons', authrity: 'CURD', ord: 1 },
                { label: 'PrimeFlex', icon: 'pi pi-fw pi-directions', url: 'https://www.primefaces.org/primeflex/', target: '_blank', ord: 2 },
            ],
        },
        {
            label: 'Pages(Samples)',
            icon: 'pi pi-fw pi-briefcase',
            ord: 5,
            items: [
                { label: 'Crud', icon: 'pi pi-fw pi-pencil', to: '/samples/crud', authrity: 'CURD', ord: 1 },
                { label: 'Calendar', icon: 'pi pi-fw pi-calendar-plus', to: '/samples/calendar', authrity: 'CURD', ord: 1 },
                { label: 'Timeline', icon: 'pi pi-fw pi-calendar', to: '/samples/timeline', authrity: 'CURD', ord: 1 },
                { label: 'Landing', icon: 'pi pi-fw pi-globe', url: 'pages/landing.html', target: '_blank', authrity: 'CURD', ord: 1 },
                { label: 'Login', icon: 'pi pi-fw pi-sign-in', to: '/login', authrity: 'CURD', ord: 1 },
                { label: 'Invoice', icon: 'pi pi-fw pi-dollar', to: '/samples/invoice', authrity: 'CURD', ord: 1 },
                { label: 'Help', icon: 'pi pi-fw pi-question-circle', to: '/samples/help', authrity: 'CURD', ord: 1 },
                { label: 'Error', icon: 'pi pi-fw pi-times-circle', to: '/error' },
                { label: 'Not Found', icon: 'pi pi-fw pi-exclamation-circle', to: '/notfound', authrity: 'CURD', ord: 1 },
                { label: 'Access Denied', icon: 'pi pi-fw pi-lock', to: '/access', authrity: 'CURD', ord: 1 },
                { label: 'Empty', icon: 'pi pi-fw pi-circle-off', to: '/empty', authrity: 'CURD', ord: 1 },
            ],
        },
        {
            label: 'Hierarchy(Samples)',
            icon: 'pi pi-fw pi-align-left',
            ord: 6,
            items: [
                {
                    label: 'Submenu 1',
                    icon: 'pi pi-fw pi-align-left',
                    ord: 1,
                    items: [
                        {
                            label: 'Submenu 1.1',
                            icon: 'pi pi-fw pi-align-left',
                            ord: 1,
                            items: [
                                { 
                                    label: 'Submenu 1.1.1', 
                                    icon: 'pi pi-fw pi-align-left',
                                    authrity: 'CURD', 
                                    ord: 1
                                },
                                { label: 'Submenu 1.1.2', icon: 'pi pi-fw pi-align-left', ord: 2 },
                                { label: 'Submenu 1.1.3', icon: 'pi pi-fw pi-align-left', ord: 3 },
                            ],
                        },
                        {
                            label: 'Submenu 1.2',
                            icon: 'pi pi-fw pi-align-left',
                            ord: 2,
                            items: [
                                { label: 'Submenu 1.2.1', icon: 'pi pi-fw pi-align-left', ord: 1 },
                                { label: 'Submenu 1.2.2', icon: 'pi pi-fw pi-align-left', ord: 2 },
                            ],
                        },
                    ],
                },
                {
                    label: 'Submenu 2',
                    icon: 'pi pi-fw pi-align-left',
                    ord: 1,
                    items: [
                        {
                            label: 'Submenu 2.1',
                            icon: 'pi pi-fw pi-align-left',
                            ord: 1,
                            items: [
                                { label: 'Submenu 2.1.1', icon: 'pi pi-fw pi-align-left', ord: 1 },
                                { label: 'Submenu 2.1.2', icon: 'pi pi-fw pi-align-left', ord: 2 },
                                { label: 'Submenu 2.1.3', icon: 'pi pi-fw pi-align-left', ord: 3 },
                            ],
                        },
                        {
                            label: 'Submenu 2.2',
                            icon: 'pi pi-fw pi-align-left',
                            ord: 2,
                            items: [
                                { label: 'Submenu 2.2.1', icon: 'pi pi-fw pi-align-left', ord: 1 },
                                { label: 'Submenu 2.2.2', icon: 'pi pi-fw pi-align-left', ord: 2 },
                            ],
                        },
                    ],
                },
            ],
        },
        { label: 'Documentation(Samples)', icon: 'pi pi-fw pi-info-circle', to: '/samples/documentation', authrity: 'CURD', ord: 2 },
    ];
