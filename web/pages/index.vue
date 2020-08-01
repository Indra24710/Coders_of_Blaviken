<template>
  <div id="wrapper" class="has-text-centered-mobile">
    <div id="loader" class="is-family-secondary" v-if="load">
      JATAYU
      <div class="progress-bar">
        <span class="bar">
          <span class="progress"></span>
        </span>
      </div>
    </div>
    <div v-if="loaded">
      <section id="hero" class="hero is-medium">
        <div class="hero-head">
          <Navbar />
          <ProfileBadge />
        </div>
        <div class="hero-body">
          <div class="container">
            <div class="columns">
              <div class="column is-flex is-column is-horizontal-centered is-vertical-centered">
                <h1
                  class="has-text-weight-bold is-family-secondary has-text-green is-size-1 is-size-3-mobile"
                >
                  <em>A Criminal Detection & Tagging System</em>
                </h1>
                <br />
                <p class="is-size-5">
                  Replacing the conventional face recognition based criminal tracking systems with
                  Triple Layer Verification-based distributed national surveillance system.
                </p>
              </div>
              <div class="column is-flex is-horizontal-centered is-vertical-centered">
                <lottie
                  :options="defaultOptions"
                  :height="400"
                  :width="400"
                  v-on:animCreated="handleAnimation"
                />
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id="features" class="section has-mustard-bg">
        <div class="container">
          <div
            class="columns reverse-row-order has-text-centered is-flex is-horizontal-centered is-vertical-centered"
          >
            <div class="column">
              <h3
                class="is-size-4 is-size-5-mobile is-color-blue has-text-weight-bold is-family-sans-serif is-capitalized"
              >
                Face Recognition
              </h3>
              <p
                class="is-size-3 is-size-4-mobile is-color-primary has-text-weight-bold is-family-sans-serif"
              >
                Say hello to the future.
              </p>
              <p class="is-size-5 is-size-6-mobile is-color-primary">
                A face detection layer powered by Retinaface which is robust to positional and
                resolution changes and can detect faces in an image with great accuracy.
              </p>
            </div>
            <div class="column svg-container">
              <img src="../static/image/video.svg" alt />
            </div>
          </div>
        </div>
        <div class="container">
          <div
            class="columns row-order has-text-centered is-flex is-horizontal-centered is-vertical-centered"
          >
            <div class="column svg-container">
              <img src="../static/image/data.svg" alt />
            </div>
            <div class="column">
              <h3
                class="is-size-4 is-size-5-mobile is-color-blue has-text-weight-bold is-family-sans-serif is-capitalized"
              >
                Data Mining
              </h3>
              <p
                class="is-size-3 is-size-4-mobile is-color-primary has-text-weight-bold is-family-sans-serif"
              >
                Effectively Mining, at a deeper level.
              </p>
              <p class="is-size-5 is-size-6-mobile is-color-primary">
                The internet, especially social media sites are scraped for images of the personnel.
                Only those images that show more than 85% accuracy are included into our criminal
                database.
              </p>
            </div>
          </div>
        </div>
        <div class="container">
          <div
            class="columns reverse-row-order has-text-centered is-flex is-horizontal-centered is-vertical-centered"
          >
            <div class="column">
              <h3
                class="is-size-4 is-size-5-mobile is-color-blue has-text-weight-bold is-family-sans-serif is-capitalized"
              >
                3D Comparsion
              </h3>
              <p
                class="is-size-3 is-size-4-mobile is-color-primary has-text-weight-bold is-family-sans-serif"
              >
                Increased Accuracy, with face reconstruction.
              </p>
              <p class="is-size-5 is-size-6-mobile is-color-primary">
                Being a false positive outcome in the case of tagging criminals is a serious error.
                Therefore, a 3D map of the face is constructed and compared.
              </p>
            </div>
            <div class="column svg-container">
              <img src="../static/image/3d.svg" alt />
            </div>
          </div>
        </div>
      </section>

      <section id="cta" class="section">
        <div class="container">
          <h3 class="is-size-3 has-text-centered has-text-weight-bold">
            Track and Detect Criminals!
          </h3>
          <br />
          <div class="is-flex is-horizontal-centered">
            <nuxt-link to="/auth/login">
              <button class="button is-large has-text-weight-semibold">Sign In 🔐</button>
            </nuxt-link>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>
<script>
import Lottie from './lottie.vue';
import Navbar from '@/components/Navbar.vue';
import * as animationData from '../static/lottie/cctv-camera.json';
import ProfileBadge from '@/components/ProfileBadge';

export default {
  layout: 'default',
  components: {
    lottie: Lottie,
    Navbar,
    ProfileBadge,
  },
  data() {
    return {
      defaultOptions: {animationData: animationData.default},
      animationSpeed: 1,
      load: true,
      loaded: false,
    };
  },
  methods: {
    handleAnimation: function (anim) {
      this.anim = anim;
    },
  },
  mounted() {
    setTimeout(() => {
      this.load = false;
      this.loaded = true;
    }, 3000);
  },
};
</script>
<style lang="scss">
#features {
  background-color: #83d4ff;
}

@media (max-width: 1024px) {
  .reverse-row-order {
    flex-direction: column-reverse;
    display: flex;
  }

  .row-order {
    flex-direction: column;
    display: flex;
  }
}

.feature-img {
  max-width: 60%;
  /* margin: 0 auto; */
}

.is-horizontal-centered {
  justify-content: center;
}

.is-vertical-centered {
  align-items: center;
}

.is-column {
  flex-direction: column;
}

.section .container,
.hero .hero-body,
.hero .hero-head {
  width: 1200px;
  max-width: 100%;
  margin: 0 auto;
}

/*
Some aesthetic improvements
*/
nav {
  padding-top: 20px;
  max-height: 50px;
}

.paragraph {
  width: 600px;
  line-height: 1.5em;
}

/*
Hero
*/
.burger {
  position: absolute;
  top: 0;
  right: 0;
}
@media (min-width: 1024px) {
  .hero-body {
    background-size: 30%;
    background-position: 80% 50%;
    background-repeat: no-repeat;
  }
  .hero-body p {
    width: 600px;
  }
}

.progress {
  animation: loader 4s ease-out infinite;
  background: #d4d4d4;
  color: #fff;
  padding: 3px;
  width: 100%;
  transform: scaleX(0);
}

.progress-bar {
  border-radius: 60px;
  overflow: hidden;
  max-width: 480px;
  width: 100%;
}

.progress-bar span {
  display: block;
}

.bar {
  background: rgba(0, 0, 0, 0.075);
}

#loader {
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  letter-spacing: 1vw;
  font-weight: 200;
  font-size: 8vw;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
  z-index: 9999;
  background-color: white;
  color: #333;
  padding: 1rem;
  transition: 1s opacity ease-in-out;
}

@keyframes loader {
  0% {
    transform: translateX(-100%) scaleX(0);
  }

  50% {
    transform: translateX(0) scaleX(1);
  }

  100% {
    transform: translateX(100%);
  }
}
</style>
