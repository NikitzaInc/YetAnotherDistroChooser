<template>
  <div class="quiz-card">
    <div class="progress-bar">
      <div class="progress" :style="{ width: progress + '%' }"></div>
    </div>
    
    <h2>{{ currentQuestion.text }}</h2>
    
    <div class="options-grid">
      <button 
        v-for="(option, index) in currentQuestion.options" 
        :key="index"
        @click="selectOption(index)"
        class="option-btn"
      >
        {{ option.label }}
      </button>
    </div>
  </div>
</template>

<script>
import { questions } from '../data/questions';

export default {
  data() {
    return {
      currentQuestionId: 'Q1',
      points: { Comfort: 0, Security: 0, Performance: 0, Stability: 0, Customization: 0 },
      settings: {},
      questions
    };
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentQuestionId];
    },
    progress() {
      // Simple progress calculation based on question ID
      const qNum = parseInt(this.currentQuestionId.replace('Q', '')) || 1;
      return (qNum / 7) * 100;
    }
  },
  methods: {
    selectOption(index) {
      const option = this.currentQuestion.options[index];

      // Update points
      Object.entries(option.points).forEach(([key, value]) => {
        this.points[key] += value;
      });

      // Update settings
      Object.assign(this.settings, option.settings);

      // Handle Logic Branching
      let next = option.next;
      if (next === 'Q6Check') {
        next = (this.points.Performance >= 2 || this.points.Customization >= 2) ? 'Q6' : 'Q7';
      }

      if (next === 'end') {
        this.$emit('finished', { points: this.points, settings: this.settings });
      } else {
        this.currentQuestionId = next;
      }
    }
  }
};
</script>

<style scoped>
.quiz-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: var(--shadow);
}
.progress-bar {
  height: 8px;
  background: #e2e8f0;
  border-radius: 4px;
  margin-bottom: 2rem;
  overflow: hidden;
}
.progress {
  height: 100%;
  background: var(--primary);
  transition: width 0.3s ease;
}
.options-grid {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 1.5rem;
}
.option-btn {
  padding: 1.2rem;
  text-align: left;
  background: #f1f5f9;
  border: 2px solid transparent;
  border-radius: 8px;
  font-size: 1rem;
  color: var(--text-dark);
}
.option-btn:hover {
  border-color: var(--primary);
  background: #eff6ff;
}
</style>