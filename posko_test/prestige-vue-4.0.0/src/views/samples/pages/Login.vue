<template>
    <body class="login-body">
        <div class="mobile-banner">
            <img src="/layout/images/logo-mobile.svg" alt="prestige-layout" />
        </div>
        <div class="login-panel">
            <div class="logo">
                <img src="/layout/images/logo.svg" alt="prestige-layout" />
            </div>
        </div>
        <div class="login-card">
            <div class="top-border"></div>
            <div class="login-content">
                <h1>Login</h1>
                <p>Welcome, please use the form to sign-in.</p>
                <div class="username-container">
                    <label>Username</label>
                    <div class="login-input">
                        <InputText placeholder="Username" v-model="username" @keypress="onkeypress"  />
                    </div>
                </div>
                <div class="password-container">
                    <label>Password</label>
                    <div class="login-input">
                        <InputText type="password" placeholder="Password" v-model="password" @keypress="onkeypress" />
                    </div>
                </div>
                <div class="forgetpassword-container">
                    <a href="#" class="forget-password">Forget Password?</a>
                </div>
                <div>
                    <Button label="Sign In" @click="formLogin" />
                </div>
            </div>
        </div>
    </body>
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
