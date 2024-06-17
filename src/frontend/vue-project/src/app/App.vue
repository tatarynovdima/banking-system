<template>
  <div id="app">
    <header>
      <div class="wrapper">
        <nav style="display: none;">
          <router-link to="/login">Login</router-link>
          <router-link to="/registration">Registration</router-link>
        </nav>
      </div>
    </header>
    <router-view />
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<style scoped>
</style>
