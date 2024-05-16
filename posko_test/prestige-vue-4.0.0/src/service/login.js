import axios from './axios';
import VueCookies from 'vue-cookies';

// loginInfo : {username: '', password: ''}
export async function loginForm(loginInfo, store){
    
    try{    
        const response = await axios.post('/api/authenticate', loginInfo);

        console.log('response => ');
        console.log(response);

        VueCookies.set('token', response.data.token, '60s' );
        VueCookies.set('refresh_token', response.data.refreshToken, '1d' );
        // axios.defaults.headers['Authorization'] = "Bearer " + VueCookies.get('token');
        // axios.defaults.headers['REFRESH_TOKEN'] = VueCookies.get('refresh_token');
        console.log("token=" + VueCookies.get('token'));
        console.log("refresh_token=" + VueCookies.get('refresh_token'));

        console.log('response.status = ' + response.status);
        if (response.status == '200') {
            console.log('store = ' + store);
            store.commit("setLogined", true);            
            store.commit("setCompanyName", "(주)대유씨엔에이");            
            store.commit("setLoginId", loginInfo.username);            
        }
        return response.status;
    }catch(err){        
        return err.message;
    }
}

export async function refreshToken(){
    try{
        const response = await axios.post('/api/refreshtoken');
        VueCookies.set('token', response.data.token, '60s' );
        // axios.defaults.headers['Authorization'] = "Bearer " + VueCookies.get('token');

        return response.data.token;
    }catch(err){
        return err;
    }
}

export async function logoutForm(store){    
    VueCookies.remove('token');
    VueCookies.remove('refresh_token');
    console.log("token=" + VueCookies.get('token'));
    console.log("refresh_token=" + VueCookies.get('refresh_token'));

    store.commit("setLogined", false);
    store.commit("setCompanyName", null);            
    store.commit("setLoginId", null);            

    console.log("Logout Completed");
}

