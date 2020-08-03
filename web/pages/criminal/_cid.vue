<template>
  <div class="page">
    <PageHead subtitle="Track & Detect" title="Criminal Profile" />
    <ProfileCard
      v-if="criminals"
      :cid="$route.params.cid"
      :img_rsc="criminals[0].picture"
      :name="criminals[0].name"
      :gender="criminals[0].gender"
      :severity="criminals[0].severity"
    />
  </div>
</template>

<script>
import ProfileCard from '../../components/ProfileCard';
import PageHead from '../../components/PageHead';
export default {
  layout: 'dashboard',
  components: {
    ProfileCard,
    PageHead,
  },
  data() {
    return {
      criminals: null,
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$axios
        .get('https://coders-of-blaviken-api.herokuapp.com/api/criminals/' + this.$route.params.cid)
        .then(res => {
          if (res.data && res.data.criminals) {
            this.criminals = res.data.criminals;
            // console.log(this.criminals[0].rsrc);
            // console.log(this.criminals);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
  },
};
</script>
