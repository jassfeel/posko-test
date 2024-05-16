<template>
    <div :class="containerClass" @click="onDocumentClick">
        <div class="layout-content-wrapper">
            <AppTopBar
                :topbarMenuActive="topbarMenuActive"
                :horizontal="layoutMode === 'horizontal'"
                :activeTopbarItem="activeTopbarItem"
                @menubutton-click="onMenuButtonClick"
                @topbar-menubutton-click="onTopbarMenuButtonClick"
                @topbar-item-click="onTopbarItemClick"
            ></AppTopBar>

            <div class="layout-menu-container" @click="onMenuClick">
                <div class="overlay-menu-button" @click="onMenuButtonClick">
                    <div class="overlay-menu-button-bars">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <div class="overlay-menu-button-times">
                        <span></span>
                        <span></span>
                    </div>
                </div>

                <div class="layout-menu-wrapper fadeInDown">
                    <AppMenu :layoutMode="layoutMode" :active="menuActive" :menuType="menuType" @menuitem-click="onMenuItemClick" @root-menuitem-click="onRootMenuItemClick"></AppMenu>
                </div>
            </div>

            <AppActionBar att="menuType"/>

            <div class="layout-content">
                <router-view />
            </div>

            <AppConfig
                :theme="theme"
                :layout="layout"
                :configActive="configActive"
                @theme-change="onThemeChange"
                @layout-change="onLayoutChange"
                :layoutMode="layoutMode"
                :wrapperMode="wrapperMode"
                @layout-mode-change="onLayoutModeChange"
                @config-click="onConfigClick"
                @config-button-click="onConfigButtonClick"
            ></AppConfig>

            <AppFooter />

            <div class="layout-mask"></div>
        </div>
    </div>
</template>

<script>
import AppTopBar from './AppTopbar.vue';
import AppConfig from './AppConfig.vue';
import AppMenu from './AppMenu.vue';
import AppActionBar from './AppActionBar.vue';
import AppFooter from './AppFooter.vue';
import EventBus from './event-bus';
import { useStore } from "vuex";
import router from '../router';

export default {
    setup() {
        const store = useStore();

        if (store.getters.getSamplePages) {
            router.push('/samples');
        } else {
            router.push('/');
        }

        return {
            store
        }
    },
    data() {
        return {
            theme: 'blue',
            layout: 'layout-blue',
            layoutMode: 'horizontal',
            wrapperMode: false,
            overlayMenuActive: false,
            mobileMenuActive: false,
            topbarMenuActive: false,
            activeTopbarItem: null,
            menuActive: false,
            configActive: false,
            configClick: false,
            windowWidth: 0
        };
    },
    watch: {
        $route() {
            this.menuActive = false;
            this.$toast.removeAllGroups();
        },
        menuType(val, oldVal) {
            console.log(this.store.getters.getSamplePages);
            if (this.store.getters.getSamplePages) {
                router.push('/samples');
            } else {
                router.push('/');
            }
        }
    },
    mounted() {
        this.$nextTick(function () {
            window.addEventListener('resize', this.getWindowWidth);

            this.getWindowWidth();
        });
    },
    methods: {
        getWindowWidth() {
            this.windowWidth = document.documentElement.clientWidth;
            if (this.windowWidth < 991) {
                this.$appState.fullWidth = true;
            } else {
                this.$appState.fullWidth = false;
            }
        },
        onDocumentClick() {
            if (!this.topbarItemClick) {
                this.activeTopbarItem = null;
                this.topbarMenuActive = false;
            }

            if (!this.menuClick) {
                if (this.isHorizontal()) {
                    this.menuActive = false;
                    EventBus.emit('reset-active-index');
                }

                this.hideOverlayMenu();
            }

            if (this.configActive && !this.configClick) {
                this.configActive = false;
            }

            this.topbarItemClick = false;
            this.menuClick = false;
            this.configClick = false;
        },
        onProfileClick(event) {
            this.profileExpanded = !this.profileExpanded;
            if (this.isHorizontal()) {
                EventBus.emit('reset-active-index');
            }

            event.preventDefault();
        },
        onMenuClick() {
            this.menuClick = true;
        },
        onMenuItemClick(event) {
            if (!event.item.items) {
                EventBus.emit('reset-active-index');
                this.hideOverlayMenu();

                if (this.isHorizontal()) {
                    this.menuActive = false;
                }
            }
        },
        onRootMenuItemClick() {
            this.menuActive = !this.menuActive;
        },
        onMenuButtonClick(event) {
            this.menuClick = true;

            this.topbarMenuActive = false;
            if (this.isDesktop()) {
                if (this.layoutMode === 'overlay') {
                    this.overlayMenuActive = !this.overlayMenuActive;
                }
            } else {
                this.mobileMenuActive = !this.mobileMenuActive;
            }

            event.preventDefault();
        },
        onTopbarMenuButtonClick(event) {
            this.topbarItemClick = true;
            this.topbarMenuActive = !this.topbarMenuActive;
            this.hideOverlayMenu();
            event.preventDefault();
        },
        onTopbarItemClick(event) {
            this.topbarItemClick = true;

            if (this.activeTopbarItem === event.item) this.activeTopbarItem = null;
            else this.activeTopbarItem = event.item;

            event.originalEvent.preventDefault();
        },
        hideOverlayMenu() {
            this.overlayMenuActive = false;
            this.mobileMenuActive = false;
        },
        isDesktop() {
            return window.innerWidth > 1024;
        },
        isHorizontal() {
            return this.layoutMode === 'horizontal';
        },
        onLayoutModeChange(layoutMode) {
            this.layoutMode = layoutMode;
            this.staticMenuDesktopInactive = false;
            this.overlayMenuActive = false;
        },
        onThemeChange(theme) {
            this.theme = theme;
        },
        onLayoutChange(layout) {
            this.layout = layout;
        },
        onConfigClick() {
            this.configClick = true;
        },
        onConfigButtonClick() {
            this.configActive = !this.configActive;
            this.configClick = true;
        },
        isMobile() {
            return window.innerWidth <= 991;
        },
    },
    computed: {
        containerClass() {
            return [
                'layout-wrapper',
                {
                    'layout-menu-overlay': this.layoutMode === 'overlay',
                    'layout-menu-overlay-active': this.overlayMenuActive,
                    'layout-mobile-active': this.mobileMenuActive,
                    'layout-menu-horizontal': this.layoutMode === 'horizontal',
                    'p-input-filled': this.$primevue.config.inputStyle === 'filled',
                    'p-ripple-disabled': this.$primevue.config.ripple === false,
                },
                {
                    'layout-fullwidth': this.$appState.fullWidth,
                },
            ];
        },
        menuType() {
            return this.store.getters.getSamplePages?'samples':'normal'
        }
    },
    components: {
        AppTopBar: AppTopBar,
        AppConfig: AppConfig,
        AppMenu: AppMenu,
        AppFooter: AppFooter,
        AppActionBar: AppActionBar,
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.getWindowWidth);
    },
};
</script>
