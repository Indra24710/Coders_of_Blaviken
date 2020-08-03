<template>
  <div class="page">
    <PageHead :title="$t('pho')" :subtitle="$t('up')" />
    <b-message type="is-success" has-icon icon="arrow-circle-up" icon-pack="fas" size="is-medium">
      <p
        class="is-family-monospace"
      >Welcome! Upload the photo you have to verify and add to the sighting database!</p>
    </b-message>
    <section>
      <div class="columns">
        <div class="column is-5 anim">
          <lottie
            :options="defaultOptions"
            :height="400"
            :width="400"
            v-on:animCreated="handleAnimation"
          />
        </div>
        <div class="column">
          <b-field class="file">
            <b-upload v-model="file" expanded>
              <a class="button is-link is-fullwidth">
                <b-icon icon="upload"></b-icon>
                <span>{{ file.name || 'Click to upload' }}</span>
              </a>
            </b-upload>
          </b-field>
          <b-field>
            <b-upload v-model="dropFiles" multiple drag-drop expanded>
              <section class="section">
                <div class="content has-text-centered">
                  <p>
                    <b-icon icon="upload" size="is-large"></b-icon>
                  </p>
                  <p>Drop your files here or click to upload</p>
                </div>
              </section>
            </b-upload>
          </b-field>
          <div class="tags">
            <span v-for="(file, index) in dropFiles" :key="index" class="tag is-primary">
              {{ file.name }}
              <button
                class="delete is-small"
                type="button"
                @click="deleteDropFile(index)"
              ></button>
            </span>
          </div>
          <div class="butn">
            <b-button
              @click.prevent="getURL"
              type="is-link"
              size="is-medium"
              rounded
              icon-left="cloud-upload-alt"
              icon-pack="fas"
              :uploading="isUploading"
            >Upload</b-button>
            <b-button
              @click="clearFiles"
              type="is-danger"
              size="is-medium"
              rounded
              icon-left="trash-alt"
              icon-pack="fas"
            >Clear File</b-button>
          </div>
          <br />
          <div>
            <b-message type="is-success" has-icon v-if="success == 1">
              <p class="is-family-monospace has-text-weight-bold">
                Your image has been uploaded successfully. It is now being
                <br />processed by the Deep Learning Core. Please wait ...
              </p>
              <p>The uploaded file is of size -> {{ size }} MB</p>
              <p>
                It can be viewed at:
                <a :href="bucketURL">{{ bucketURL }}</a>
              </p>
            </b-message>
            <b-message type="is-danger" has-icon v-if="success == 2">
              <p
                class="is-family-monospace has-text-weight-bold"
              >There was an error while uploading the file! 🙃</p>
              <p>The error status is: {{ error }}</p>
            </b-message>
            <div class="card" v-if="success == 3">
              <div class="columns">
                <div class="column">
                  <b-message
                    type="is-link"
                  >The image matches the criminal database records! Criminal with ID {{ detectedCid }} has been identified!</b-message>
                </div>
              </div>
            </div>

            <b-message
              type="is-danger"
              has-icon
              v-if="success == 4"
            >Sorry :(. The uploaded image does not match our criminal database records. Please try another image.</b-message>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Lottie from '../lottie.vue';
import PageHead from '@/components/PageHead';
import * as animationData from '@/static/lottie/upload-photo.json';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
    lottie: Lottie,
  },
  data() {
    return {
      defaultOptions: {animationData: animationData.default},
      animationSpeed: 1,
      file: {},
      dropFiles: [],
      success: null,
      error: '',
      bucketURL: '',
      size: '',
      serverURL: '',
      isUploading: null,
      showUploadDialog: false,
      detectedCid: '',
      currentLocation: '',
      currentLoc: '',
    };
  },
  methods: {
    handleAnimation: function(anim) {
      this.anim = anim;
    },
    deleteDropFile(index) {
      this.dropFiles.splice(index, 1);
    },
    getURL() {
      // console.log(this.file);
      this.isUploading = true;
      let fd = new FormData();
      fd.append('video', this.file);
      this.$axios({
        url: 'https://upload-dimg.herokuapp.com/upload/video',
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        method: 'post',
        data: fd,
      })
        .then(res => {
          console.log(res);
          this.success = 1;
          this.bucketURL = res.data.url;
          this.size = Math.round((res.data.size / (1024 * 1024) + Number.EPSILON) * 100) / 100;
          this.uploadImage();
        })
        .catch(err => {
          this.error = err;
        });
    },
    uploadImage() {
      // console.log(this.serverURL + '/image' + '?url=' + this.bucketURL);
      this.$axios
        .get(this.serverURL + '/image' + '?url=' + this.bucketURL)
        .then(res => {
          this.isUploading = false;
          console.log(res);
          console.log(res.data.cid);
          if (res.data.cid == 'Not in database') {
            this.success = 4;
          } else {
            this.success = 3;
            this.detectedCid = res.data.cid;
            console.log(this.res);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    clearFiles() {
      this.file = {};
    },
    updateDatabase() {
      this.$axios.post('https://coders-of-blaviken-api.herokuapp.com/api/detections', {
        cid: this.detectedCid,
        location: this.currentLoc,
        rsrc: this.bucketURL,
        // time_stamp:
        // location: this.
      });
    },
  },
  mounted() {
    // console.log(Date.now());
    this.currentLocation = navigator.geolocation.getCurrentPosition(pos => {
      let lat = parseFloat(pos.coords.latitude.toFixed(4));
      let lng = parseFloat(pos.coords.longitude.toFixed(4));
      this.currentLoc = lat + 'Lats' + lng;
      // console.log(this.currentLoc);
    });

    this.$axios
      .get('https://codersofblaviken.blob.core.windows.net/detection/images/url.json')
      .then(res => {
        console.log(res.data.url);
        this.serverURL = res.data.url;
      })
      .catch(err => {
        console.log(err);
      });
  },
};
</script>

<style>
.butn {
  display: flex;
  justify-content: center;
}
.anim {
  position: relative;
}

div > svg {
  position: absolute;
  height: 100%;
  width: 100%;
  left: 0;
  top: 0;
}

.upload-btn {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
