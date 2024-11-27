import { createRouter, createWebHistory } from 'vue-router'
import Registration from "@/components/registration/Registration.vue";
import MainPage from "@/components/main/MainPage.vue";
import RegistrationError from "@/components/registration/RegistrationError.vue";
import CholetskyMethod from "@/components/methods/CholetskyMethod.vue";
import SimpsonMethod from "@/components/methods/SimpsonMethod.vue";
import ImprovedEulerMethod from "@/components/methods/ImprovedEulerMethod.vue";
import NewtonMethod from "@/components/methods/NewtonMethod.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    {
      path: '/',
      name: 'auth',
      component: Registration,
    },
      {
          path: '/main',
          name: 'main',
          component: MainPage,
          beforeEnter: (to, from, next) => {
              if (localStorage.getItem("jwt")) next();
              else next({
                  name: 'error-page-app',
              });
          }
      },
      {
          path: '/choletsky',
          name: 'choletsky',
          component: CholetskyMethod,
          beforeEnter: (to, from, next) => {
              if (localStorage.getItem("jwt")) next();
              else next({
                  name: 'error-page-app',
              });
          }
      },
      {
          path: '/newton',
          name: 'newton',
          component: NewtonMethod,
          beforeEnter: (to, from, next) => {
              if (localStorage.getItem("jwt")) next();
              else next({
                  name: 'error-page-app',
              });
          }
      },
      {
          path: '/simpson',
          name: 'simpson',
          component: SimpsonMethod,
          beforeEnter: (to, from, next) => {
              if (localStorage.getItem("jwt")) next();
              else next({
                  name: 'error-page-app',
              });
          }
      },
      {
          path: '/euler',
          name: 'euler',
          component: ImprovedEulerMethod,
          beforeEnter: (to, from, next) => {
              if (localStorage.getItem("jwt")) next();
              else next({
                  name: 'error-page-app',
              });
          }
      },
      {
          path: '/error-page-app',
          name: 'error-page-app',
          component: RegistrationError,
          props: {
              default: true,
              errorCode: "401",
              errorMessage: "У вас нет доступа к приложению. Сначала авторизуйтесь!"
          }
      }
  ],
})

export default router
