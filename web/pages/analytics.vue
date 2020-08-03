<template>
  <div class="page">
    <PageHead :title="$t('analy')" :subtitle="$t('dash')" />
    <div>
      <div class="tile is-vertical is-ancestor">
        <div class="tile is-parent">
          <div class="tile is-parent">
            <div class="tile is-child notification is-link">
              <article class="media">
                <figure class="media-left">
                  <b-icon pack="fas" icon="user-secret" size="is-medium"></b-icon>
                </figure>
                <figure class="media-right">
                  <p class="title is-size-4 is-size-5-mobile">Recent Detections</p>
                  <p
                    class="subtitle is-size-3 is-size-4-mobile is-family-monospace has-text-weight-bold"
                  >
                    12
                  </p>
                </figure>
              </article>
            </div>
          </div>
          <div class="tile is-parent">
            <div class="tile is-child notification is-black">
              <article class="media">
                <figure class="media-left">
                  <b-icon pack="fas" icon="stream" size="is-medium"></b-icon>
                </figure>
                <figure class="media-right">
                  <p class="title is-size-4 is-size-5-mobile">Watchlist</p>
                  <p
                    class="subtitle is-size-4 is-size-5-mobile is-family-monospace has-text-weight-bold"
                  >
                    3 Criminals Watched
                  </p>
                </figure>
              </article>
            </div>
          </div>
          <div class="tile is-parent">
            <div class="tile is-child notification is-danger">
              <article class="media">
                <figure class="media-left">
                  <b-icon pack="fas" icon="server" size="is-medium"></b-icon>
                </figure>
                <figure class="media-right">
                  <p class="title is-size-5 is-size-6-mobile">New Criminals in Database</p>
                  <p
                    class="subtitle is-size-3 is-size-4-mobile is-family-monospace has-text-weight-bold"
                  >
                    4
                  </p>
                </figure>
              </article>
            </div>
          </div>
        </div>
        <div class="tile is-parent">
          <div class="tile is-child notification is-light is-danger">
            <graph :data="barChartData" :options="barChartOptions" :height="200" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PageHead from '@/components/PageHead';
import Graph from '@/components/Graph';

export default {
  layout: 'dashboard',
  components: {
    PageHead,
    Graph,
  },

  data() {
    return {
      heatMapPoints: [
        {lat: 23.255404, lng: 77.414452},
        {lat: 23.255404, lng: 77.414452},
        {lat: 23.255404, lng: 77.41245},
        {lat: 23.255404, lng: 77.410448},
        {lat: 23.255404, lng: 77.408482},
        {lat: 23.255404, lng: 77.406472},
        {lat: 23.255404, lng: 77.404462},
        {lat: 23.2585, lng: 77.504462},
        {lat: 23.2585, lng: 77.604462},
        {lat: 23.2585, lng: 77.505462},
        {lat: 23.2585, lng: 77.506462},
        {lat: 23.2585, lng: 77.507462},
        {lat: 23.2205, lng: 77.508462},
        {lat: 23.2205, lng: 77.409062},
      ],

      barChartData: {
        labels: [
          '2019-06',
          '2019-07',
          '2019-08',
          '2019-09',
          '2019-10',
          '2019-11',
          '2019-12',
          '2020-01',
          '2020-02',
          '2020-03',
          '2020-04',
          '2020-05',
        ],
        datasets: [
          {
            label: 'Potential Criminal Activities Avoided',
            data: [10, 35, 25, 30, 20, 30, 20, 33, 34, 45, 51, 78, 85],
            backgroundColor: '#003f5c',
          },
          {
            label: 'Criminals detected',
            data: [30, 24, 57, 23, 68, 72, 25, 64, 133, 143, 165, 33, 56],
            backgroundColor: '#2f4b7c',
          },
          {
            label: 'Criminal Activities detected',
            data: [45, 65, 30, 53, 34, 35, 26, 37, 34, 45, 67, 87, 98],
            backgroundColor: '#665191',
          },
        ],
      },
      barChartOptions: {
        responsive: true,
        legend: {
          display: false,
        },
        title: {
          display: true,
          text: 'Model Performance Data',
          fontSize: 24,
          fontColor: '#6b7280',
        },
        tooltips: {
          backgroundColor: '#17BF62',
        },
        scales: {
          xAxes: [
            {
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
              gridLines: {
                display: false,
              },
            },
          ],
        },
      },
    };
  },
  // mounted() {
  //   /* Data points defined as an array of LatLng objects */
  //   var heatmapData = [
  //     new google.maps.LatLng(23.255404, 77.414452),
  //     new google.maps.LatLng(23.255404, 77.414452),
  //     new google.maps.LatLng(23.255404, 77.412450),
  //     new google.maps.LatLng(23.255404, 77.410448),
  //     new google.maps.LatLng(23.255404, 77.408482),
  //     new google.maps.LatLng(23.255404, 77.406472),
  //     new google.maps.LatLng(23.255404, 77.404462),
  //     new google.maps.LatLng(23.258500, 77.504462),
  //     new google.maps.LatLng(23.258500, 77.604462),
  //     new google.maps.LatLng(23.258500, 77.505462),
  //     new google.maps.LatLng(23.258500, 77.506462),
  //     new google.maps.LatLng(23.258500, 77.507462),
  //     new google.maps.LatLng(23.220500, 77.508462),
  //     new google.maps.LatLng(23.220500, 77.409062)
  //   ];

  //   var MadhyaPradesh = new google.maps.LatLng(23.473324, 77.947998);

  //   map = new google.maps.Map(document.getElementById('map'), {
  //     center: MadhyaPradesh,
  //     zoom: 5,
  //     mapTypeId: 'satellite'
  //   });

  //   var heatmap = new google.maps.visualization.HeatmapLayer({
  //     data: heatmapData
  //   });
  //   heatmap.setMap(map);
  // },
};
</script>
