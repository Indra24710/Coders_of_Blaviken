<template>
  <div class="hero">
    <div class="hero-body">
      <div class="container">
        <span class="is-size-5">{{ subtitle }}</span>
        <span class="is-size-5">/</span>
        <span class="is-size-4 has-text-weight-bold highlight">{{ title }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageHead',
  props: ['subtitle', 'title'],
};
</script>
