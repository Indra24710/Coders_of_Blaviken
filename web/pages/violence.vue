<template>
  <div class="page">
    <PageHead :subtitle="$t('dat')" :title="$t('viod')" />
    <Card class="has-table" title="Click on the arrow on the left to open map!">
      <ViolenceTable data-url="https://coders-of-blaviken-api.herokuapp.com/api/violence/" />
    </Card>
  </div>
</template>

<script>
import ViolenceTable from '@/components/ViolenceTable';
import PageHead from '@/components/PageHead';
import Card from '@/components/Card';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
    Card,
    ViolenceTable,
  },
};
</script>