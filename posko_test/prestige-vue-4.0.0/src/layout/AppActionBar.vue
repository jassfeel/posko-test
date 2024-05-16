<template>
    <div class="layout-actionbar">
        <div class="layout-breadcrumb">
            <span v-if="breadcrumb && breadcrumb[0]">
                {{ breadcrumb[0].parent }}
                <template v-for="item in breadcrumb" v-bind:key="item.id">
                    <span v-if="item.label"> <i class="pi pi-fw pi-chevron-right mr-1" style="font-size: 0.8rem"></i> </span>{{ item.label }}
                </template>
            </span>
        </div>
        <div class="layout-actions">
            <ul>
                <li>
                    <button><i class="pi pi-compass icon"></i></button>
                </li>
                <li>
                    <button><i class="pi pi-inbox icon"></i></button>
                </li>
                <li>
                    <button><i class="pi pi-file-o icon"></i></button>
                </li>
                <li>
                    <button><i class="pi pi-info-circle icon"></i></button>
                </li>
                <li>
                    <button><i class="pi pi-user icon"></i></button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import router from '../router';

    export default {
        name: 'AppActionBar',
        computed: {
            breadcrumb() {
                return router.currentRoute.value.meta.breadcrumb
            }
        },
    };
</script>

<style scoped></style>
