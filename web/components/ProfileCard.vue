<template>
  <div class="container">
    <div class="tile is-ancestor">
      <div class="tile is-vertical is-8">
        <div class="tile">
          <div class="tile is-parent is-vertical">
            <article class="tile is-child notification is-black">
              <p class="title">Criminal Name 🔰</p>
              <p class="subtitle has-text-weight-bold is-family-monospace">{{ name }}</p>
            </article>
            <article class="tile is-child notification is-warning">
              <p class="title">Criminal ID 🆔</p>
              <p class="subtitle has-text-weight-bold is-family-monospace">{{ cid }}</p>
            </article>
            <div class="tile is-child">
              <article class="tile is-child notification is-danger">
                <p class="title">Severity Rating ⚠️</p>
                <p class="subtitle has-text-weight-bold is-family-monospace">{{ severity }}</p>
                <div class="content">
                  <!-- Content -->
                </div>
              </article>
            </div>
          </div>
          <div class="tile is-parent is-vertical">
            <article class="tile is-child notification is-link">
              <p class="title">Criminal Photo 📸</p>
              <div class="img-container">
                <img :src="img_rsc" class="zoom" />
              </div>
            </article>
            <article class="tile is-child notification is-info">
              <p class="title">Gender 📝</p>
              <p class="subtitle has-text-weight-bold is-family-monospace">{{ gender }}</p>
              <div class="content">
                <!-- Content -->
              </div>
            </article>
          </div>
        </div>
      </div>
      <div class="tile is-parent">
        <article class="tile is-child notification is-success">
          <div class="content">
            <p class="title">Detections 🌍</p>
            <!-- <p class="subtitle">With even more content</p> -->
            <div class="content">
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15666.035794074467!2d77.02311632666643!3d11.000389224715542!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x4a6ff9afe6fbc8b3!2sSINGANALLUR%20BUS%20STAND!5e0!3m2!1sen!2sin!4v1595945232309!5m2!1sen!2sin"
                width="600"
                frameborder="0"
                style="border:0; height: 60vh;"
                allowfullscreen=""
                aria-hidden="false"
                tabindex="0"
              ></iframe>
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileCard',
  props: ['cid', 'img_rsc', 'name', 'gender', 'severity'],
  mounted() {
    // document.getElementById('c-image').setAttribute('src', this.img_rsc);
  },
  data() {
    return {
      currentLocation: {},
      circleOptions: {},
      locations: [
        {
          lat: 44.933076,
          lng: 15.629058,
        },
        {
          lat: 45.815,
          lng: '15.9819',
        },
        {
          lat: '45.12',
          lng: '16.21',
        },
      ],
      pins: {},
      mapStyle: [],
      clusterStyle: [
        {
          url:
            'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m1.png',
          width: 56,
          height: 56,
          textColor: '#fff',
        },
      ],
    };
  },
};
</script>
<style>
.img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  /* max-height: 20vh; */
}
</style>