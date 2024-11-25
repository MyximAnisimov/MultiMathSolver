<script>
import ButtonRegLog from "@/components/Button.vue";
import apiClient from "@/main.js";
import { notify } from "@kyvg/vue3-notification";
import ResultDisplay from "@/components/ResultDisplay.vue";
export default {

  components: { ResultDisplay, ButtonRegLog },
  data() {
    return {
      systemId: 0,
      systemNumbers: [1, 2, 3, 4],
      numberOfUnknowns: 0,
      initialApproximations: "",
      results: "",
      items: [],
      SNAEF1: '../../public/СНАУ1.PNG',
      SNAEF2: '../../public/СНАУ2.PNG',
      SNAEF3: '../../public/СНАУ3.PNG',
      SNAEF4: '../../public/СНАУ4.PNG'
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
    sendNewtonMethod(e) {
      e.preventDefault();
      const token = localStorage.getItem("jwt");
      if (!token) {
        console.error("JWT Token is missing");
        return;
      }

      apiClient.put('http://localhost:8080/api/newton', {
        systemId: this.systemId,
        numberOfUnknowns: this.numberOfUnknowns,
        initialApproximation: this.initialApproximations
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
            localStorage.setItem("newton", response.data);
            this.results = localStorage.getItem("newton");
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
    },
  }
}
</script>

<template>
  <section class="main">
    <div id="content">
      <h1 class="title">Метод Ньютона</h1>
      <div class="form-container">
        <form id="forms">
          <label for="system-id">Введите номер системы</label>
          <select id="system-id" v-model="systemId">
            <option v-for="number in systemNumbers" :key="number" :value="number">
              {{ number }}
            </option>
          </select>
          <div>
            <h4>Доступные для решения системы</h4>
            <img id="system-of-equations1" :src="SNAEF1" alt="Система 1" />
            <img id="system-of-equations" :src="SNAEF2" alt="Система 2" />
            <img id="system-of-equations" :src="SNAEF3" alt="Система 3" />
            <img id="system-of-equations" :src="SNAEF4" alt="Система 4" />
          </div>
          <label for="matrix">Введите количество начальных приближений</label>
          <textarea id="matrix" v-model="numberOfUnknowns" placeholder="Введите матрицу" class="no-resize"></textarea>
          <label for="vector">Введите значения начальных приближений</label>
          <textarea id="vector" v-model="initialApproximations" class="no-resize"></textarea>
          <ButtonRegLog color="white" style="color: black" label="Отправить данные" @click="sendNewtonMethod"/>
        </form>
      </div>

      <ResultDisplay id="results" :results="results" />
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
  font-size: 2em;
  margin-bottom: 20px;
}

.form-container {
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
}

#system-id {
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

#results {
  border: 2px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  max-width: 600px;
  margin: 20px;
}
.result-container {
  margin-top: 20px;
  padding: 15px;
  border: 2px solid #ccc;
  border-radius: 5px;
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

#system-of-equations{
  padding: 10px;
  width: 40%;
}
#system-of-equations1{
  width: 20%;
  padding: 10px;
}
</style>
