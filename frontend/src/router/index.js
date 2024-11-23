import { createRouter, createWebHistory } from 'vue-router'
import Registration from "@/components/Registration.vue";
import MainPage from "@/components/MainPage.vue";
import RegistrationError from "@/components/RegistrationError.vue";
import CholetskyMethod from "@/components/CholetskyMethod.vue";
import SimpsonMethod from "@/components/SimpsonMethod.vue";
import ImprovedEulerMethod from "@/components/ImprovedEulerMethod.vue";
import NewtonMethod from "@/components/NewtonMethod.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/auth',
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
