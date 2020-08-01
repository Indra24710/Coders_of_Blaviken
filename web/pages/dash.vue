<template>
  <div ref="page" class="page">
    <PageHead title="Watchlist" subtitle="Dashboard" />
    <b-message type="is-success" has-icon icon="user-secret" icon-pack="fas" size="is-medium">
      <p>Welcome! Track high risk criminals and add criminals to your watchlist!</p>
      <p class="is-size-6">Click on the criminal ID to view criminal details</p>
    </b-message>
    <div class="columns">
      <div class="column is-half">
        <p class="title is-size-3">Your Watchlist</p>
        <Card class="has-table">
          <b-table
            striped
            hoverable
            default-sort="id"
            default-sort-direction="asc"
            :data="watchlist"
          >
            <template slot-scope="props">
              <b-table-column label="Criminal ID" field="id" sortable centered>
                <template slot="header" slot-scope="{column}">
                  <b-tooltip label="Search with the Criminal ID using the box!" dashed>
                    <p class="is-family-monospace">{{ column.label }}</p>
                  </b-tooltip>
                </template>
                <nuxt-link
                  :to="{name: 'criminal-cid', params: {cid: props.row.id}}"
                  class="button is-small is-link has-text-weight-bold"
                  >{{ props.row.id }}</nuxt-link
                >
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
          </b-table>
        </Card>
      </div>
      <div class="column is-half has-text-centered">
        <p class="title is-size-3">Add a Criminal to Watchlist</p>
        <b-message type="is-link" has-icon icon="map-marker-alt" icon-pack="fas" size="is-medium">
          <p>Add a criminal ID here to add to watchlist!</p>
        </b-message>
        <b-field label="Criminal ID">
          <b-select
            placeholder="Select a Criminal ID"
            icon="id-badge"
            size="is-medium"
            icon-pack="fas"
            expanded
            v-model="selectedCid"
          >
            <option v-for="option in criminals" :value="option" :key="option">{{ option }}</option>
          </b-select>
        </b-field>
        <b-button
          @click="addToList"
          type="is-link"
          ref="addToList"
          icon-left="plus-circle"
          icon-pack="fas"
          >Add to List</b-button
        >
      </div>
    </div>
  </div>
</template>

<script>
// import {mapGetters} from 'vuex';
import PageHead from '@/components/PageHead';
import Card from '@/components/Card';
import {fireDb} from '@/plugins/firebase.js';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
    Card,
  },
  // middleware: 'auth',
  data() {
    return {
      email: '',
      cid: null,
      watchlist: [],
      selectedCid: null,
      criminals: [],
      isLoading: false,
    };
  },
  async mounted() {
    this.email = this.$store.state.user.email;
    // console.log(this.email);
    await fireDb
      .collection(this.email)
      .get()
      .then(query => {
        query.forEach(doc => {
          // console.log(doc.data());
          this.watchlist.push(doc.data());
        });
        // console.log(this.watchlist);
      })
      .catch(err => {
        console.log(err);
      });
    // console.log(JSON.stringify(this.watchlist));

    this.isLoading = true;
    await this.$axios
      .get('https://coders-of-blaviken-api.herokuapp.com/api/criminals')
      .then(r => {
        this.isLoading = false;
        // this.criminals = r.data.criminals;
        r.data.criminals.forEach(el => {
          if (el.id) {
            this.criminals.push(el.id);
            // console.log(el.id);
          }
          // console.log(this.criminals);
        });
      })
      .catch(e => {
        this.isLoading = false;
        this.$buefy.toast.open({
          message: `Error: ${e.message}`,
          type: 'is-danger',
        });
      });
    // console.log(JSON.stringify(this.criminals));
  },
  methods: {
    addToList() {
      console.log(this.selectedCid.toString());
      fireDb
        .collection(this.email)
        .doc(this.selectedCid.toString())
        .set({
          id: this.selectedCid,
        })
        .then(res => {
          this.forceRerender();
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
  },
};
</script>
