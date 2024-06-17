<template>
  <div>
    <Header/>

    <div class="transfer-container">
      <h3>Transfer</h3>
      <form @submit.prevent="handleTransfer">
        <div>
          <label for="cardNumber">Card number</label>
          <input type="text" id="cardNumber" v-model="cardNumber" @input="formatCardNumber" maxlength="19" required>
        </div>
        <div>
          <label for="amount">Amount</label>
          <input type="text" id="amount" v-model="amount" @input="formatAmount" required>
        </div>
        <div class="button-container">
          <button type="submit">Transfer</button>
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
  name: 'Transfer',
  components: {
    Header,
    Footer
  },
  data() {
    return {
      cardNumber: '',
      amount: '',
      errorMsg: ''
    };
  },
  methods: {
    formatCardNumber(event) {
      let value = event.target.value.replace(/\D/g, '');
      if (value.length > 16) {
        value = value.substring(0, 16);
      }
      this.cardNumber = value.replace(/(.{4})/g, '$1 ').trim();
    },
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
    handleTransfer() {
      const token = localStorage.getItem('token');
      const cardNumberWithSpaces = this.cardNumber;
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
        recipientCardNumber: cardNumberWithSpaces,
        amount: amount
      };

      fetch('http://localhost:8080/api/transfer', {
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
                throw new Error(data.message || 'Transfer failed');
              });
            }
            return response.json();
          })
          .then(data => {
            if (data && data.message) {
              this.errorMsg = data.message;
              this.$router.push('/transfer-successful');
            }
          })
          .catch(error => {
            if (error.response && error.response.status === 400) {
              let errorMessage = 'Transfer failed';
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

<style src="./TransferPage.css"></style>
