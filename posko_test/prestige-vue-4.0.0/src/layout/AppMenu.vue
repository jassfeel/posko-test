<template>
    <AppSubmenu class="layout-menu clearfix" :items="applyMenu" :layoutMode="layoutMode" :menuActive="active" :root="true" :parentMenuItemActive="true" />
</template>

<script>
import AppSubmenu from './AppSubmenu.vue';
import {menu, menuSamples} from '@/service/MenuDataService';

export default {
    props: {
        layoutMode: String,
        active: Boolean,
        menuType: String,
    },
    setup(props) {
        const applyMenu = (props.menuType=='samples')?menuSamples:menu
        return {
            applyMenu
        }
    },
    watch: {
        menuType (newMenuType) {
            this.applyMenu = (newMenuType=='samples')?menuSamples:menu;
        }
    },
    components: {
        AppSubmenu: AppSubmenu,
    },
};
</script>

<style scoped></style>
