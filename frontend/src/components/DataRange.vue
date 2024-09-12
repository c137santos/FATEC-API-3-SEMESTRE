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
  
  <style scoped>
  .date-range {
    display: flex;
    gap: 10px;
    width: 100%;
  }
  
  .date-input-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
  }
  
  .date-input {
    width: 100%;
    padding: 8px;
    font-size: 14px;
    border-radius: 4px;
    border: 1px solid #d9d9d9;
  }
  
  .date-text {
    font-size: 12px;
    color: #49454F;
    margin-top: 4px;
  }
  </style>
  