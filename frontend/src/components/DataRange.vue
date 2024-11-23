<template>
    <div class="date-range">
      <div class="date-input-container">
        <input
          class="date-input"
          type="date"
          v-model="startDate"
          @change="emitStartDate"
          placeholder="Data início"
        />
        <span class="date-text">DD/MM/YYYY</span>
      </div>
      <div class="date-input-container">
        <input
          class="date-input"
          type="date"
          v-model="endDate"
          @change="emitEndDate"
          placeholder="Data final"
        />
        <span class="date-text">DD/MM/YYYY</span>
      </div>
    </div>
  </template>
  
  <script>
  import '@/assets/base.css';
  export default {
    data() {
      return {
        startDate: '',
        endDate: ''
      };
    },
    methods: {
      emitStartDate() {
        this.$emit('start-date', this.startDate);
      },
      emitEndDate() {
        this.$emit('end-date', this.endDate);
      }
    },
    watch: {
      startDate(val) {
        this.emitStartDate();
      },
      endDate(val) {
        this.emitEndDate();
      }
    }
  };
  </script>
  