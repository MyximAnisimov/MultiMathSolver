import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import {Notifications} from "@kyvg/vue3-notification";

const apiClient = axios.create({
    baseURL: 'https://your-api-url.com', // Замените на URL вашего API
    headers: {
        'Content-Type': 'application/json'
    }
});

// Можно добавить перехватчики (interceptors) для обработки запросов и ответов
apiClient.interceptors.request.use(config => {
    return config;
}, error => {
    return Promise.reject(error);
});

apiClient.interceptors.response.use(response => {
    return response;
}, error => {
    console.error('API error:', error);
    return Promise.reject(error);
});

export default apiClient;
const app = createApp(App)

app.use(Notifications)
app.use(router)

app.mount('#app')
