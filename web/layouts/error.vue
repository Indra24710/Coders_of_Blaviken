<template>
  <div>
    <div class="container">
      <lottie :options="defaultOptions" v-on:animCreated="handleAnimation" />
    </div>
    <div class="">
      <div>
        <p class="is-family-monospace has-text-centered has-text-weight-bold is-size-2">
          Oopsie 😐
        </p>
        <p class="is-family-monospace has-text-centered has-text-weight-bold is-size-4">
          The page you're looking for does not exist.
        </p>
        <br />
        <div class="has-text-centered">
          <nuxt-link to="/">
            <b-button size="is-medium" rounded>
              Return to Homepage
            </b-button>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as animationData from '../static/lottie/404-space.json';
import Lottie from '../pages/lottie.vue';

export default {
  name: 'error',
  layout: 'empty',
  components: {
    lottie: Lottie,
  },
  data() {
    return {
      defaultOptions: {animationData: animationData.default},
      animationSpeed: 1,
    };
  },
};
</script>

<style scoped>
html {
  margin: 0;
  padding: 0;
  background-color: white;
}

body,
html {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.container {
  height: 70vh;
}
</style>
