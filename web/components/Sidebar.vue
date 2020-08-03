<template>
  <div class="sidebar-container">
    <b-menu activable>
      <b-menu-list :label="$t('lang') ">
        <b-menu-item @click="changeLanguage('en')" icon="font" icon-pack="fas" :label="$t('en')"></b-menu-item>
        <b-menu-item @click="changeLanguage('hi')" icon="om" icon-pack="fas" :label="$t('hi')"></b-menu-item>
      </b-menu-list>  
      <b-menu-list :label=" $t('dash') ">
        <b-menu-item
          @click="$router.push('/analytics')"
          icon="chart-bar"
          icon-pack="fas"
          :label="$t('analy') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/dash')"
          icon="tachometer-alt"
          icon-pack="fas"
          :label=" $t('watchlist') "
        ></b-menu-item>
      </b-menu-list>
      <b-menu-list :label="$t('dat') ">
        <b-menu-item
          @click="$router.push('/detections')"
          icon="exclamation-triangle"
          icon-pack="fas"
          :label="$t('detections') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/criminals')"
          icon="user-ninja"
          icon-pack="fas"
          :label="$t('criminals') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/violence')"
          icon="bomb"
          icon-pack="fas"
          :label="$t('vio') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/track')"
          icon="search-location"
          icon-pack="fas"
          :label="$t('rat') "
        ></b-menu-item>
      </b-menu-list>
      <b-menu-list :label="$t('up') ">
        <b-menu-item
          @click="$router.push('/upload/video')"
          icon="video"
          icon-pack="fas"
          :label="$t('uav') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/upload/photo')"
          icon="images"
          icon-pack="fas"
          :label="$t('uap') "
        ></b-menu-item>
        <b-menu-item
          @click="$router.push('/results')"
          icon="poll-h"
          icon-pack="fas"
          :label="$t('res') "
        ></b-menu-item>
      </b-menu-list>
      <b-menu-list :label="$t('acc') ">
        <b-menu-item
          @click="$router.push('/profile')"
          icon="id-badge"
          icon-pack="fas"
          :label="$t('pro') "
        ></b-menu-item>
        <b-menu-item @click="signOut" icon="sign-out-alt" icon-pack="fas" :label="$t('signout') "></b-menu-item>
      </b-menu-list>
    </b-menu>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
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

      changeLanguage(lang) {  
      this.$i18n.locale = lang;
    }
  },
};
</script>

<style lang="scss">
.sidebar-container {
  margin-top: 7vh;
}
</style>
