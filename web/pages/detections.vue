<template>
  <div class="page">
    <PageHead title="Detections" subtitle="Track & Detect" />
    <Card class="has-table">
      <detections-table data-url="https://coders-of-blaviken-api.herokuapp.com/api/detections" />
    </Card>
  </div>
</template>

<script>
import DetectionsTable from '@/components/DetectionsTable';
import Card from '@/components/Card';
import PageHead from '@/components/PageHead';
export default {
  layout: 'dashboard',
  components: {
    DetectionsTable,
    Card,
    PageHead,
  },
};
</script>
