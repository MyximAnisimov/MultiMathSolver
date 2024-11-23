<template>
  <div id="main">
    <h2 class="welcome-message">Ниже представлены методы, которые вы можете решить с помощью нашего сайта</h2>

    <div class="methods-container">
      <h3 class="methods-title">Выберите метод</h3>
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
  align-items: center; /* Центрируем список по горизонтали */
  justify-content: center; /* Выравниваем по вертикали */
  height: calc(100vh - 80px); /* Высота контейнера, вычитаем высоту заголовка */
}

.methods-title {
  font-size: 1.5em;
  margin: 10px 0;
}

.methods-box {
  width: 70%;
  text-align: center;
  border: 2px solid #000000; /* Цвет рамки */
  border-radius: 10px; /* Скругление углов */
  padding: 20px; /* Внутренние отступы вокруг списка */
  background-color: #f9f9f9; /* Цвет фона внутри рамки */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* Тень для глубины */
}

.methods-list {
  list-style-type: none; /* Убираем маркеры списка */
  padding: 0; /* Убираем отступы */
}

.methods-list li {
  margin: 10px 0; /* Отступы между элементами списка */
}

.methods-list a {
  text-decoration: none; /* Убираем подчеркивание */
  color: #000000; /* Цвет ссылок */
  font-weight: bold; /* Жирный текст для ссылок */
}

.methods-list a:hover {
  text-decoration: underline; /* Подчеркивание при наведении */
}
</style>
