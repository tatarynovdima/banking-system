<template>
  <div class="header">
    <a href="/main" class="logo">Online Banking System</a>
    <div class="header-right">
      <table class="user-table">
        <tr>
          <td>User</td>
          <td>Balance</td>
        </tr>
        <tr>
          <td>{{ userData ? userData.fullName : 'Unknown' }}</td>
          <td>{{ userData ? userData.balance : '0' }} €</td>
        </tr>
      </table>
    </div>
    <a href="#" class="logout-button" @click.prevent="logout">Log out</a>
  </div>
</template>


<script>
export default {
  name: 'Header',
  data() {
    return {
      userData: null
    };
  },
  mounted() {
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
      const token = localStorage.getItem('token');
      if (token) {
        fetch('http://localhost:8080/api/balance', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok');
              }
              return response.json();
            })
            .then(data => {
              this.userData = data;
            })
            .catch(error => {
              console.error('There was a problem with your fetch operation:', error);
            });
      } else {
        console.error('Token not found in local storage');
      }
    },
    logout() {
      // Удаление токена из localStorage
      localStorage.removeItem('token');
      // Перенаправление на страницу входа
      this.$router.push('/login');
    }
  }
}
</script>

<style src="./Header.css"></style>
