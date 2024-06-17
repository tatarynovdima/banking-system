<template>
  <div>
    <Header/>

    <div class="replenishment-container">
      <h3>Replenishment</h3>
      <form @submit.prevent="handleReplenishment">
        <div>
          <label for="amount">Amount</label>
          <input type="text" id="amount" v-model="amount" @input="formatAmount" required>
        </div>
        <div class="button-container">
          <button type="submit">Replenishment</button>
        </div>
        <div class="error-message" v-if="errorMsg">{{ errorMsg }}</div>
      </form>
    </div>

    <Footer/>
  </div>
</template>

<script>
import Header from '@/windgest/Header/Header.vue';
import Footer from '@/windgest/Footer/Footer.vue';

export default {
  name: 'Replenishment',
  components: {
    Header,
    Footer
  },
  data() {
    return {
      amount: '',
      errorMsg: ''
    };
  },
  methods: {
    formatAmount(event) {
      let value = event.target.value.replace(/[^0-9.]/g, '');
      const pointIndex = value.indexOf('.');

      if (pointIndex !== -1) {
        const integerPart = value.slice(0, pointIndex);
        let decimalPart = value.slice(pointIndex + 1).replace(/\./g, '');
        if (decimalPart.length > 2) {
          decimalPart = decimalPart.slice(0, 2);
        }
        value = `${integerPart}.${decimalPart}`;
      }

      this.amount = value;
    },
    handleReplenishment() {
      const token = localStorage.getItem('token');
      const amount = parseFloat(this.amount);

      if (!token) {
        this.errorMsg = 'Token not found in local storage';
        return;
      }

      if (amount <= 0) {
        this.errorMsg = 'Amount must be greater than zero';
        return;
      }

      const requestData = {
        amount: amount
      };

      fetch('http://localhost:8080/api/replenishment', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(requestData)
      })
          .then(response => {
            if (!response.ok) {
              return response.json().then(data => {
                throw new Error(data.message || 'Replenishment failed');
              });
            }
            return response.json();
          })
          .then(data => {
            if (data && data.message) {
              this.errorMsg = data.message;
              this.$router.push('/replenishment-successful');
            }
          })
          .catch(error => {
            if (error.response && error.response.status === 400) {
              let errorMessage = 'Replenishment failed';
              if (error.response.data && error.response.data.message) {
                errorMessage = error.response.data.message;
              }
              this.errorMsg = errorMessage;
            } else if (error.request) {
              this.errorMsg = 'Error sending request';
            } else {
              this.errorMsg = error.message;
            }
          });
    }
  }
};
</script>

<style src="./ReplenishmentPage.css">


</style>