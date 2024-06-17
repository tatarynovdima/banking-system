<template>
  <div>
    <Header/>
    <div class="button-container">
      <div class="button-row">
        <router-link class="custom-button" to="/replenishment">Replenishment</router-link>
        <router-link class="custom-button" to="/transaction">Transaction</router-link>
        <router-link class="custom-button" to="/info">Info</router-link>
      </div>
      <div class="button-row">
        <router-link class="custom-button" to="/transfer">Transfer</router-link>
        <router-link class="custom-button" to="/withdraw">Withdraw</router-link>
        <router-link class="custom-button" to="/account">Account</router-link>
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/windgest/Header/Header.vue';
import Footer from '@/windgest/Footer/Footer.vue';

export default {
  name: 'Main',
  components: {
    Header,
    Footer
  }
}
</script>

<style src="./MainViews.css"></style>
