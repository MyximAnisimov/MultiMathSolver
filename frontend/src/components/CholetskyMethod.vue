<script>
import ButtonRegLog from "@/components/Button.vue";
import apiClient from "@/main.js";
import { notify } from "@kyvg/vue3-notification";
import ResultDisplay from "@/components/ResultDisplay.vue";

export default {
  components: { ResultDisplay, ButtonRegLog },
  data() {
    return {
      size: "",
      matrix: "",
      vector: "",
      result: ""
    };
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
            this.result = localStorage.getItem("choletsky");
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
    }
  }
}
</script>

<template>
  <section class="main">
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

      <ResultDisplay id="results" :results="result" />
    </div>
  </section>
</template>

<style scoped>
.title {
  text-align: center;
  font-size: 2em;
  margin-bottom: 20px;
}

.form-container {
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto; /* Центрируем контейнер */
}

#matrix-size {
  margin: 10px 0;
  width: 100%; /* Занять всю ширину */
}

textarea {
  width: 100%; /* Занять всю ширину */
  margin: 10px 0;
  height: 100px; /* Фиксированная высота для textarea */
}

/* Ограничиваем изменение размера текстовой области */
.no-resize {
  resize: none; /* Запретить изменение размера */
}

#results {
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  max-width: 600px;
  margin: 20px;
}
</style>
