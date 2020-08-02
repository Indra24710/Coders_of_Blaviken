<template>
  <div>
    <Navbar />
    <ProfileBadge profileName="User" />
    <section class="section">
      <div class="columns">
        <div class="column is-2">
          <Sidebar />
        </div>
        <div class="column">
          <nuxt />
        </div>
      </div>
    </section>

    <Footer />
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import Footer from '@/components/Footer';
import Sidebar from '@/components/Sidebar';
import Navbar from '@/components/Navbar';
import ProfileBadge from '@/components/ProfileBadge';
export default {
  components: {
    Footer,
    Sidebar,
    Navbar,
    ProfileBadge,
  },
  middleware: 'auth',
  computed: {
    ...mapGetters({
      user: 'user',
    }),
  },
};
</script>

<style lang="scss"></style>
