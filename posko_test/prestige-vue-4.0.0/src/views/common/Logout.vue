<template></template>

<script>
import { logoutForm } from '../../service/login'
import { useStore } from "vuex";
import router from "../../router";

export default {
  setup() {
    const store = useStore();
    logoutForm(store);
    router.push('/login');
  }
};
</script>