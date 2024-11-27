<script>
import ButtonRegLog from "@/components/button/Button.vue";
import apiClient from "@/main.js";
import { notify } from "@kyvg/vue3-notification";

export default {
  components: { ButtonRegLog },
  data() {
    return {
      size: "",
      matrix: "",
      vector: "",
      results: "",
      items: []
    };
  },
  watch: {
    results(newValue) {
      if (newValue.trim() !== "") {
        this.splitString();
      } else {
        this.items = [];
      }
    }
  },
  methods: {
    sendMatrix(e) {
      e.preventDefault();
      const token = localStorage.getItem("jwt");
      if (!token) {
        console.error("JWT Token is missing");
        return; // Останавливаем, если токена нет
      }

      apiClient.put('http://localhost:8080/api/choletsky', {
        size: this.size,
        matrix: this.matrix,
        vector: this.vector
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
            localStorage.setItem("choletsky", response.data);
            this.results = localStorage.getItem("choletsky");
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
    splitString() {
      this.items = this.results.split(",");
    }
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
      <h1 class="title">Метод Холецкого</h1>
      <div class="form-container">
        <form id="forms">
          <label for="matrix-size">Введите размер матрицы</label>
          <input id="matrix-size" v-model="size" />
          <label for="matrix">Введите матрицу в соответствии с размером:</label>
          <textarea id="matrix" v-model="matrix" placeholder="Введите матрицу" class="no-resize"></textarea>
          <label for="vector">Введите вектор B (каждое новое значение писать на новой строке)</label>
          <textarea id="vector" v-model="vector" class="no-resize"></textarea>
          <ButtonRegLog color="white" style="color: black" label="Отправить данные" @click="sendMatrix"/>
        </form>
      </div>

      <div class="result-container" id="results" v-if="items.length > 0">
        <h3>Результаты:</h3>
        <ul>
          <li v-for="(item, index) in items" :key="index">
            X{{ index + 1 }}: {{ item.trim() }}
          </li>
        </ul>
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

#matrix-size {
  margin: 10px 0;
  width: 100%;
}

textarea {
  width: 100%;
  margin: 10px 0;
  height: 100px;
}

.no-resize {
  resize: none;
}

.result-container {
  margin-top: 20px;
  margin-bottom: 20px;
  padding: 15px;
  border-radius: 5px;
  background-color: #f9f9f9;
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
  box-shadow: 0 5px 15px #181818;
  max-width: 600px;
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
</style>
