<template>

  <div id="app">
    <Loader id="load" v-if="loading"/>
    <div id="contentContainer" v-else>
      <router-view/>
      <notifications />
      <notifications group="error" position="bottom right" max="5" />
      <notifications group="success" position="bottom right" max="5" />
    </div>

  </div>
</template>

<script>

import {Notifications} from "@kyvg/vue3-notification";

export default {
  name: 'App',
  components: {Notifications},
  mounted(){
    setTimeout(() => {this.loading = false}, 1000)
  }
}
</script>

<style>
</style>