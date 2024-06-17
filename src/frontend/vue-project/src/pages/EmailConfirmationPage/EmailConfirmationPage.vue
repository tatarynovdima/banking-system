<template>
  <div class="confirmation-container">
    <p>Please check your email, we sent an activation link</p>
  </div>
</template>

<script>
export default {
  name: 'EmailConfirmation'
}
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