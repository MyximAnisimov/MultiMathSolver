<template>
  <div id="content">
    <h1 class="greeting">Добро пожаловать на сайт-решатор по математике</h1>

    <div class="form-container">
      <form id="form">
        <div id="login">
          <label for="loginInput">Введите логин:</label>
          <input class="inputs"
                 type="text"
                 id="loginInput"
                 required
                 placeholder="Логин"
                 v-model.trim="email"/>
        </div>
        <div id="password">
          <label for="passwordInput">Введите пароль:</label>
          <input
              class="inputs"
              type="password"
              id="passwordInput"
              required
              placeholder="Пароль"
              v-model.trim="password"/>
        </div>
        <div id="buttons">
          <ButtonRegLog color="blue" label="Войти в систему" @click="logIn"/>
          <ButtonRegLog color="white" style="color: black" label="Зарегистрироваться" @click="register"/>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import ButtonRegLog from "@/components/button/Button.vue";
import { notify } from "@kyvg/vue3-notification";
import apiClient from "@/main.js";

export default {
  name: "RegistrationPage",
  components: {
    ButtonRegLog,
  },
  beforeCreate() {
    localStorage.clear();
  },
  data(){
    return{
      email: "",
      password: ""
    }
  },
  methods: {
    logIn(e){
      e.preventDefault()
      apiClient.post('http://localhost:8080/user/login', {
        email: this.email,
        password: this.password
      }).then(response => {
        notify({
          group: 'success',
          title: 'Авторизация',
          text: 'Вход выполнен успешно',
          type: 'success'
        });
        localStorage.setItem("jwt", response.data);
        this.$router.push({name: 'main'});
        console.log(localStorage.getItem("jwt"))
      }).catch(error => {
        this.AxiosErrorHandler(error.response.data);
      });
    },
    register(e){
      e.preventDefault();
      apiClient.post('http://localhost:8080/user/registration', {
        password: this.password,
        email: this.email
      }).then(() => {
        notify({
          group: 'success',
          title: 'Регистрация',
          text: 'Вы теперь можете войти',
          type: 'success'
        });
      }).catch(error => {
        this.AxiosErrorHandler(error.response.data);
      });
    },
    AxiosErrorHandler(errorText){
      notify({
        group: 'error',
        title: 'Error',
        text: errorText,
        type: 'error'
      })
    }
  }
}
</script>

<style scoped>
#content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  height: 100vh;
}

.greeting {
  padding-top: 50px;
  text-align: center;
  font-size: 2em;
  margin-bottom: 20px;
}

.form-container {
  padding-top: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

#form {
  position: relative;
  font-size: 20px;
  flex-direction: column;
  margin: auto;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 10px 1px black;
}

#form button {
  margin: 20px 10px 10px 10px;
}

#login, #password {
  margin: 5px;
}

input {
  margin-left: 13px;
  padding: 5px 0 5px 2px;
}

.inputs {
  color: black;
  border-radius: 5px;
  border-color: #000000;
}
</style>
