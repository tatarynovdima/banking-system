<template>
  <div class="registration-container">
    <h1>Registration</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="email">Email address</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div>
        <label for="firstName">First Name</label>
        <input type="text" id="firstName" v-model="firstName" required>
      </div>
      <div>
        <label for="lastName">Last Name</label>
        <input type="text" id="lastName" v-model="lastName" required>
      </div>
      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="text" id="phoneNumber" v-model="phoneNumber" required>
      </div>
      <div>
        <label for="countryName">Country</label>
        <select id="countryName" v-model="selectedCountry" required>
          <option v-for="country in countries" :key="country.id" :value="country.name">{{ country.name }}</option>
        </select>
      </div>
      <div>
        <label for="password">Password</label>
        <div class="password-input">
          <input :type="passwordFieldType" id="password" v-model="password" required>
          <i class="fas" :class="passwordIconClass" @click="togglePasswordVisibility"></i>
        </div>
      </div>
      <div>
        <label for="confirmPassword">Confirm Password</label>
        <div class="password-input">
          <input :type="passwordFieldType" id="confirmPassword" v-model="confirmPassword" required>
          <i class="fas" :class="passwordIconClass" @click="togglePasswordVisibility"></i>
        </div>
      </div>
      <div class="checkbox-container">
        <input type="checkbox" id="terms" v-model="agreeTerms" required>
        <label for="terms">I am 16 years of age or older and agree to the terms of the Bank Subscriber Agreement</label>
      </div>
      <div class="button-container">
        <button type="submit">Register</button>
      </div>
    </form>
    <p v-if="errorMsg">{{ errorMsg }}</p>
  </div>
</template>

<script src="./RegistrationPage.js"></script>

<style src="./RegistrationPage.css"></style>
