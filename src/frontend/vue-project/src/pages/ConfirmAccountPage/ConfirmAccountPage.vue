<template>
  <div class="confirmation-container">
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
export default {
  name: 'ConfirmAccount',
  data() {
    return {
      message: ''
    };
  },
  mounted() {
    const token = this.$route.query.token;
    if (token) {
      fetch(`http://localhost:8080/api/confirm-account?token=${token}`, {
        method: 'GET'
      })
          .then(response => response.text())
          .then(data => {
            this.message = data;
            if (data === 'Email verified successfully!') {
              setTimeout(() => {
                this.$router.push('login');
              }, 3000); // перенаправление через 2 секунды
            }
          })
          .catch(error => {
            this.message = 'Error confirming account';
            console.error('Error confirming account:', error);
          });
    } else {
      this.message = 'Invalid confirmation token';
    }
  }
};
</script>

<style scoped>
.confirmation-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  font-size: 2.5rem;
  font-weight: bold;
}
</style>
