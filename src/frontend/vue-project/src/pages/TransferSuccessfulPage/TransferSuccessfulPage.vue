<template>
  <div>
    <Header/>
    <div class="transfer-confirmation-container">
      <p>Transfer is successful. Thanks for choosing us!</p>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/windgest/Header/Header.vue';
import Footer from '@/windgest/Footer/Footer.vue';

export default {
  name: 'Transfer',
  components: {
    Header,
    Footer
  },
  mounted() {
    setTimeout(() => {
      this.$router.push('/transfer');
    }, 3000);
  }
}
</script>

<style scoped>
.transfer-confirmation-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  font-size: 2.5rem;
  font-weight: bold;
}
</style>
