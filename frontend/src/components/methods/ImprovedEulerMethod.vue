<script>
import ButtonRegLog from "@/components/button/Button.vue";
import apiClient from "@/main.js";
import { notify } from "@kyvg/vue3-notification";
export default {

  components: { ButtonRegLog },
  data() {
    return {
      minValue: 0.0,
      maxValue: 0.0,
      numberOfFunction: [1, 2, 3, 4],
      selectedFunction: null,
      epsilon: 0.0,
      y_a: 0.0,
      result: "",
      items: [],
      eulerEquation1: '../../public/Euler1.PNG',
      eulerEquation2: '../../public/Euler2.PNG',
      eulerEquation3: '../../public/Euler3.PNG',
      eulerEquation4: '../../public/Euler4.PNG'

    };
  },
  methods: {
    sendEulerMethod(e) {
      e.preventDefault();
      const token = localStorage.getItem("jwt");
      if (!token) {
        console.error("JWT Token is missing");
        return;
      }

      apiClient.put('http://localhost:8080/api/euler', {
        minValue: this.minValue,
        maxValue: this.maxValue,
        numberOfFunction: this.selectedFunction,
        epsilon: this.epsilon,
        y_a: this.y_a
      }, {
        headers: {
          "Authorization": "Bearer " + token,
        }
      })
          .then(response => {
            notify({
              group: 'success',
              title: 'Отправление',
              text: 'Данные отправлены!',
              type: 'success'
            });
            localStorage.setItem("euler", response.data);
            this.result = localStorage.getItem("euler");
          })
          .catch(error => {
            this.AxiosErrorHandler(error.response.data);
          });
    },
    AxiosErrorHandler(errorText) {
      notify({
        group: 'error',
        title: 'Error',
        text: errorText,
        type: 'error'
      })
    },
  }
}
</script>

<template>
  <section class="main">
    <header class="header">
      <router-link to="/main" class="header-title">
        <h1 id="header-title">Multi math solver</h1>
      </router-link>
    </header>
    <div id="content">
      <h3 class="title">Улучшенный метод Эйлера</h3>
      <div class="form-container">
        <form id="forms">
          <div>
            <h4>Доступные для решения уравнения</h4>
            <img id="integral" :src="eulerEquation1" alt="Система 1" />
            <img id="integral" :src="eulerEquation2" alt="Система 2" />
            <img id="integral" :src="eulerEquation3" alt="Система 3" />
            <img id="integral" :src="eulerEquation4" alt="Система 4" />
          </div>
          <label for="function-id">Введите номер уравнения</label>
          <select id="function-id" v-model="selectedFunction">
            <option v-for="number in numberOfFunction" :key="number" :value="number">
              {{ number }}
            </option>
          </select>
          <label for="a-value">Введите значение а</label>
          <textarea id="a-value" class="no-resize" v-model="minValue"></textarea>
          <label for="b-value">Введите значение b</label>
          <textarea id="b-value" class="no-resize" v-model="maxValue"></textarea>
          <label for="y_a">Введите значение функции y(a)</label>
          <input id="y_a" v-model="y_a">
          <label for="epsilon">Введите значение приближения epsilon</label>
          <input id="epsilon" v-model="epsilon">

          <ButtonRegLog color="white" style="color: black" label="Отправить данные" @click="sendEulerMethod"/>
        </form>
      </div>

      <div class="result-container" id="results" v-if="result.length > 0">
        <h3>Результат</h3>
        {{result}}
      </div>
    </div>
  </section>
</template>

<style scoped>
.title {
  text-align: center;
  font-size: 1.5em;
  margin-bottom: 20px;
}
.header{
  text-align: center;
  border-radius: 5px;
  font-size: 1.4em;
}

.form-container {
  border-radius: 10px;
  padding: 20px;
  max-width: 600px;
  margin: 10px auto;
  box-shadow: 0 5px 15px #181818;
}

#function-id {
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}

textarea {
  width: 100%;
  margin: 10px 0;
  height: 100px;
}
#a-value{
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}
.no-resize {
  resize: none;
}

#b-value{
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}
#y_a{
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}

.result-container {
  margin-top: 20px;
  margin-bottom: 20px;
  box-shadow: 0 5px 15px #181818;
  padding: 15px;
  border-radius: 10px;
  background-color: #f9f9f9;
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
}

.result-container h3 {
  margin-bottom: 10px;
}

.result-container ul {
  list-style-type: none;
  padding: 0;
}

.result-container li {
  margin: 5px 0;
}

#integral{
  padding: 10px;
  width: 30%;
}

#epsilon{
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}
</style>
