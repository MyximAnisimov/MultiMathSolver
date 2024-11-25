<template>
  <div class="result-container" v-if="items.length > 0">
    <h3>Результаты:</h3>
    <ul>
      <li v-for="(item, index) in items" :key="index">
        {{ index }}: {{ item.trim() }}
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data() {
    return {
      results: "",
      items: []
    }
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
    splitString() {
      this.items = this.results.split(",");
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
</style>