<template>
  <div class="result-container" v-if="results.length > 0">
    <h3>Результаты:</h3>

    <h4>Вектор Y:</h4>
    <ul>
      <li v-for="(item, index) in firstHalf" :key="index">{{ item }}</li>
    </ul>

    <h4>Вектор X:</h4>
    <ul>
      <li v-for="(item, index) in secondHalf" :key="index">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    results: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      resultsSplittedByComma: this.results.split(",").map(item => item.trim())
    };
  },
  computed: {
    firstHalf() {
      const midIndex = Math.ceil(this.resultsSplittedByComma.length / 2);
      return this.resultsSplittedByComma.slice(0, midIndex);
    },
    secondHalf() {
      const midIndex = Math.ceil(this.resultsSplittedByComma.length / 2);
      return this.resultsSplittedByComma.slice(midIndex);
    }
  }
}
</script>

<style scoped>
.result-container {
  margin-top: 20px;
  padding: 15px;
  border: 2px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  word-wrap: break-word; /* Позволяет переносить длинные слова */
  overflow-wrap: break-word; /* Современный стандарт для переноса слов */
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
</style>
