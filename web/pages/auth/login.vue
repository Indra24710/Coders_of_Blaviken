<template>
  <div>
    <Navbar />
    <section class="section">
      <div class="container has-text-centered">
        <div class="columns is-8 is-variable is-vcentered">
          <div class="column is-7 has-text-centered">
            <h1 class="is-size-4 has-text-info">Log In to Samaritan</h1>
            <p class="is-size-3 has-text-weight-bold is-color-primary">
              Criminal Tracking and Detection,
              <br />Made Easy.
            </p>
            <figure class="image is-3by2">
              <img src="../../static/image/surveillance.svg" />
            </figure>
          </div>
          <div class="column is-5 has-text-left">
            <div class="container is-vcentered">
              <b-notification
                v-if="success == 1"
                type="is-link"
                has-icon
                aria-close-label="Close notification"
              >
                <p class="is-family-monospace has-text-weight-bold">
                  You have successfully signed in!
                </p>
              </b-notification>
              <b-notification
                v-if="success == 2"
                type="is-danger"
                has-icon
                aria-close-label="Close notification"
              >
                <p class="is-family-monospace has-text-weight-bold">{{ error }}</p>
              </b-notification>
              <div class="field">
                <p class="control has-icons-left has-icons-right">
                  <input
                    class="input is-medium"
                    v-model="email"
                    type="email"
                    placeholder="Enter your email ID 😄"
                  />
                  <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
                  </span>
                </p>
              </div>
              <div class="field">
                <p class="control has-icons-left">
                  <input
                    class="input is-medium"
                    v-model="password"
                    type="password"
                    placeholder="Your super secret password 🔐"
                  />
                  <span class="icon is-medium is-left">
                    <i class="fas fa-lock"></i>
                  </span>
                </p>
              </div>
              <div class="field">
                <p class="control">
                  <button
                    class="is-block is-medium is-fullwidth has-text-weight-bold button is-success"
                    @click="signIn"
                  >
                    Login 🔐
                  </button>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      email: '',
      password: '',
      success: null,
      error: null,
    };
  },

  methods: {
    signIn() {
      this.$store
        .dispatch('signInWithEmail', {
          email: this.email,
          password: this.password,
        })
        .then(() => {
          this.email = '';
          this.password = '';
          this.success = 1;
          setTimeout(() => {
            this.$router.push('/dash');
          }, 1500);
        })
        .catch(err => {
          console.log(err);
          this.error = err;
          this.success = 2;
        });
    },
  },
};
</script>

<style></style>
