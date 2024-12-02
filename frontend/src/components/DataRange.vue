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

.date-input-container label {
  margin-bottom: 4px;
  font-size: 14px;
  color: #49454F;
}
</style>
