<template>
  <div>
    <b-table
      :loading="isLoading"
      :paginated="paginated"
      pagination-position="both"
      pagination-size="is-small"
      mobile-cards
      :striped="isStriped"
      :per-page="perPage"
      hoverable
      default-sort="id"
      default-sort-direction="desc"
      :sort-icon="sortIcon"
      :sort-icon-size="sortIconSize"
      :data="clients"
    >
      <template slot-scope="props">
        <b-table-column label="Criminal Name" field="name" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search using the Criminal Name" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          {{ props.row.name }}
        </b-table-column>
        <b-table-column label="Criminal ID" field="id" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search with the Criminal ID using the box!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          <nuxt-link
            :to="{name: 'criminal-cid', params: {cid: props.row.id}}"
            class="button is-small is-link has-text-weight-bold"
          >{{ props.row.id }}</nuxt-link>
        </b-table-column>
        <b-table-column label="Severity" field="severity" sortable centered>{{ props.row.severity }}</b-table-column>
        <b-table-column class="image is-square" label="Criminal Database Image">
          <template slot="header" slot-scope="{column}">
            <p class="is-family-monospace">{{ column.label }}</p>
          </template>
          <img class="zoom" :src="props.row.picture" />
        </b-table-column>
        <b-table-column label="Gender" field="gender" searchable sortable>
          <template slot="header" slot-scope="{column}">
            <b-tooltip label="Search with Gender using the box!" dashed>
              <p class="is-family-monospace">{{ column.label }}</p>
            </b-tooltip>
          </template>
          <span class="tag is-link">{{ props.row.gender }}</span>
          <!-- {{ props.row.time_stamp }} -->
        </b-table-column>
      </template>

      <section slot="empty" class="section">
        <div class="content has-text-grey has-text-centered">
          <template v-if="isLoading">
            <p>
              <b-icon icon="dots-horizontal" size="is-large" />
            </p>
            <p>{{ $t('fetchingData') }}</p>
          </template>
          <template v-else>
            <p>
              <b-icon icon="emoticon-sad" size="is-large" />
            </p>
            <p>{{ $t('nothingHere') }}&hellip;</p>
          </template>
        </div>
      </section>
    </b-table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CriminalsTable',
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
      trashObject: null,
      clients: [],
      isStriped: true,
      selected: null,
      isLoading: false,
      paginated: false,
      perPage: 5,
      checkedRows: [],
      coord: '',
      sortIcon: 'arrow-up',
      sortIconSize: 'is-medium',
    };
  },
  computed: {
    trashObjectName() {
      if (this.trashObject) {
        return this.trashObject.name;
      }

      return null;
    },
  },
  mounted() {
    console.log(this.dataUrl);
    if (this.dataUrl) {
      console.log(this.dataUrl);
      this.isLoading = true;
      this.$axios
        .get(this.dataUrl)
        .then(r => {
          this.isLoading = false;
          if (r.data && r.data.criminals) {
            if (r.data.criminals.length > this.perPage) {
              this.paginated = true;
            }
            this.clients = r.data.criminals;
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
};
</script>
