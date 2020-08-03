<template>
  <div class="page">
    <PageHead :title="$t('detections')" :subtitle="$t('dat')" />
    <Card class="has-table" title="Click on the icon on the left to open map!">
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
