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
  
  <style scoped>
  .sidebar-button {
    display: flex;
    flex-direction: column; /* Faz o ícone ficar em cima do texto */
    align-items: center;
    justify-content: center;
    padding: 10px;
    text-decoration: none;
    color: #49454F;
    font-size: 14px;
    background-color: transparent;
    border-radius: 8px;
    width: 100%;
    box-sizing: border-box;
    text-align: center;
  }
  
  .sidebar-button:hover {
    background-color: #D9D9D9;
    cursor: pointer;
  }
  
  .star-icon {
    width: 18px; /* Aumentado de 16px para 18px */
    height: 18px; /* Aumentado de 16px para 18px */
    margin-bottom: 5px; /* Espaçamento entre o ícone e o texto */
  }
  
  span {
    text-align: center;
  }
  </style>
  