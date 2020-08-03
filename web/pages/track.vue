<template>
  <div class="page">
    <PageHead :subtitle="$t('dat')" :title="$t('rat')" />
    <Card class="has-table" title="Recent Criminal Detections" v-if="isLoaded">
      <GMap
        ref="gMap"
        language="en"
        :cluster="{options: {styles: clusterStyle}}"
        :center="{lat: locations[0].lat, lng: locations[0].lng}"
        :options="{fullscreenControl: false, styles: mapStyle}"
        :zoom="3"
      >
        <GMapMarker
          v-for="location in locations"
          :key="location.id"
          :position="{lat: location.lat, lng: location.lng}"
          :options="{icon: location === currentLocation ? pins.selected : pins.notSelected}"
          @click="currentLocation = location"
        >
          <GMapInfoWindow :options="{maxWidth: 400}">
            <b-message type="is-danger">
              <p>
                <span class="title is-family-primary is-size-6">Detection ID:</span>
                <span class="subtitle is-family-primary is-size-6">{{ location.id }}</span>
              </p>
              <p>
                <span class="title is-family-primary is-size-6">Criminal ID:</span>
                <span class="subtitle is-family-primary is-size-6">{{ location.cid }}</span>
              </p>
              <b-button
                @click="openInNewWindow(location.lat, location.lng)"
                class="has-text-weight-centered is-family-primary"
              >Track >></b-button>
            </b-message>
          </GMapInfoWindow>
        </GMapMarker>
      </GMap>
    </Card>
  </div>
</template>

<script>
import PageHead from '@/components/PageHead';
import Card from '@/components/Card';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
    Card,
  },
  data() {
    return {
      currentLocation: {
        lat: '11.0168',
        lng: '76.9558',
      },
      detections: [],
      mapData: [],
      locations: [],
      clients: null,
      lats: [],
      circleOptions: {},
      locations: [],
      pins: {},
      mapStyle: [],
      isLoaded: false,
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
  mounted() {
    // this.isLoading = true;
    this.$axios
      .get('https://coders-of-blaviken-api.herokuapp.com/api/detections')
      .then(r => {
        this.isLoading = false;
        if (r.data && r.data.detections) {
          this.clients = r.data.detections;
          this.clients.forEach(el => {
            let val = el['location'];
            this.lats.push(val);
            // console.log(this.lats);
          });
          for (let i = 0; i < this.lats.length; i++) {
            let res = this.lats[i].split('Lats');
            this.mapData.push(res);
            // console.log(this.mapData);
          }
          let i = 0;
          this.clients.forEach(el => {
            el['location'] = this.mapData[i];
            i = i + 1;
          });
          // console.log(this.clients);
        }
        for (let i = 0; i < this.clients.length; i++) {
          let temp = {
            lat: '',
            lng: '',
            id: '',
            cid: '',
            timeStamp: '',
          };
          temp.lat = parseFloat(this.clients[i].location[0]);
          temp.lng = parseFloat(this.clients[i].location[1]);
          temp.id = this.clients[i].id;
          temp.cid = this.clients[i].cid;
          temp.timeStamp = this.clients[i].time_stamp;
          this.locations.push(temp);
        }
        this.locations.push({lat: 23.4356, lng: 23.4234, id: 23, cid: 1231});

        // console.log(JSON.stringify(this.locations));
        this.isLoaded = true;
      })
      .catch(e => {
        this.isLoading = false;
        this.$buefy.toast.open({
          message: `Error: ${e.message}`,
          type: 'is-danger',
        });
      });
  },
  methods: {
    openInNewWindow(lat, lng) {
      let query = 'https://www.google.com/maps/search/?api=1&query=' + lat + ',' + lng;
      window.open(query);
    },
  },
};
</script>
