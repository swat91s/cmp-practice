<template>
  <div id="app">
    <Students />
    <footer class="info">
      <p>Sample Application to demonstrate Spring Boot and VueJS</p>
      <p>Original StudentApp project is developed using Spring Boot and Hibernate</p>
    </footer>
  </div>
</template>

<script>
  import Students from './components/StudentForm.vue'
  // app Vue instance
  const app = {
    name: 'app',
    components: {
      Students
    },
    // app initial state
    data: () => {
      return {
      }
    }
  }

  export default app
</script>

<style>
  #app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
