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
            <div class="content" v-if="isMounted">
              <div v-if="displayMap">
                <ProfileMaps :coord="mapData" />
              </div>
            </div>
            <div v-if="!displayMap">
              <b-message type="is-warning" has-icon icon="frown" icon-pack="fas">
                <p class="is-family-monospace is-size-5">No detections for this criminal yet!</p>
              </b-message>
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileMaps from '@/components/ProfileMaps';
export default {
  name: 'ProfileCard',
  props: ['cid', 'img_rsc', 'name', 'gender', 'severity'],
  components: {
    ProfileMaps,
  },

  data() {
    return {
      detections: [],
      lats: [],
      mapData: [],
      isMounted: false,
      displayMap: false,
    };
  },
  mounted() {
    this.$axios
      .get('https://coders-of-blaviken-api.herokuapp.com/api/detections/')
      .then(r => {
        this.isLoading = false;
        if (r.data && r.data.detections) {
          this.detections = r.data.detections;
          this.detections.forEach(el => {
            if (el['cid'] == this.cid) {
              let val = el['location'];
              this.lats.push(val);
            }
          });
          // console.log(this.lats);
          for (let i = 0; i < this.lats.length; i++) {
            let res = this.lats[i].split('Lats');
            this.mapData.push(res);
            // console.log(res);
          }
          // console.log(this.mapData);
          if (this.mapData.length > 0) {
            this.displayMap = true;
          }
          let i = 0;
          this.detections.forEach(el => {
            el['location'] = this.mapData[i];
            i = i + 1;
          });
          // console.log(this.mapData);
          this.isMounted = true;
          // console.log(this.lats);
          // console.log(this.detections);
        }
      })
      .catch(err => {
        console.log(err);
      });
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
