<template>
  <div class="page">
    <PageHead :title="$t('res')" :subtitle="$t('up')" />
  </div>
</template>

<script>
import PageHead from '@/components/PageHead';
export default {
  layout: 'dashboard',
  components: {
    PageHead,
  },
};
</script>