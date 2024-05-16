<template>
    <div class="route-bar">
        <div class="route-bar-breadcrumb">
            <ul>
                <li>
                    <button class="p-link" @click="home"><i class="pi pi-home"></i></button>
                </li>
                <span v-if="breadcrumb && breadcrumb[0]">
                    <li>/</li>
                    <li>{{ breadcrumb[0].parent }}</li>
                    <template v-for="item in breadcrumb" v-bind:key="item.id">
                        <li>/</li>
                        <li>{{ item.label }}</li>
                    </template>
                </span>
            </ul>
            <!-- <ul>
                <li>
                    <button class="p-link" @click="home"><i class="pi pi-home"></i></button>
                </li>
                <template v-if="$route.meta.breadcrumb">
                    <li v-if="$route.meta.breadcrumb[0].parent">/</li>
                    <li v-if="$route.meta.breadcrumb[0].parent">{{ $route.meta.breadcrumb[0].parent }}</li>
                    <li>/</li>
                    <li>{{ $route.meta.breadcrumb[0].label }}</li>
                </template>
            </ul> -->
        </div>
    </div>
</template>

<script>
import router from '../router';

export default {
    methods: {
        home() {
            window.location = '/#/';
        },
    },
    computed: {
            breadcrumb() {
                return router.currentRoute.value.meta.breadcrumb
            }
        },
};
</script>

<style scoped></style>
