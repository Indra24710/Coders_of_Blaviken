<template>
  <div>
    <modal-box
      :is-active="isModalActive"
      :trash-object-name="trashObjectName"
      :tf="tf"
      @confirm="trashConfirm"
      @cancel="trashCancel"
    />
    <b-table
      :loading="isLoading"
      :paginated="paginated"
      hasMobileCards
      :striped="isStriped"
      detailed
      detail-key="id"
      :per-page="perPage"
      hoverable
      default-sort="id"
      default-sort-direction="desc"
      :sort-icon="sortIcon"
      :sort-icon-size="sortIconSize"
      :data="clients"
      ref="table"
    >
      <template slot-scope="props">
        <b-table-column label="Detection ID" field="id" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search with the detection ID using the box!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          {{ props.row.id }}
        </b-table-column>
        <b-table-column label="Criminal ID" field="cid" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search with the Criminal ID using the box!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          <nuxt-link
            :to="{name: 'criminal-cid', params: {cid: props.row.cid}}"
            class="button is-small is-link has-text-weight-bold"
            >{{ props.row.cid }}</nuxt-link
          >
        </b-table-column>
        <b-table-column label="Location: Coordinates" field="location" sortable centered>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Click on the icon at the left to show map!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          {{ props.row.location }}
        </b-table-column>
        <b-table-column class="image is-square" label="Detected Image">
          <template slot="header" slot-scope="{column}">
            <p class="is-family-monospace">{{ column.label }}</p>
          </template>
          <img class="zoom" :src="props.row.rsrc" />
        </b-table-column>
        <b-table-column label="Time Stamp" field="time_stamp" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search with the timestamp using the box!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          <span class="tag is-link">{{ props.row.time_stamp }}</span>
          <!-- {{ props.row.time_stamp }} -->
        </b-table-column>
        <b-table-column label="Verified" field="valid" searchable centered sortable>
          <template slot="header" slot-scope="{column}">
            <p class="is-family-monospace">{{ column.label }}</p>
          </template>
          <template v-if="props.row.valid">
            <b-icon icon="check-circle" pack="fas" size="is-medium" type="is-success"></b-icon>
          </template>
          <template v-else>
            <b-icon icon="times-circle" pack="fas" size="is-medium" type="is-danger"></b-icon>
          </template>
        </b-table-column>
        <b-table-column custom-key="action-false" label="Mark as False Positive">
          <div
            class="buttons"
            @click.prevent="trashModal(props.row.id, false)"
            v-if="props.row.valid"
          >
            <b-icon type="is-danger" icon="exclamation" pack="fas" size="is-medium" />
          </div>
        </b-table-column>
        <b-table-column custom-key="actions-true" label="Mark as Verified">
          <div
            class="buttons"
            @click.prevent="trashModal(props.row.id, true)"
            v-if="!props.row.valid"
          >
            <b-icon icon="clipboard-check" pack="fas" type="is-success" size="is-medium" />
          </div>
        </b-table-column>
      </template>

      <section slot="empty" class="section">
        <div class="content has-text-grey has-text-centered">
          <template v-if="isLoading">
            <p>
              <b-icon icon="dots-horizontal" size="is-large" />
            </p>
            <p>Fetching data...</p>
          </template>
          <template v-else>
            <p>
              <b-icon icon="emoticon-sad" size="is-large" />
            </p>
            <p>Nothing's here&hellip;</p>
          </template>
        </div>
      </section>
      <template slot="detail" slot-scope="props">
        <article class="media">
          <div class="media-content">
            <div class="content">
              <Maps :loc="props.row.location" />
            </div>
          </div>
        </article>
      </template>
    </b-table>
  </div>
</template>

<script>
import axios from 'axios';
import Maps from '@/components/Maps';
import ModalBox from '@/components/ModalBox';

export default {
  name: 'DetectionsTable',
  components: {
    Maps,
    ModalBox,
  },
  props: {
    dataUrl: {
      type: String,
      default: false,
    },
    checkable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      active: false,
      isModalActive: false,
      trashObject: null,
      clients: [],
      isStriped: true,
      selected: null,
      isLoading: false,
      paginated: false,
      perPage: 10,
      checkedRows: [],
      coord: '',
      sortIcon: 'arrow-up',
      sortIconSize: 'is-medium',
      lats: [],
      mapData: [],
      trashID: '',
      trashInfo: '',
      tf: '',
    };
  },
  computed: {
    trashObjectName() {
      if (this.trashObject) {
        return this.trashObject;
      }

      return null;
    },
  },
  mounted() {
    console.log(this.dataUrl);
    if (this.dataUrl) {
      // console.log(this.dataUrl);
      this.isLoading = true;
      this.$axios
        .get(this.dataUrl)
        .then(r => {
          this.isLoading = false;
          if (r.data && r.data.detections) {
            if (r.data.detections.length > this.perPage) {
              this.paginated = true;
            }
            this.clients = r.data.detections;
            this.clients.forEach(el => {
              let val = el['location'];
              this.lats.push(val);
            });
            for (let i = 0; i < this.lats.length; i++) {
              let res = this.lats[i].split('Lats');
              this.mapData.push(res);
              // console.log(res);
            }
            let i = 0;
            this.clients.forEach(el => {
              el['location'] = this.mapData[i];
              i = i + 1;
            });
          }
        })
        .catch(e => {
          this.isLoading = false;
          this.$buefy.toast.open({
            message: `Error: ${e.message}`,
            type: 'is-danger',
          });
        });
    }
  },
  methods: {
    trashModal(trashObject, tf) {
      this.trashObject = trashObject;
      this.trashID = trashObject;
      this.tf = tf;
      this.isModalActive = true;
    },
    trashConfirm() {
      this.isModalActive = false;
      this.$axios
        .get('https://coders-of-blaviken-api.herokuapp.com/api/detections/' + this.trashID)
        .then(res => {
          this.trashInfo = res.data.detections[0];
          this.updateDetection();
        })
        .catch(err => {
          console.log(err);
          this.$buefy.snackbar.open({
            message: 'Error!',
            queue: false,
          });
        });
    },
    updateDetection() {
      console.log(
        this.trashID,
        this.trashInfo.cid,
        this.trashInfo.location,
        this.trashInfo.rsrc,
        this.trashInfo.time_stamp,
        this.tf
      );
      let body =
        'cid=' +
        this.trashInfo.cid +
        '&' +
        'location=' +
        this.trashInfo.location +
        '&' +
        'rsrc=' +
        this.trashInfo.rsrc +
        '&' +
        'time_stamp=' +
        this.trashInfo.time_stamp +
        '&' +
        'valid=' +
        this.tf;
      this.$axios
        .put('https://coders-of-blaviken-api.herokuapp.com/api/detections/' + this.trashID, body)
        .then(res => {
          // console.log(res);
          this.$buefy.snackbar.open({
            message: 'Successfully marked as ' + this.tf,
            queue: false,
          });
        })
        .catch(err => {
          console.log(err);
          this.$buefy.snackbar.open({
            duration: 3000,
            message: 'Error!',
            queue: false,
          });
        });
    },
    trashCancel() {
      this.isModalActive = false;
    },
  },
};
</script>
