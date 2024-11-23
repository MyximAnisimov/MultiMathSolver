<script>
export default {
  name: "error-page-app",
  props: {
    errorCode: String,
    errorMessage: String
  }
}
</script>

<template>
  <div id='content'>
    <h1>{{ errorCode }}</h1>
    <h2>{{ errorMessage }}</h2>
    <hr>
    <router-link to='/auth' id='button'>Вернуться на главную страницу</router-link>
  </div>
</template>

<style scoped>
#error-message{
  //text-align: center;
  //font-size: 100%;
}
</style>