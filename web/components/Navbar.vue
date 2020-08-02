<template>
  <!-- <nav class="navbar">
    <div class="container">
      <div class="navbar-brand">
        <a href class="navbar-item">
          <h3 class="is-family-secondary logo has-text-green is-size-4">
            <strong>Jatayu</strong>
          </h3>
        </a>
      </div>
      <div class="navbar-menu">
        <div class="navbar-end">
          <nuxt-link to="/dash" class="a-menu is-size-5 navbar-item">Dashboard</nuxt-link>
          <nuxt-link to="#features" class="a-menu is-size-5 navbar-item">Features</nuxt-link>
          <nuxt-link to="/about" class="a-menu is-size-5 navbar-item">About</nuxt-link>
          <nuxt-link to="/login" class="a-menu is-size-5 navbar-item">Sign In</nuxt-link>
        </div>
      </div>
      <span class="navbar-burger burger" data-target="navbarMenuHeroA">
        <span></span>
        <span></span>
        <span></span>
      </span>
    </div>
  </nav>-->
  <div>
    <div id="myNav" class="overlay">
      <!-- Overlay content -->
      <div class="overlay-content is-family-secondary">
        <nuxt-link to="/" @click="toggleNav" class="link-inner">Home</nuxt-link>
        <nuxt-link to="/dash" @click="toggleNav" class="link-inner">Dashboard</nuxt-link>
        <nuxt-link to="/#features" @click="toggleNav" class="link-inner">Features</nuxt-link>
        <nuxt-link to="/about" @click="toggleNav" class="link-inner">About</nuxt-link>
        <nuxt-link to="/#" @click="toggleNav" class="link-inner">Logout</nuxt-link>
      </div>
    </div>
    <div class="menu-icon" @click="toggleNav">
      <div class="menu-wrap">
        <span class="menu-icon__line menu-icon__line-left"></span>
        <span class="menu-icon__line"></span>
        <span class="menu-icon__line menu-icon__line-right"></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navbar',
  data() {
    return {
      isNavOpen: 0,
    };
  },
  methods: {
    toggleNav() {
      if (this.isNavOpen) {
        document.getElementById('myNav').style.width = '0%';
        this.isNavOpen = 0;
      } else {
        document.getElementById('myNav').style.width = '100%';
        this.isNavOpen = 1;
      }
    },
  },
};
</script>

<style lang="scss">
.overlay {
  /* Height & width depends on how you want to reveal the overlay (see JS below) */
  height: 100%;
  width: 0;
  position: fixed; /* Stay in place */
  z-index: 10; /* Sit on top */
  left: 0;
  top: 0;
  background-color: rgb(0, 0, 0); /* Black fallback color */
  background-color: #f3f6ff; /* Black w/opacity */
  overflow-x: hidden; /* Disable horizontal scroll */
  transition: 0.5s; /* 0.5 second transition effect to slide in or slide down the overlay (height or width, depending on reveal) */
}
/* Position the content inside the overlay */

.overlay-content {
  position: relative;
  top: 25%; /* 25% from the top */
  width: 100%; /* 100% width */
  text-align: center; /* Centered text/links */
  margin-top: 30px; /* 30px top margin to avoid conflict with the close button on smaller screens */
}
/* The navigation links inside the overlay */
.overlay a {
  padding: 8px;
  text-decoration: none;
  font-size: 36px;
  color: #818181;
  display: block; /* Display block instead of inline */
  transition: 0.3s; /* Transition effects on hover (color) */
}

/* When you mouse over the navigation links, change their color */
.overlay a:hover,
.overlay a:focus {
  color: #0068ff;
}

/* Position the close button (top right corner) */
.overlay .closebtn {
  position: absolute;
  top: 20px;
  right: 45px;
  font-size: 60px;
}

@media screen and (max-height: 450px) {
  .overlay a {
    font-size: 20px;
  }
  .overlay .closebtn {
    font-size: 40px;
    top: 15px;
    right: 35px;
  }
}

.menu-icon {
  padding: 1rem -1rem 1rem 1rem;
  $size: 50px;
  border-radius: 10px;
  height: $size;
  width: $size;
  position: fixed;
  z-index: 9000;
  left: calc(3vw + 10px);
  top: calc(3vh + 0.5rem);
  cursor: pointer;
  background-color: #0068ff;
  $csize: 30px;
  display: flex;
  justify-content: center;
  align-items: center;

  .menu-wrap {
    position: relative;
    height: 14px;
    width: 30px;
  }

  &__line {
    height: 2px;
    width: $csize;
    display: block;
    background-color: white;
    margin-bottom: 5px;
    transition: transform 0.2s ease, background-color 0.5s ease;
  }

  &__line-left {
    width: $csize / 2;
  }

  &__line-right {
    width: $csize / 2;
    float: right;
  }
}
</style>
