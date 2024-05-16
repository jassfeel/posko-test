<template>
    <div class="layout-topbar">
        <div class="layout-topbar-logo" @click="$router.push({ path: '/' })">
            <img class="logo" src="/layout/images/logo.png" alt="" />
        </div>

        <a class="layout-menu-button" href="#" @click="onMenuButtonClick">
            <i class="pi pi-bars"></i>
        </a>

        <ul class="layout-topbar-menu">
            <li :class="[{ 'active-topmenuitem': activeTopbarItem === 'settings' }]" class="layout-topbar-item" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'settings' })">
                <a class="layout-topbar-icon first">
                    <i class="topbar-icon pi pi-cog"></i>
                </a>
                <transition name="layout-submenu-container">
                    <ul class="fadeInDown" v-show="activeTopbarItem === 'settings'">
                        <li>
                            <a href="#" class="layout-topbar-icon" @click="togeulSamples">
                                <i class="topbar-icon pi pi-gift"></i>
                            </a>
                            <div class="layout-quickmenu-tooltip">
                                <div class="layout-quickmenu-tooltip-arrow"></div>
                                <div class="layout-quickmenu-tooltip-text">{{ sampleTitle }}</div>
                            </div>
                        </li>
                    </ul>
                </transition>
            </li>

            <li :class="[{ 'active-topmenuitem': activeTopbarItem === 'language' }]" class="layout-topbar-item" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'language' })">
                <a class="layout-topbar-icon first">
                    <i class="topbar-icon pi pi-language"></i>
                </a>
                <transition name="layout-submenu-container">
                    <ul class="fadeInDown" v-show="activeTopbarItem === 'language'">
                        <li>
                            <a href="#" class="layout-topbar-text" @click="changing('ko')">Ko</a>
                        </li>
                        <li>
                            <a href="#" class="layout-topbar-text" @click="changing('en')">En</a>
                        </li>
                    </ul>
                </transition>
            </li>

            <li :class="['topbar-search', { 'active-topmenuitem': activeTopbarItem === 'search' }]" class="layout-topbar-item" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'search' })">
                <a href="#" class="layout-topbar-icon first">
                    <i class="topbar-icon pi pi-search"></i>
                </a>
                <input type="text" placeholder="Type to search..." />
            </li>

            <li :class="[{ 'active-topmenuitem': activeTopbarItem === 'profile' }]" class="user-profile" @click="$emit('topbar-item-click', { originalEvent: $event, item: 'profile' })">
                <a href="#">
                    <div class="user-profile-info profile-link">
                        <span class="user-profile-name">{{ store.getters.getLoginId }}</span>
                        <span class="user-profile-role">{{ store.getters.getCompanyName }}</span>
                    </div>
                    <i class="pi pi-android" style="font-size: 3.5rem"></i>
                </a>
                <transition name="layout-submenu-container">
                    <ul class="fadeInDown" v-show="activeTopbarItem === 'profile'">
                        <li>
                            <router-link to="/logout">                                
                                <a href="#" class="profile-detail-icon">
                                    <i class="pi pi-sign-out"></i>
                                    <div class="menu-text">
                                        <span class="menu-title">Logout</span>
                                        <span class="menu-subtitle">Logout from current User</span>
                                    </div>
                                </a>
                            </router-link>
                        </li>
                        <li>
                            <a href="#">
                                <i class="pi pi-user"></i>
                                <div class="menu-text">
                                    <span class="menu-title">Profile</span>
                                    <span class="menu-subtitle">Edit your profile</span>
                                </div>

                                <i class="right-icon pi pi-angle-right"></i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="pi pi-cog"></i>
                                <div class="menu-text">
                                    <span class="menu-title">Settings</span>
                                    <span class="menu-subtitle">Dashboard Settings</span>
                                </div>
                                <i class="right-icon pi pi-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                </transition>
            </li>
        </ul>
    </div>
</template>

<script setup>
    import { ref } from "vue";
    import { useI18n } from 'vue-i18n';
    import { useStore } from "vuex";
    import router from '../router';

    const props = defineProps({
        topbarMenuActive: Boolean,
        activeTopbarItem: String,
    });

    const { locale } = useI18n();

    const changing = (la)=>{
        console.log(la);
        locale.value = la;
    }

    const onMenuButtonClick = (event)=> {
        this.$emit('menubutton-click', event);
    }

    const store = useStore();

    const sampleTitle = ref(!store.state.samplePages?'Go Sample Pages':'Go Normal Pages');
    const togeulSamples = (event) => {
        let blnSamples = !store.state.samplePages;
        store.commit("setSamplePages", blnSamples); 
        sampleTitle.value = !store.state.samplePages?'Go Sample Pages':'Go Normal Pages';
    }

</script>
