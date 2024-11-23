<template>
  <div class="result-container" v-if="results.length > 0">
    <h3>Результаты:</h3>
    <p>{{results}}</p>
  </div>
</template>

<script>
export default {
  props: {
    results: {
      type: Array,
      required: true
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
