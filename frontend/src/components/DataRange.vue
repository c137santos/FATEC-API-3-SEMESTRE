<template>
  <div class="date-range">
    <div class="date-input-container">
      <label for="start-date">Data Início:</label>
      <input
        id="start-date"
        class="date-input"
        type="date"
        v-model="startDate"
        @change="emitStartDate"
      />
    </div>
    <div class="date-input-container">
      <label for="end-date">Data Fim:</label>
      <input
        id="end-date"
        class="date-input"
        type="date"
        v-model="endDate"
        @change="emitEndDate"
      />
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