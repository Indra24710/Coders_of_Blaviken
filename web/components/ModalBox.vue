<template>
  <b-modal :active.sync="isModalActive" has-modal-card>
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Confirm</p>
      </header>
      <section class="modal-card-body">
        <p>
          This will mark ID
          <b>{{ trashObjectName }}</b>
          <span v-if="tf">as verified</span>
          <span v-else>as a false positive</span>
        </p>
      </section>
      <footer class="modal-card-foot">
        <button class="button" type="button" @click="cancel">Cancel</button>
        <button v-if="tf" class="button is-danger" @click="confirm">Mark as Verified</button>
        <button v-else class="button is-danger" @click="confirm">Mark as False</button>
      </footer>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'ModalBox',
  props: {
    isActive: {
      type: Boolean,
      default: false,
    },
    trashObjectName: {
      type: Number,
      default: null,
    },
    tf: {
      type: Boolean,
      default: null,
    },
  },
  data() {
    return {
      isModalActive: false,
    };
  },
  methods: {
    cancel() {
      this.$emit('cancel');
    },
    confirm() {
      this.$emit('confirm');
    },
  },
  watch: {
    isActive(newValue) {
      this.isModalActive = newValue;
    },
    isModalActive(newValue) {
      if (!newValue) {
        this.cancel();
      }
    },
  },
};
</script>
