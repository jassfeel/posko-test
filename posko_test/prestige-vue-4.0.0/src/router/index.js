import { createRouter, createWebHashHistory } from 'vue-router';
import VueCookies from 'vue-cookies';
import { refreshToken } from '../service/login';
import AppLayout from '@/layout/AppLayout.vue';
const routes = [
    {
        path: '/',
        component: AppLayout,
        children: [
            {
                path: '/',
                name: 'main',
                exact: true,
                component: () => import('@/views/Main.vue'),
                meta: {
                    breadcrumb: [   
                        { parent: 'Home', label: 'Main' },
                    ],
                },
            },
            {
                path: '/systemCode',
                name: 'systemCode',
                component: () => import('@/views/system/SystemCode.vue'),
                meta: {
                    breadcrumb: [{ parent: 'System', label: 'System Code' }],
                },
            },
            {
                path: '/user',
                name: 'user',
                component: () => import('@/views/system/User.vue'),
                meta: {
                    breadcrumb: [{ parent: 'System', label: 'User' }],
                },
            },
            {
                path: '/userAuthority',
                name: 'userAuthority',
                component: () => import('@/views/system/UserAuthority.vue'),
                meta: {
                    breadcrumb: [{ parent: 'System', label: 'User Authority' }],
                },
            },
        ],
    },
    {
        path: '/samples',
        component: () => import('@/layout/AppLayout.vue'),
        children: [
            {
                path: '/samples',
                name: 'samplesdashboard',
                exact: true,
                component: () => import('@/views/samples/Dashboard.vue'),
                meta: {
                    breadcrumb: [   
                        { parent: 'Dashboard(Samples)', label: 'Sales Dashboard' },
                    ],
                },
            },
            {
                path: '/samples/formlayout',
                name: 'samplesformlayout',
                component: () => import('@/views/samples/uikit/FormLayoutDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Form Layout' }],
                },
            },
            {
                path: '/samples/invalidstate',
                name: 'samplesinvalidstate',
                component: () => import('@/views/samples/uikit/InvalidStateDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Invalid State' }],
                },
            },
            {
                path: '/samples/input',
                name: 'samplesinput',
                component: () => import('@/views/samples/uikit/InputDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Input' }],
                },
            },
            {
                path: '/samples/floatlabel',
                name: 'samplesfloatlabel',
                component: () => import('@/views/samples/uikit/FloatLabelDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Float Label' }],
                },
            },
            {
                path: '/samples/button',
                name: 'samplesbutton',
                component: () => import('@/views/samples/uikit/ButtonDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Button' }],
                },
            },
            {
                path: '/samples/table',
                name: 'samplestable',
                component: () => import('@/views/samples/uikit/TableDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Table' }],
                },
            },
            {
                path: '/samples/list',
                name: 'sampleslist',
                component: () => import('@/views/samples/uikit/ListDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'List' }],
                },
            },
            {
                path: '/samples/timeline',
                name: 'samplestimeline',
                component: () => import('@/views/samples/pages/TimelineDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Timeline' }],
                },
            },
            {
                path: '/samples/tree',
                name: 'samplestree',
                component: () => import('@/views/samples/uikit/TreeDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Tree' }],
                },
            },
            {
                path: '/samples/panel',
                name: 'samplespanel',
                component: () => import('@/views/samples/uikit/PanelsDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Panel' }],
                },
            },
            {
                path: '/samples/overlay',
                name: 'samplesoverlay',
                component: () => import('@/views/samples/uikit/OverlayDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Overlay' }],
                },
            },
            {
                path: '/samples/media',
                name: 'samplesmedia',
                component: () => import('@/views/samples/uikit/MediaDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Media' }],
                },
            },
            {
                path: '/samples/menu',
                component: () => import('@/views/samples/uikit/MenuDemo.vue'),
                children: [
                    {
                        path: '',
                        component: () => import('@/views/samples/uikit/menu/PersonalDemo.vue'),
                        meta: {
                            breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Menu' }],
                        },
                    },
                    {
                        path: '/samples/menu/seat',
                        component: () => import('@/views/samples/uikit/menu/SeatDemo.vue'),
                        meta: {
                            breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Menu' }],
                        },
                    },
                    {
                        path: '/samples/menu/payment',
                        component: () => import('@/views/samples/uikit/menu/PaymentDemo.vue'),
                        meta: {
                            breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'PaymentDemo' }],
                        },
                    },
                    {
                        path: '/samples/menu/confirmation',
                        component: () => import('@/views/samples/uikit/menu/ConfirmationDemo.vue'),
                        meta: {
                            breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Menu' }],
                        },
                    },
                ],
            },
            {
                path: '/samples/messages',
                name: 'samplesmessages',
                component: () => import('@/views/samples/uikit/MessagesDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Messages' }],
                },
            },
            {
                path: '/samples/file',
                name: 'samplesfile',
                component: () => import('@/views/samples/uikit/FileDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'File' }],
                },
            },
            {
                path: '/samples/chart',
                name: 'sampleschart',
                component: () => import('@/views/samples/uikit/ChartDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Charts' }],
                },
            },
            {
                path: '/samples/misc',
                name: 'samplesmisc',
                component: () => import('@/views/samples/uikit/MiscDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'UI Kit(Samples)', label: 'Misc' }],
                },
            },
            {
                path: '/samples/icons',
                name: 'samplesicons',
                component: () => import('@/views/samples/utilities/Icons.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Utilities(Samples)', label: 'Icons' }],
                },
            },
            {
                path: '/samples/crud',
                name: 'samplescrud',
                component: () => import('@/views/samples/pages/CrudDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Crud' }],
                },
            },
            {
                path: '/samples/calendar',
                name: 'samplescalendar',
                component: () => import('@/views/samples/pages/CalendarDemo.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Calendar' }],
                },
            },
            {
                path: '/samples/invoice',
                name: 'samplesinvoice',
                component: () => import('@/views/samples/pages/Invoice.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Invoice' }],
                },
            },
            {
                path: '/samples/help',
                name: 'sampleshelp',
                component: () => import('@/views/samples/pages/Help.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Help' }],
                },
            },
            {
                path: '/samples/empty',
                name: 'samplesempty',
                component: () => import('@/views/samples/pages/EmptyPage.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Pages(Samples)', label: 'Empty Page' }],
                },
            },
            {
                path: '/samples/documentation',
                name: 'samplesdocumentation',
                component: () => import('@/views/samples/utilities/Documentation.vue'),
                meta: {
                    breadcrumb: [{ parent: 'Start(Samples)', label: 'Documentation' }],
                },
            },
        ],
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/common/Login.vue'),
        meta: { unauthorized : true },
    },
    {
        path: '/logout',
        name: 'logout',
        component: () => import('@/views/common/Logout.vue'),
        meta: { unauthorized : true },
    },
    {
        path: '/error',
        name: 'error',
        component: () => import('@/views/common/Error.vue'),
    },
    {
        path: '/notfound',
        name: 'notfound',
        component: () => import('@/views/common/NotFound.vue'),
    },
    {
        path: '/access',
        name: 'access',
        component: () => import('@/views/common/Access.vue'),
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
    scrollBehavior() {
        return { left: 0, top: 0 };
    },
});

router.beforeEach( async(to, from, next) => {

    console.log('token=' + JSON.stringify(VueCookies.get('token')));
    console.log('refresh_token=' + JSON.stringify(VueCookies.get('refresh_token')));
    if (to.name === 'login') {
      VueCookies.remove('token');
      VueCookies.remove('refresh_token');
    }
  
    if(VueCookies.get('token')===null && VueCookies.get('refresh_token') !== null){
      console.log('refreshToken');
      await refreshToken();
    }
  
    console.log('token after refresh=' + JSON.stringify(VueCookies.get('token')));
    console.log(
      'if = ' +
        (
        to.matched.some(record => record.meta.unauthorized) 
        || (!to.matched.some(record => record.meta.unauthorized) && from.name === 'login')
        || VueCookies.get('token')
        )
    );
  
    if (
        to.matched.some(record => record.meta.unauthorized) 
        || (!to.matched.some(record => record.meta.unauthorized) && from.name === 'login')
        || VueCookies.get('token')
    ){
      console.log('next');
      return next();
    }
  
    alert('로그인 해주세요');
    return next('/login');
  })
  
export default router;
