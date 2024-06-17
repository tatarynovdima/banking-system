<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="email">Email address</label>
        <input type="text" id="email" v-model="email" required>
      </div>
      <div>
        <label for="password">Password</label>
        <div class="password-input">
          <input :type="passwordFieldType" id="password" v-model="password" required>
          <i class="fas" :class="passwordIconClass" @click="togglePasswordVisibility"></i>
        </div>
      </div>
      <p class="create-account-link">
        <router-link to="/registration">Create account</router-link>
      </p>
      <div class="button-container">
        <button type="submit">Login</button>
      </div>
    </form>
    <p v-if="errorMsg">{{ errorMsg }}</p>
  </div>
</template>

<script src="./LoginPage.js"></script>

<style src="./LoginPage.css"></style>
