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
      functionNumbers: [1, 2, 3, 4, 5],
      selectedNumber: null,
      epsilon: 0.0,
      result: "",
      items: [],
      simpsonIntegral1: '../../public/SimpsonIntegral1.PNG',
      simpsonIntegral2: '../../public/SimpsonIntegral2.PNG',
      simpsonIntegral3: '../../public/SimpsonIntegral3.PNG',
      simpsonIntegral4: '../../public/SimpsonIntegral4.PNG',
      simpsonIntegral5: '../../public/SimpsonIntegral5.PNG'

    };
  },
  methods: {
    sendSimpsonMethod(e) {
      e.preventDefault();
      const token = localStorage.getItem("jwt");
      if (!token) {
        console.error("JWT Token is missing");
        return;
      }

      apiClient.put('http://localhost:8080/api/simpson', {
        minValue: this.minValue,
        maxValue: this.maxValue,
        functionNumbers: this.selectedNumber,
        epsilon: this.epsilon
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
            localStorage.setItem("simpson", response.data);
            this.result = localStorage.getItem("simpson");
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
      <h1 class="title">Метод Симпсона</h1>
      <div class="form-container">
        <form id="forms">
          <div>
            <h4>Доступные для решения интегралы</h4>
            <img id="integral" :src="simpsonIntegral1" alt="Система 1" />
            <img id="integral" :src="simpsonIntegral2" alt="Система 2" />
            <img id="integral" :src="simpsonIntegral3" alt="Система 3" />
            <img id="integral" :src="simpsonIntegral4" alt="Система 4" />
            <img id="integral" :src="simpsonIntegral5" alt="Система 4" />
          </div>
          <label for="function-id">Введите номер интеграла</label>
          <select id="function-id" class="input" v-model="selectedNumber">
            <option v-for="number in functionNumbers" :key="number" :value="number">
              {{ number }}
            </option>
          </select>
          <label for="a-value">Введите значение а</label>
          <textarea id="a-value" class="no-resize" v-model="minValue"></textarea>
          <label for="b-value">Введите значение b</label>
          <textarea id="b-value" class="no-resize" v-model="maxValue"></textarea>
          <label for="epsilon">Введите значение приближения epsilon</label>
          <input id="epsilon" v-model="epsilon">
          <ButtonRegLog color="white" style="color: black" label="Отправить данные" @click="sendSimpsonMethod"/>
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

#header-title{
  font-size: 2em;
  margin-bottom: 20px
}
.header{
  text-align: center;
  border-radius: 5px;
}

.form-container {
  border-radius: 10px;
  padding: 20px;
  max-width: 600px;
  margin: 10px auto;
  box-shadow: 0 5px 15px #181818;
}

.no-resize {
  resize: none;
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
#a-value, #b-value{
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
}

.result-container {
  margin-top: 20px;
  margin-bottom: 20px;
  padding: 15px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 5px 15px #181818;
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
