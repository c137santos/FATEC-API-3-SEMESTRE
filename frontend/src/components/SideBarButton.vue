<!-- src/components/SideBarButton.vue -->
<template>
    <router-link :to="to" class="sidebar-button">
      <img src="@/components/icons/star.png" alt="Star Icon" class="star-icon" />
      <span>{{ title }}</span>
    </router-link>
  </template>
  
  <script>
  export default {
    props: {
      title: {
        type: String,
        required: true
      },
      to: {
        type: String,
        required: true
      }
    }
  };
  </script>
  