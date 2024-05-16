<template>
    <div class="login-body">
        <div class="login-panel"></div>

        <div class="login-content">
            <img src="/layout/images/logo-black.png" alt="babylon-layout" />

            <h1><span>SIGN IN</span> TO SYSTEM</h1>
            <p>Welcome, please use the form to sign-in.</p>

            <div class="login-input-wrapper">
                <InputText placeholder="Username" v-model="username" @keypress="onkeypress"  />
                <i class="pi pi-user"></i>
            </div>

            <div class="login-input-wrapper">
                <InputText type="password" placeholder="Password" v-model="password" @keypress="onkeypress" />
                <i class="pi pi-lock"></i>
            </div>

            <Button label="Sign In" @click="formLogin" />
        </div>
    </div>
</template>

<script>
import { loginForm, logoutForm } from '../../service/login'
import { useStore } from "vuex";

export default {
    setup() {
        const store = useStore();
        return { store };
    },    
    data() {
        return {
            username: '',
            password: '',
        };
    },
    methods: {
        onkeypress(event) {
            if (event.keyCode == 13) {
                this.formLogin(event);
            }
        },
        async formLogin() {

        const result = await loginForm(
            {
            username: this.username,
            password: this.password
            },
            this.store
        );

        console.log('login=' + result);
        if (result == '200') {
            await this.$router.push('/');
        } else {
            logoutForm(this.store);
            await this.$router.push('/login');
        }
    }
  },
};
</script>

<style scoped></style>
