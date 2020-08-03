<template>
  <div class="page">
    <PageHead :title="$t('pro')" :subtitle="$t('acc')" />
    <div class="section">
      <div class="media">
        <div class="media-left pad is-hidden-mobile">
          <div class="svg-container">
            <img src="../static/image/profile.svg" />
          </div>
        </div>
        <div class="media-content">
          <h1 class="is-size-1 is-family-secondary"></h1>
          <div class="content is-size-5 is-family-monospace">
            <div class="media">
              <div class="media-left has-text-weight-bold">💂 Name:</div>
              <div class="media-content">Administrator</div>
            </div>
            <div class="media">
              <div class="media-left has-text-weight-bold">📧 Email:</div>
              <div class="media-content">{{ $store.state.user.email }}</div>
            </div>
            <div class="media">
              <div class="media-left has-text-weight-bold">🆔 User ID:</div>
              <div class="media-content">#420</div>
            </div>
            <div class="media">
              <div class="media-left has-text-weight-bold">📍 Zone:</div>
              <div class="media-content">South Zone</div>
            </div>
            <div class="media">
              <div class="media-left has-text-weight-bold">🏙️ City:</div>
              <div class="media-content">Coimbatore</div>
            </div>
            <div class="media">
              <b-button @click="signOut" type="is-link" size="is-medium" class="is-fullwidth">
                <p class="has-text-weight-bold">Sign Out</p>
              </b-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import PageHead from '@/components/PageHead';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
  },
  computed: {
    ...mapGetters({
      user: 'user',
    }),
  },
  methods: {
    signOut() {
      this.$store
        .dispatch('signOut')
        .then(() => {
          this.$router.push('/');
        })
        .catch(err => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.svg-container {
  max-width: 35vw;
}
.media {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
