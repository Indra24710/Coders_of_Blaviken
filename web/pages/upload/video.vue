<template>
  <div class="page">
    <PageHead :title="$t('vid')" :subtitle="$t('up')" />
    <b-message type="is-success" has-icon icon="arrow-circle-up" icon-pack="fas" size="is-medium">
      <p class="is-family-monospace">
        Welcome! Upload the video footage you have to verify and add to the sighting database!
      </p>
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
              <button class="delete is-small" type="button" @click="deleteDropFile(index)"></button>
            </span>
          </div>
          <div class="butn">
            <b-button
              @click.prevent="uploadVideo"
              type="is-link"
              size="is-medium"
              rounded
              icon-left="cloud-upload-alt"
              icon-pack="fas"
              >Upload</b-button
            >
            <b-button
              @click="clearFiles"
              type="is-danger"
              size="is-medium"
              rounded
              icon-left="trash-alt"
              icon-pack="fas"
              >Clear File</b-button
            >
          </div>
          <br />
          <div>
            <b-message type="is-success" has-icon v-if="success == 1">
              <p class="is-family-monospace has-text-weight-bold">
                Your video has been uploaded successfully. Thank you for contributing to the
                dataset! 😄
              </p>
              <p>The uploaded file is of size -> {{ size }} MB</p>
              <p>
                It can be viewed at:
                <a :href="bucketURL">{{ bucketURL }}</a>
              </p>
            </b-message>
            <b-message type="is-danger" has-icon v-if="success == 2">
              <p class="is-family-monospace has-text-weight-bold">
                There was an error while uploading the file! 🙃
              </p>
              <p>The error status is: {{ error }}</p>
            </b-message>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Lottie from '../lottie.vue';
import PageHead from '@/components/PageHead';
import * as animationData from '@/static/lottie/upload-video.json';
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
    };
  },
  methods: {
    handleAnimation: function(anim) {
      this.anim = anim;
    },
    deleteDropFile(index) {
      this.dropFiles.splice(index, 1);
    },
    uploadVideo() {
      // console.log(this.file);
      let fd = new FormData();
      fd.append('video', this.file);
      this.$axios({
        url: 'https://upload-dimg.herokuapp.com/upload/video',
        headers: {
          'Content-Type': 'video/mp4',
        },
        method: 'post',
        data: fd,
      })
        .then(res => {
          console.log(res);
          this.success = 1;
          this.bucketURL = res.data.url;
          this.size = Math.round((res.data.size / (1024 * 1024) + Number.EPSILON) * 100) / 100;
        })
        .catch(err => {
          this.error = err;
          this.success = 2;
        });
    },
    clearFiles() {
      this.file = {};
    },
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
</style>
