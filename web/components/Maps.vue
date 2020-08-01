<template>
  <div>
    <p class="subtitle">Location of Detection</p>
    <GMap
      ref="gMap"
      language="en"
      :cluster="{options: {styles: clusterStyle}}"
      :center="{lat: locations[0].lat, lng: locations[0].lng}"
      :options="{fullscreenControl: false, styles: mapStyle}"
      :zoom="6"
    >
      <GMapMarker
        v-for="location in locations"
        :key="location.id"
        :position="{lat: location.lat, lng: location.lng}"
        :options="{icon: location === currentLocation ? pins.selected : pins.notSelected}"
        @click="currentLocation = location"
      >
        <GMapInfoWindow :options="{maxWidth: 200}">
          <code>
            <p @click="openInNewWindow(location.lat, location.lng)">Open location in Google Maps</p>
          </code>
        </GMapInfoWindow>
      </GMapMarker>
    </GMap>
  </div>
</template>

<script>
export default {
  name: 'Maps',
  props: {
    loc: Array,
  },
  data() {
    return {
      currentLocation: {
        lat: parseFloat(this.loc[0]),
        lng: parseFloat(this.loc[1]),
      },
      circleOptions: {},
      locations: [
        {
          lat: parseFloat(this.loc[0]),
          lng: parseFloat(this.loc[1]),
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
  mounted() {
    // console.log(this.loc);
  },
  methods: {
    openInNewWindow(lat, lng) {
      let query = 'https://www.google.com/maps/search/?api=1&query=' + lat + ',' + lng;
      window.open(query);
    },
  },
};
</script>

<style>
#map-box {
  height: 50vh;
}
</style>
