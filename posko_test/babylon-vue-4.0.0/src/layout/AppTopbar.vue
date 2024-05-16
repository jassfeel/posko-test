<template>
    <div class="layout-topbar clearfix">
        <button class="layout-topbar-logo p-link" @click="goDashboard">
            <img id="layout-topbar-logo" alt="babylon-layout" src="/layout/images/logo-white.png" />
        </button>

        <button class="layout-menu-button p-link" @click="onMenuButtonClick">
            <i class="pi pi-bars"></i>
        </button>

        <button id="topbar-menu-button" class="p-link" @click="onTopbarMenuButtonClick">
            <i class="pi pi-ellipsis-v"></i>
        </button>

        <ul :class="topbarItemsClass">
            <li v-if="profileMode === 'popup' || horizontal" :class="['user-profile', { 'active-topmenuitem': activeTopbarItem === 'profile' }]" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'profile' })">
                <button class="p-link">
                    <img src="/layout/images/avatar.png" alt="babylon-layout" />
                    <span class="topbar-item-name">Arlene Welch</span>
                </button>

                <transition name="layout-submenu-container">
                    <ul class="fadeInDown" v-show="activeTopbarItem === 'profile'">
                        <li role="menuitem">
                            <button class="p-link">
                                <i class="pi pi-user"></i>
                                <span>Profile</span>
                            </button>
                        </li>
                        <li role="menuitem">
                            <button class="p-link"  @click="logout">
                                <i class="pi pi-sign-out"></i>
                                <span>Logout</span>
                            </button>
                        </li>
                        <li role="menuitem">
                            <button class="p-link">
                                <i class="pi pi-envelope"></i>
                                <span>Message</span>
                            </button>
                        </li>
                        <li role="menuitem">
                            <button class="p-link"  @click="togeulSamples">
                                <i class="pi pi-bell"></i>
                                <span>{{ sampleTitle }}</span>
                            </button>
                        </li>
                    </ul>
                </transition>
            </li>

            <li :class="[{ 'active-topmenuitem': activeTopbarItem === 'settings' }]" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'settings' })">
                <button class="p-link">
                    <i class="topbar-icon pi pi-cog"></i>
                    <span class="topbar-item-name">Setting</span>
                </button>

                <transition name="layout-submenu-container">
                    <ul class="fadeInDown" v-show="activeTopbarItem === 'settings'">
                        <li role="menuitem">
                            <button class="p-link" @click="changing('ko')">
                                <i class="pi pi-calendar-plus"></i>
                                <span>Language(Ko)</span>
                            </button>
                            <button class="p-link" @click="changing('en')">
                                <i class="pi pi-calendar-plus"></i>
                                <span>Language(En)</span>
                            </button>
                        </li>
                    </ul>
                </transition>
            </li>

        </ul>
    </div>
</template>

<script>
import { ref } from "vue";
import { useI18n } from 'vue-i18n';
import { useStore } from "vuex";
import router from '../router';

export default {
    props: {
        topbarMenuActive: Boolean,
        activeTopbarItem: String,
        profileMode: String,
        horizontal: Boolean,
    },
    setup() {
        const { locale } = useI18n();
        const store = useStore();

        return { 
            locale, 
            store 
        };
    },  
    methods: {
        onMenuButtonClick(event) {
            this.$emit('menubutton-click', event);
        },
        onTopbarMenuButtonClick(event) {
            this.$emit('topbar-menubutton-click', event);
        },
        goDashboard() {
            window.location = '/#/';
        },
        changing(la) {
            console.log(la);
            locale.value = la;
        },
        togeulSamples(event) {
            let blnSamples = !this.store.state.samplePages;
            this.store.commit("setSamplePages", blnSamples); 
        },
        logout(event) {
            router.push({
                path: "/logout",
            });
        }
    },
    computed: {
        topbarItemsClass() {
            return [
                'topbar-menu fadeInDown',
                {
                    'topbar-menu-visible': this.topbarMenuActive,
                },
            ];
        },
        sampleTitle() {
            return !this.store.state.samplePages?'Go Sample Pages':'Go Normal Pages';
        } 
    },
};
</script>