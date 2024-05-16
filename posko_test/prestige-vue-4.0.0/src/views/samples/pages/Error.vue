<template>
    <div class="exception-body error">
        <div class="mobile-banner">
            <img src="/layout/images/logo-mobile.svg" alt="prestige-layout" />
        </div>
        <div class="exception-panel">
            <div class="logo">
                <img src="/layout/images/logo.svg" alt="prestige-layout" />
            </div>
        </div>
        <div class="exception-card">
            <div class="top-border"></div>
            <div class="exception-content">
                <h1>Error Occured</h1>
                <p>Something went wrong.</p>
                <router-link to="/">
                    <Button label="Go To Dashboard" @click="goDashboard" />
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        goDashboard() {
            window.location = '/#/';
        },
    },
};
</script>

<style scoped></style>
