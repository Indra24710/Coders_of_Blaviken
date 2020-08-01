<template>
  <div class="page">
    <PageHead title="Criminals" subtitle="Track & Detect" />
    <Card class="has-table">
      <criminals-table data-url="https://coders-of-blaviken-api.herokuapp.com/api/criminals" />
    </Card>
  </div>
</template>

<script>
import CriminalsTable from '@/components/CriminalsTable';
import Card from '@/components/Card';
import PageHead from '@/components/PageHead';
export default {
  layout: 'dashboard',
  components: {
    CriminalsTable,
    Card,
    PageHead,
  },
};
</script>
