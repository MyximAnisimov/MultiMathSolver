<template>
  <div id="main">
    <h2 class="welcome-message">Ниже представлены методы, которые вы можете решить с помощью нашего сайта</h2>

    <div class="methods-container">
      <div class="methods-box">
        <ul class="methods-list">
          <li><router-link to="/choletsky">Метод Холецкого</router-link></li>
          <li><router-link to="/newton">Метод Ньютона</router-link></li>
          <li><router-link to="/simpson">Метод Симпсона</router-link></li>
          <li><router-link to="/euler">Улучшенный метод Эйлера</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>

</script>

<style scoped>
#welcome-message {
  text-align: center;
  margin-bottom: 20px;
}

.methods-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: calc(100vh - 80px);
}


.methods-box {
  width: 70%;
  text-align: center;
  border-radius: 10px;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 5px 15px #181818;
}

.methods-list {
  list-style-type: none;
  padding: 0;
}

.methods-list li {
  margin: 10px 0;
}

.methods-list a {
  text-decoration: none;
  color: #000000;
  font-weight: bold;
}

.methods-list a:hover {
  text-decoration: underline;
}
</style>
