<template>
  <div class="hero is-fullheight" id="app">
    <nuxt />
    <Footer />
  </div>
</template>

<script>
// @ is an alias to /src
// import NavBar from '@/components/NavBar'
// import AsideMenu from '@/components/AsideMenu'
// import FooterBar from '@/components/FooterBar'
import Footer from '@/components/Footer';
export default {
  name: 'App',
  components: {
    // FooterBar,
    Footer,
    // AsideMenu,
    // NavBar
  },
  computed: {
    menu() {
      return [
        'Dashboard',
        [
          {
            to: '/',
            icon: 'desktop-mac',
            label: 'Dashboard',
          },
        ],
        'Features',
        [
          {
            to: '/tables',
            label: 'Tables',
            icon: 'table',
            updateMark: true,
          },
          {
            to: '/forms',
            label: 'Forms',
            icon: 'square-edit-outline',
          },
          {
            to: '/search',
            label: 'Search a Criminal',
            icon: 'account-circle',
          },
          {
            to: '/recent',
            label: 'Recent Sightings',
            icon: 'account-circle',
          },
          {
            to: '/track',
            label: 'Tracking',
            icon: 'account-circle',
          },
          {
            to: '/upload',
            label: 'Upload Footage',
            icon: 'account-circle',
          },
        ],
        'Profile',
        [
          {
            to: '/profile',
            label: 'View Profile',
            icon: 'account-circle',
          },
          {
            to: '#',
            label: 'Logout',
            icon: 'logout',
          },
        ],
        'About',
        [
          {
            href: 'https://github.com/cyberShaw',
            label: 'View the Source',
            icon: 'credit-card',
          },
          {
            to: '/about',
            label: 'About',
            icon: 'help-circle',
          },
        ],
      ];
    },
  },
  created() {
    this.$store.commit('user', {
      name: 'John Doe',
      email: 'john@example.com',
      avatar: 'https://avatars.dicebear.com/v2/gridy/John-Doe.svg',
    });
  },
  mounted() {
    document.documentElement.classList.add('has-aside-left');
    document.documentElement.classList.add('has-aside-mobile-transition');
    document.documentElement.classList.add('has-navbar-fixed-top');
    document.documentElement.classList.add('has-aside-expanded');
  },
  methods: {
    menuClick(item) {
      if (item.action && item.action === 'dark-mode-toggle') {
        this.$store.commit('darkModeToggle');
      }
    },
  },
};
</script>
